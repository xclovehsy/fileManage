package FileOperation;

import java.io.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ���
 * @version 1.0
 * @date 2021-11-12 13:18
 */

/*
�����������������˼�룬����Java�ļ������I/O��ܣ�ʵ�ֻ���ͼ�ν����GUI�ļ���������
1��ʵ���ļ��д�����ɾ�������롣
2��ʵ�ֵ�ǰ�ļ����µ��������С�
3��ʵ���ļ��������ļ��п������ļ��п���ָ��ȿ���������������Ŀ¼���ļ�����
4��ʵ��ָ���ļ��ļ��ܺͽ��ܡ�
5��ʵ��ָ���ļ����ļ��е�ѹ����
6��ʵ��ѹ���ļ��Ľ�ѹ��
7���ļ�����������ͼ�ν��档
*ע�Ȿ��ʵ����Ҫ�ύʵ�鱨�棨���������ʹ���
*/

public class FileManager {

    public FileManager() {

    }

    /**
     * �����ļ�����
     * @param copyfile Դ�ļ�
     * @param targetFolder Ŀ���ļ���
     * @return �Ƿ�ɹ�����
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
             return true;  //�����ļ��ɹ�

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
        return false;  //�����ļ���ʧ��
    }

    /**
     * �����ļ��в���
     * @param copyfile Դ�ļ���
     * @param targetFolder Ŀ���ļ���
     * @return �Ƿ��Ƴɹ�
     */
    public static boolean copyFolder(File copyfile, File targetFolder) {
        /**
         * ��Ҫ�ж�Ŀ���ļ��Ƿ�ΪԴ�ļ��е����ļ���
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
     *  ���ƶ༶�ļ���(��������㷨)
     * @param f Դ�ļ���
     * @param destFolder Ŀ���ļ���
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
     *  �����ļ�_���ڸ����ļ��з�������
     * @param srcFile Դ�ļ�
     * @param destFile Ŀ���ļ�
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
     * ������ǰ�ļ���
     * @param path �ļ���·��
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
//                    System.out.println(file.getName()+" "+getLastTime(file) + " �ļ���");
                }

            }
        }
    }

    /**
     * ��ȡĿ���ļ���С
     * @param file Ŀ���ļ�
     * @return �ļ���С��bt, kb, mk, gb��
     */
    public static String getFileSize(File file){
        //��ȡ�ļ���С
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
     * ��ȡ�ļ����ͣ���׺����
     * @param file Ŀ���ļ�
     * @return �ļ�����
     */
    public static String getFileType(File file){
        if(file.isDirectory()){
            return "�ļ���";
        }else{
            String filename = file.getName();
            //��ȡ�ļ�����(��׺)
            String[] strArray = filename.split("\\.");
            int suffixIndex = strArray.length -1;
            String mimeType = strArray[suffixIndex];
            mimeType = mimeType.toUpperCase(Locale.ROOT) + "�ļ�";
            return mimeType;
        }
    }

    /**
     * ��ȡ�����ļ���
     * @param file Ŀ���ļ�
     * @return �����ļ���
     */
    public static String getFileEncryptName(File file){
        String filename = file.getName();
        //��ȡ�ļ�����(��׺)
        String[] strArray = filename.split("\\.");
        String encryptName = "";
        int suffixIndex = strArray.length -1;
        for(int i = 0; i<strArray.length-1; i++){
            encryptName+=strArray[i];
            if(i!=strArray.length-2){
                encryptName+=".";
            }
        }
        encryptName+="(�����ļ�)" + ".";
        encryptName+=strArray[suffixIndex];
//        System.out.println(encryptName);
        return encryptName;
    }

    /**
     * ��ȡ�����ļ���
     * @param file Ŀ���ļ�
     * @return �����ļ���
     */
    public static String getFileDecryptName(File file){
        return file.getName().replace("(�����ļ�)", "(�����ļ�)");
    }

    /**
     * ��ȡ�ļ�����޸�ʱ��
     * @param file Ŀ���ļ�
     * @return �ļ�����޸�ʱ��
     */
    public static String getLastTime(File file){
        //��ȡ�ļ��޸�ʱ��
        long time = file.lastModified();
        Date d = new Date(time);
        Format simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String dateString = simpleFormat.format(d);
        return dateString;
    }

    /**
     * ����һ���µ��ļ�
     * @param fileName ���ļ�����
     * @return �����Ƿ�ɹ�
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
     * ����һ���µ��ļ���
     * @param FolderName ���ļ�����
     * @return �����Ƿ�ɹ�
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
     * �ļ��С��ļ�ѹ��
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
     * �ļ��С��ļ���ѹ
     */
    public static boolean uncompressFile(File file, File destFolder){
        String FilePath = file.getAbsolutePath();
        String newFileName = file.getName().substring(0, file.getName().lastIndexOf("."))+"(��ѹ�ļ�)";
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
     * ɾ�������ļ���
     * @param father Ŀ���ļ���
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
     * �����ļ�����
     * @param file ���ܶ����ļ�
     * @param destFolder Ŀ���ļ���
     * @return �Ƿ���ܳɹ�
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
     * �����ļ�����
     * @param file ���ܶ����ļ�
     * @param destFolder Ŀ���ļ���
     * @return �Ƿ���ܳɹ�
     */
    public static boolean decryptionFile(File file, File destFolder){
        if(!file.getName().contains("(�����ļ�)")){  //ֻ�ܶԼ����ļ����н��ܲ���
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
     * ���Ժ���
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(getFileDecryptName(new File("D:\\xc.lo.ve.hsy(�����ļ�).txt")));

    }

}
