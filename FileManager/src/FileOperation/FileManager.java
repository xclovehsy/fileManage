package FileOperation;

import java.io.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-12 13:18
 */

/*
运用面向对象程序设计思想，基于Java文件管理和I/O框架，实现基于图形界面的GUI文件管理器。
1、实现文件夹创建、删除、进入。
2、实现当前文件夹下的内容罗列。
3、实现文件拷贝和文件夹拷贝（文件夹拷贝指深度拷贝，包括所有子目录和文件）。
4、实现指定文件的加密和解密。
5、实现指定文件和文件夹的压缩。
6、实现压缩文件的解压。
7、文件管理器具有图形界面。
*注意本次实验需要提交实验报告（见附件）和代码
*/

public class FileManager {

    public FileManager() {

    }

    /**
     * 复制文件操作
     * @param copyfile 源文件
     * @param targetFolder 目标文件夹
     * @return 是否成功复制
     */
    public static boolean copyFile(File copyfile, File targetFolder) {
        File newFile = new File(targetFolder.getAbsoluteFile()+File.separator+copyfile.getName());
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
             bis = new BufferedInputStream(new FileInputStream(copyfile));
             bos = new BufferedOutputStream(new FileOutputStream(newFile));

             byte[] bytes = new byte[1024];
             int len;
             while((len = bis.read(bytes))!=-1){
                 bos.write(bytes, 0, len);
             }

             bos.close();
             bis.close();
             return true;  //复制文件成功

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;  //复制文件夹失败
    }

    /**
     * 复制文件夹操作
     * @param copyfile 源文件夹
     * @param targetFolder 目标文件夹
     * @return 是否复制成功
     */
    public static boolean copyFolder(File copyfile, File targetFolder) {
        /**
         * 需要判断目标文件是否为源文件夹的子文件夹
         */
        String pathCopyFile = copyfile.getAbsolutePath();
        String targetFileName = targetFolder.getName();
        if(pathCopyFile.contains(targetFileName)){
            return false;
        }

        if(!targetFolder.exists()){
            targetFolder.mkdirs();
        }

        try {
            dfs(copyfile, targetFolder);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  复制多级文件夹(深度优先算法)
     * @param f 源文件夹
     * @param destFolder 目标文件夹
     * @throws IOException
     */
    public static void dfs(File f, File destFolder)throws IOException{
        if(f.isDirectory()){
            File newFolder = new File(destFolder, f.getName());
            if(!newFolder.exists()){
                newFolder.mkdir();
            }

            File[] files = f.listFiles();

            assert files != null;
            for(File nf: files){
                dfs(nf, newFolder);
            }

        }else{
            File destFile = new File(destFolder, f.getName());
            fileCopy(f, destFile);
        }

    }

    /**
     *  复制文件_用于复制文件夹方法调用
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @throws IOException
     */
    public static void fileCopy(File srcFile, File destFile)throws IOException{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = bis.read(bytes))!=-1){
            bos.write(bytes, 0, len);
        }
        bis.close();
        bos.close();
    }

    /**
     * 遍历当前文件夹
     * @param path 文件夹路径
     * @throws IOException
     */
    public void fileTraversal(String path) throws IOException {
        File parent = new File(path);
        if(parent.exists() && parent.isDirectory()){
            File[] files = parent.listFiles();

            assert files != null;
            for(File file :files){
                if(file.exists() && file.isFile()){
//                    System.out.println(file.getName()+" "+getLastTime(file) + " " + getFileType(file) + " " + getFileSize(file));

                }else if(file.exists() && file.isDirectory()){
//                    System.out.println(file.getName()+" "+getLastTime(file) + " 文件夹");
                }

            }
        }
    }

    /**
     * 获取目标文件大小
     * @param file 目标文件
     * @return 文件大小（bt, kb, mk, gb）
     */
    public static String getFileSize(File file){
        //获取文件大小
        String size = "";
        long fileS = file.length();
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
            size = df.format((double) fileS) + "BT";
        } else if (fileS < 1048576) {
            size = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            size = df.format((double) fileS / 1048576) + "MB";
        } else {
            size = df.format((double) fileS / 1073741824) +"GB";
        }
        return size;
    }

    /**
     * 获取文件类型（后缀名）
     * @param file 目标文件
     * @return 文件类型
     */
    public static String getFileType(File file){
        if(file.isDirectory()){
            return "文件夹";
        }else{
            String filename = file.getName();
            //获取文件类型(后缀)
            String[] strArray = filename.split("\\.");
            int suffixIndex = strArray.length -1;
            String mimeType = strArray[suffixIndex];
            mimeType = mimeType.toUpperCase(Locale.ROOT) + "文件";
            return mimeType;
        }
    }

    /**
     * 获取加密文件名
     * @param file 目标文件
     * @return 加密文件名
     */
    public static String getFileEncryptName(File file){
        String filename = file.getName();
        //获取文件类型(后缀)
        String[] strArray = filename.split("\\.");
        String encryptName = "";
        int suffixIndex = strArray.length -1;
        for(int i = 0; i<strArray.length-1; i++){
            encryptName+=strArray[i];
            if(i!=strArray.length-2){
                encryptName+=".";
            }
        }
        encryptName+="(加密文件)" + ".";
        encryptName+=strArray[suffixIndex];
//        System.out.println(encryptName);
        return encryptName;
    }

    /**
     * 获取解密文件名
     * @param file 目标文件
     * @return 解密文件名
     */
    public static String getFileDecryptName(File file){
        return file.getName().replace("(加密文件)", "(解密文件)");
    }

    /**
     * 获取文件最后修改时间
     * @param file 目标文件
     * @return 文件最后修改时间
     */
    public static String getLastTime(File file){
        //获取文件修改时间
        long time = file.lastModified();
        Date d = new Date(time);
        Format simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String dateString = simpleFormat.format(d);
        return dateString;
    }

    /**
     * 创建一个新的文件
     * @param fileName 新文件夹名
     * @return 创建是否成功
     */
    public static boolean createFile(String fileName){
        if(FileController.getPath().compareTo("") == 0){
            return false;
        }

        String newFilePath = FileController.getPath()+File.separator+fileName;
        File newFile = new File(newFilePath);
        if(newFile.exists()){
            return false;
        }else{
            try {
                return newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 创建一个新的文件夹
     * @param FolderName 新文件夹名
     * @return 创建是否成功
     */
    public static boolean createFolder(String FolderName){
        if(FileController.getPath().compareTo("") == 0){
            return false;
        }

        String newFolderPath = FileController.getPath()+File.separator+FolderName;
        File newFolder = new File(newFolderPath);
        if(newFolder.exists()){
            return false;
        }else{
            return newFolder.mkdir();
        }
    }

    /**
     * 文件夹、文件压缩
     */
    public static boolean compressFile(File file, File destFolder){
        String FilePath = file.getAbsolutePath();
        String zipFilePath = "";
        if(file.isFile()){
            zipFilePath = destFolder.getAbsolutePath() + File.separator +
                    file.getName().substring(0,file.getName().lastIndexOf(".")) + ".zip";
        }else{
            zipFilePath = destFolder.getAbsolutePath() + File.separator +
                    file.getName() + ".zip";
        }

        try {
            FileZip.ZipCompress(FilePath, zipFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文件夹、文件解压
     */
    public static boolean uncompressFile(File file, File destFolder){
        String FilePath = file.getAbsolutePath();
        String newFileName = file.getName().substring(0, file.getName().lastIndexOf("."))+"(解压文件)";
        String zipFilePath = destFolder.getAbsolutePath()+File.separator+ newFileName;
//        System.out.println(FilePath);
//        System.out.println(zipFilePath);

        try {
            System.out.println("hah");
            FileZip.ZipUncompress(FilePath, zipFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除整个文件夹
     * @param father 目标文件夹
     */
    public static void removeFolder(File father){
        if(father == null){
            return;
        }
        File[] children = father.listFiles();
        if(children!=null){
            for(File file : children){
                removeFolder(file);
            }
        }
        father.delete();
    }

    /**
     * 加密文件操作
     * @param file 加密对象文件
     * @param destFolder 目标文件夹
     * @return 是否加密成功
     */
    public static boolean encryptionFile(File file, File destFolder){
        File EncryptFile = new File(destFolder.getAbsoluteFile() + File.separator + getFileEncryptName(file));
        try {
//            FileEncrypt.EnFile(file, EncryptFile);
            FileEncrypt.EnFile(file, EncryptFile);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解密文件操作
     * @param file 解密对象文件
     * @param destFolder 目标文件夹
     * @return 是否解密成功
     */
    public static boolean decryptionFile(File file, File destFolder){
        if(!file.getName().contains("(加密文件)")){  //只能对加密文件进行解密操作
            return false;
        }

        File decryptFile = new File(destFolder.getAbsoluteFile() + File.separator + getFileDecryptName(file));
        try {
            FileEncrypt.DecFile(file, decryptFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 测试函数
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(getFileDecryptName(new File("D:\\xc.lo.ve.hsy(加密文件).txt")));

    }

}
