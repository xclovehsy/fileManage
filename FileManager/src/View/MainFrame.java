package View;

import FileOperation.FileController;
import FileOperation.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-05 19:55
 */

public class MainFrame extends JFrame {
    public static ReturnPanel returnPanel = null;
    public static ChangePositionPanel changePositionPanel = null;
    public static TableScrollPanel tableScrollPane = null;
    public static TreeScrollPanel treeScrollPane = null;
    public GridBagLayout gridBag = null;
    public GridBagConstraints c = null;

    /**
     * 构造函数
     */
    public MainFrame(){
        Load();
    }

    /**
     * 加载主要界面
     */
    private void Load() {
        returnPanel = new ReturnPanel();
        changePositionPanel = new ChangePositionPanel(this);
        tableScrollPane = new TableScrollPanel(this);
        treeScrollPane = new TreeScrollPanel();
        gridBag = new GridBagLayout();

        setTitle("文件管理器");
        setSize(1100,700);
        setLocation(400,200);
        setLayout(gridBag);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new MyMenubar(this));      //添加菜单栏

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(returnPanel, c);

        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 3;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(changePositionPanel, c);

        c = new GridBagConstraints();
        c.weighty = 6;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(treeScrollPane, c);

        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 3;
        c.weighty = 6;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(tableScrollPane, c);

        add(returnPanel);
        add(changePositionPanel);
        add(treeScrollPane);
        add(tableScrollPane);
    }

    /**
     * 更新界面，同时调用TablePanel和ChangePositionPanel的renew函数
     */
    public static void renew(){
        TableScrollPanel.renew();
        ChangePositionPanel.renew();
    }

    /**
     * 创建新文件操作
     */
    public void createNewFile(){
        // 显示输入对话框, 返回输入的内容
        String fileName = JOptionPane.showInputDialog(
                this,
                "新建文件名:",
                ""
        );

        if(fileName == null){
            return;
        }

        if(fileName.compareTo("") == 0){
            JOptionPane.showMessageDialog(
                    this,
                    "新文件名不能为空！",
                    "创建文件",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if(FileManager.createFile(fileName)){
            // 消息对话框无返回, 仅做通知作用
            String message = "创建文件\"" + fileName + "\"成功！";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "创建文件",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            String message = "创建文件\"" + fileName + "\"失败！";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "创建文件",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        MainFrame.renew();
    }

    /**
     * 打开文件、文件夹操作
     */
    public void openFile(File file){
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个文件夹或者文件",
                    "打开文件",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if(file.exists() && file.isFile()){
            String message = "是否确定打开文件\"" + file.getName() + "\"?";
            int result = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "提示",
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    message = "打开文件\"" + file.getName() + "\"失败!";
                    JOptionPane.showMessageDialog(
                            this,
                            message,
                            "打开文件",
                            JOptionPane.WARNING_MESSAGE
                    );
                    e.printStackTrace();
                }
            }
        }else if(file.exists() && file.isDirectory()){
            FileController.setPath(file.getAbsolutePath()+"\\");
            renew();
        }
    }

    /**
     * 删除文件夹、文件操作
     */
    public void removeFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个文件或者文件夹",
                    "删除文件or文件夹",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if(file.exists()){
            String message = "";
            if(file.isFile()){
                message = "文件";
            }else if(file.isDirectory()){
                message = "文件夹";
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "是否确定删除"+message+"\"" + file.getName() + "\"?",
                    "删除"+message,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(file.isFile()){
                    if(file.delete()){  //删除文件
                        JOptionPane.showMessageDialog(
                                this,
                                "删除\""+ file.getName()+ "\"文件成功！",
                                "删除文件",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }else{
                        JOptionPane.showMessageDialog(
                                this,
                                "删除\"" + file.getName() + "\"文件失败！",
                                "删除文件",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }else if(file.isDirectory()){  //删除文件夹

                    FileManager.removeFolder(file);
                    JOptionPane.showMessageDialog(
                            this,
                            "删除\""+ file.getName()+ "\"文件夹成功！",
                            "删除文件夹",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            }

        }else{
            JOptionPane.showMessageDialog(
                    this,
                    "当前文件夹或文件不存在",
                    "删除文件、文件夹",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        MainFrame.renew();  //刷新界面
    }

    /**
     * 创建新文件夹操作
     */
    public void createNewFolder(){
        // 显示输入对话框, 返回输入的内容
        String fileName = JOptionPane.showInputDialog(
                this,
                "新建文件夹名:",
                ""
        );

        if(fileName == null){  //如果直接关闭对话框
            return;
        }

        if(fileName.compareTo("") == 0){
            JOptionPane.showMessageDialog(
                    this,
                    "新文件夹名不能为空！",
                    "创建文件夹",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if(FileManager.createFolder(fileName)){
            // 消息对话框无返回, 仅做通知作用
            String message = "创建文件夹\"" + fileName + "\"成功！";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "创建文件夹",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            String message = "创建文件夹\"" + fileName + "\"失败！";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "创建文件夹",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        MainFrame.renew();
    }

    /**
     * 文件重命名操作
     */
    public void renameFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个文件或者文件夹",
                    "重命名文件or文件夹",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if(file.exists()){
            String message = "";
            if(file.isFile()){
                message = "文件";
            }else if(file.isDirectory()){
                message = "文件夹";
            }
            String newName = JOptionPane.showInputDialog(
                    this,
                    "请输入新"+message+"名:",
                    file.getName()
            );
            if(newName == null){
                return;
            }
            if(newName == ""){
                JOptionPane.showMessageDialog(
                        this,
                        "新"+message+"名不能为空",
                        message+"重命名",
                        JOptionPane.WARNING_MESSAGE
                );
                renameFile();
                return;
            }

            String rootPath = file.getParent();
            File newFile = new File(rootPath+File.separator+newName);

            if(file.renameTo(newFile)){
                JOptionPane.showMessageDialog(
                        this,
                        message+"重命名成功！",
                        message+"重命名",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                        this,
                        message+"重命名失败！",
                        message+"重命名",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }else{
            JOptionPane.showMessageDialog(
                    this,
                    "当前文件夹或文件不存在",
                    "重命名文件、文件夹",
                    JOptionPane.WARNING_MESSAGE
            );
//            System.out.println(file.getAbsolutePath());
        }

        MainFrame.renew();  //刷新界面
    }

    /**
     * 复制文件夹、文件操作
     */
    public void copyFile(){
        File copyfile = FileController.getCopyFile();
        if(copyfile == null || !copyfile.exists()){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个源文件或文件夹",
                    "复制文件",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        File targetFolder = new File(FileController.getPath());
        String fileType = "";
        if(copyfile.isFile()){
            fileType = "文件";
        }else if(copyfile.isDirectory()){
            fileType = "文件夹";
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "是否确定将" + fileType + "\""+ copyfile.getName() + "\"复制到文件夹\""
                        + targetFolder.getAbsolutePath() + "\"中吗？",
                "复制文件夹",
                JOptionPane.YES_NO_OPTION
        );

        if(result == JOptionPane.YES_OPTION){
            boolean flag = false;
            if(copyfile.isFile()){
                /**
                 * 判断是否有重名文件
                 */
                File targetFile = new File(targetFolder.getAbsolutePath()+File.separator+copyfile.getName());
                if(targetFile.exists()){
                    JOptionPane.showMessageDialog(
                            this,
                            "目标文件夹中有重名文件",
                            "复制失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                flag = FileManager.copyFile(copyfile, targetFolder);
            }else if(copyfile.isDirectory()){

                /**
                 * 判断是否有重名文件夹
                 */
                File targetFile = new File(targetFolder.getAbsolutePath()+File.separator+copyfile.getName());
                if(targetFile.exists()){
                    JOptionPane.showMessageDialog(
                            this,
                            "目标文件夹中有重名文件夹",
                            "复制失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                /**
                 * 需要判断目标文件是否为源文件夹的子文件夹
                 */
                String copyFilePath = copyfile.getAbsolutePath();
                String targetFilePath = targetFolder.getAbsolutePath();
                if(copyFilePath.contains(targetFilePath)){
                    JOptionPane.showMessageDialog(
                            this,
                            "目标文件不能为源文件夹的子文件夹",
                            "复制失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                flag = FileManager.copyFolder(copyfile, targetFolder);
            }

            if(flag){
                JOptionPane.showMessageDialog(
                        this,
                        "复制成功！",
                        "复制" + fileType,
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                        this,
                        "复制失败！",
                        "复制" + fileType,
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
        MainFrame.renew();  //刷新界面
    }

    /**
     * 压缩文件操作
     */
    public void compressFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个文件或文件夹",
                    "文件、文件夹压缩",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            String fileType = "";
            if(file.isDirectory()){
                fileType = "文件夹";
            }else {
                fileType = "文件";
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "是否确定压缩"+fileType +"\"" +file.getName()+ "\"?",
                    "压缩"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.compressFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "文件压缩成功",
                            "文件压缩",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "文件压缩失败",
                            "文件压缩",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //刷新界面
    }

    /**
     * 解压文件、文件夹
     */
    public void uncompressFile(){
        File file = TableScrollPanel.getSelectFile();

        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个文件或文件夹",
                    "文件、文件夹解压",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            if(!file.getName().contains(".zip")){
                JOptionPane.showMessageDialog(
                        this,
                        "该文件或文件夹不能解压",
                        "文件、文件夹解压",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String fileType = "";
            if(file.isDirectory()){
                fileType = "文件夹";
            }else {
                fileType = "文件";
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "是否确定解压"+fileType +"\"" +file.getName()+ "\"?",
                    "解压"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.uncompressFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "文件解压成功",
                            "文件解压",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "文件解压失败",
                            "文件解压",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //刷新界面
    }

    /**
     * 加密文件操作
     */
    public void encryptFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个需要加密的文件",
                    "文件加密",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            String fileType = "文件";
            if(file.getName().contains("(加密文件)")){
                JOptionPane.showMessageDialog(
                        this,
                        "不能对已经加密文件进行加密操作",
                        "加密失败",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "是否确定加密"+fileType +"\"" +file.getName()+ "\"?",
                    "加密"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.encryptionFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "文件加密成功",
                            "文件加密",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "文件加密失败",
                            "文件加密",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //刷新界面
    }

    /**
     * 解密文件操作
     */
    public void decryptFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "请选择一个需要解密的文件",
                    "文件解密",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            String fileType = "文件";
            if(!file.getName().contains("(加密文件)")){
                JOptionPane.showMessageDialog(
                        this,
                        "只能对加密文件进行解密操作",
                        "解密失败",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "是否确定解密"+fileType +"\"" +file.getName()+ "\"?",
                    "解密"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.decryptionFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "文件解密成功",
                            "文件解密",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "文件解密失败",
                            "文件解密",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //刷新界面
    }

    /**
     * 设置Menubar中部分按钮是否有效
     */
    public static void setButtonOFF(){
        MyMenubar.setOFF();
    }

    public static void setButtonOpen(){
        MyMenubar.setOpen();
    }



    /**
     * 测试函数
     * @param args
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

}




