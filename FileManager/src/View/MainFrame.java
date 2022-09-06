package View;

import FileOperation.FileController;
import FileOperation.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author ���
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
     * ���캯��
     */
    public MainFrame(){
        Load();
    }

    /**
     * ������Ҫ����
     */
    private void Load() {
        returnPanel = new ReturnPanel();
        changePositionPanel = new ChangePositionPanel(this);
        tableScrollPane = new TableScrollPanel(this);
        treeScrollPane = new TreeScrollPanel();
        gridBag = new GridBagLayout();

        setTitle("�ļ�������");
        setSize(1100,700);
        setLocation(400,200);
        setLayout(gridBag);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new MyMenubar(this));      //��Ӳ˵���

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
     * ���½��棬ͬʱ����TablePanel��ChangePositionPanel��renew����
     */
    public static void renew(){
        TableScrollPanel.renew();
        ChangePositionPanel.renew();
    }

    /**
     * �������ļ�����
     */
    public void createNewFile(){
        // ��ʾ����Ի���, �������������
        String fileName = JOptionPane.showInputDialog(
                this,
                "�½��ļ���:",
                ""
        );

        if(fileName == null){
            return;
        }

        if(fileName.compareTo("") == 0){
            JOptionPane.showMessageDialog(
                    this,
                    "���ļ�������Ϊ�գ�",
                    "�����ļ�",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if(FileManager.createFile(fileName)){
            // ��Ϣ�Ի����޷���, ����֪ͨ����
            String message = "�����ļ�\"" + fileName + "\"�ɹ���";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "�����ļ�",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            String message = "�����ļ�\"" + fileName + "\"ʧ�ܣ�";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "�����ļ�",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        MainFrame.renew();
    }

    /**
     * ���ļ����ļ��в���
     */
    public void openFile(File file){
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ���ļ��л����ļ�",
                    "���ļ�",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if(file.exists() && file.isFile()){
            String message = "�Ƿ�ȷ�����ļ�\"" + file.getName() + "\"?";
            int result = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "��ʾ",
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    message = "���ļ�\"" + file.getName() + "\"ʧ��!";
                    JOptionPane.showMessageDialog(
                            this,
                            message,
                            "���ļ�",
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
     * ɾ���ļ��С��ļ�����
     */
    public void removeFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ���ļ������ļ���",
                    "ɾ���ļ�or�ļ���",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if(file.exists()){
            String message = "";
            if(file.isFile()){
                message = "�ļ�";
            }else if(file.isDirectory()){
                message = "�ļ���";
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "�Ƿ�ȷ��ɾ��"+message+"\"" + file.getName() + "\"?",
                    "ɾ��"+message,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(file.isFile()){
                    if(file.delete()){  //ɾ���ļ�
                        JOptionPane.showMessageDialog(
                                this,
                                "ɾ��\""+ file.getName()+ "\"�ļ��ɹ���",
                                "ɾ���ļ�",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }else{
                        JOptionPane.showMessageDialog(
                                this,
                                "ɾ��\"" + file.getName() + "\"�ļ�ʧ�ܣ�",
                                "ɾ���ļ�",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }else if(file.isDirectory()){  //ɾ���ļ���

                    FileManager.removeFolder(file);
                    JOptionPane.showMessageDialog(
                            this,
                            "ɾ��\""+ file.getName()+ "\"�ļ��гɹ���",
                            "ɾ���ļ���",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            }

        }else{
            JOptionPane.showMessageDialog(
                    this,
                    "��ǰ�ļ��л��ļ�������",
                    "ɾ���ļ����ļ���",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        MainFrame.renew();  //ˢ�½���
    }

    /**
     * �������ļ��в���
     */
    public void createNewFolder(){
        // ��ʾ����Ի���, �������������
        String fileName = JOptionPane.showInputDialog(
                this,
                "�½��ļ�����:",
                ""
        );

        if(fileName == null){  //���ֱ�ӹرնԻ���
            return;
        }

        if(fileName.compareTo("") == 0){
            JOptionPane.showMessageDialog(
                    this,
                    "���ļ���������Ϊ�գ�",
                    "�����ļ���",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if(FileManager.createFolder(fileName)){
            // ��Ϣ�Ի����޷���, ����֪ͨ����
            String message = "�����ļ���\"" + fileName + "\"�ɹ���";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "�����ļ���",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            String message = "�����ļ���\"" + fileName + "\"ʧ�ܣ�";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "�����ļ���",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        MainFrame.renew();
    }

    /**
     * �ļ�����������
     */
    public void renameFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ���ļ������ļ���",
                    "�������ļ�or�ļ���",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if(file.exists()){
            String message = "";
            if(file.isFile()){
                message = "�ļ�";
            }else if(file.isDirectory()){
                message = "�ļ���";
            }
            String newName = JOptionPane.showInputDialog(
                    this,
                    "��������"+message+"��:",
                    file.getName()
            );
            if(newName == null){
                return;
            }
            if(newName == ""){
                JOptionPane.showMessageDialog(
                        this,
                        "��"+message+"������Ϊ��",
                        message+"������",
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
                        message+"�������ɹ���",
                        message+"������",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                        this,
                        message+"������ʧ�ܣ�",
                        message+"������",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }else{
            JOptionPane.showMessageDialog(
                    this,
                    "��ǰ�ļ��л��ļ�������",
                    "�������ļ����ļ���",
                    JOptionPane.WARNING_MESSAGE
            );
//            System.out.println(file.getAbsolutePath());
        }

        MainFrame.renew();  //ˢ�½���
    }

    /**
     * �����ļ��С��ļ�����
     */
    public void copyFile(){
        File copyfile = FileController.getCopyFile();
        if(copyfile == null || !copyfile.exists()){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ��Դ�ļ����ļ���",
                    "�����ļ�",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        File targetFolder = new File(FileController.getPath());
        String fileType = "";
        if(copyfile.isFile()){
            fileType = "�ļ�";
        }else if(copyfile.isDirectory()){
            fileType = "�ļ���";
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "�Ƿ�ȷ����" + fileType + "\""+ copyfile.getName() + "\"���Ƶ��ļ���\""
                        + targetFolder.getAbsolutePath() + "\"����",
                "�����ļ���",
                JOptionPane.YES_NO_OPTION
        );

        if(result == JOptionPane.YES_OPTION){
            boolean flag = false;
            if(copyfile.isFile()){
                /**
                 * �ж��Ƿ��������ļ�
                 */
                File targetFile = new File(targetFolder.getAbsolutePath()+File.separator+copyfile.getName());
                if(targetFile.exists()){
                    JOptionPane.showMessageDialog(
                            this,
                            "Ŀ���ļ������������ļ�",
                            "����ʧ��",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                flag = FileManager.copyFile(copyfile, targetFolder);
            }else if(copyfile.isDirectory()){

                /**
                 * �ж��Ƿ��������ļ���
                 */
                File targetFile = new File(targetFolder.getAbsolutePath()+File.separator+copyfile.getName());
                if(targetFile.exists()){
                    JOptionPane.showMessageDialog(
                            this,
                            "Ŀ���ļ������������ļ���",
                            "����ʧ��",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                /**
                 * ��Ҫ�ж�Ŀ���ļ��Ƿ�ΪԴ�ļ��е����ļ���
                 */
                String copyFilePath = copyfile.getAbsolutePath();
                String targetFilePath = targetFolder.getAbsolutePath();
                if(copyFilePath.contains(targetFilePath)){
                    JOptionPane.showMessageDialog(
                            this,
                            "Ŀ���ļ�����ΪԴ�ļ��е����ļ���",
                            "����ʧ��",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                flag = FileManager.copyFolder(copyfile, targetFolder);
            }

            if(flag){
                JOptionPane.showMessageDialog(
                        this,
                        "���Ƴɹ���",
                        "����" + fileType,
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                        this,
                        "����ʧ�ܣ�",
                        "����" + fileType,
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
        MainFrame.renew();  //ˢ�½���
    }

    /**
     * ѹ���ļ�����
     */
    public void compressFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ���ļ����ļ���",
                    "�ļ����ļ���ѹ��",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            String fileType = "";
            if(file.isDirectory()){
                fileType = "�ļ���";
            }else {
                fileType = "�ļ�";
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "�Ƿ�ȷ��ѹ��"+fileType +"\"" +file.getName()+ "\"?",
                    "ѹ��"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.compressFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ�ѹ���ɹ�",
                            "�ļ�ѹ��",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ�ѹ��ʧ��",
                            "�ļ�ѹ��",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //ˢ�½���
    }

    /**
     * ��ѹ�ļ����ļ���
     */
    public void uncompressFile(){
        File file = TableScrollPanel.getSelectFile();

        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ���ļ����ļ���",
                    "�ļ����ļ��н�ѹ",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            if(!file.getName().contains(".zip")){
                JOptionPane.showMessageDialog(
                        this,
                        "���ļ����ļ��в��ܽ�ѹ",
                        "�ļ����ļ��н�ѹ",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String fileType = "";
            if(file.isDirectory()){
                fileType = "�ļ���";
            }else {
                fileType = "�ļ�";
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "�Ƿ�ȷ����ѹ"+fileType +"\"" +file.getName()+ "\"?",
                    "��ѹ"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.uncompressFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ���ѹ�ɹ�",
                            "�ļ���ѹ",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ���ѹʧ��",
                            "�ļ���ѹ",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //ˢ�½���
    }

    /**
     * �����ļ�����
     */
    public void encryptFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ����Ҫ���ܵ��ļ�",
                    "�ļ�����",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            String fileType = "�ļ�";
            if(file.getName().contains("(�����ļ�)")){
                JOptionPane.showMessageDialog(
                        this,
                        "���ܶ��Ѿ������ļ����м��ܲ���",
                        "����ʧ��",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "�Ƿ�ȷ������"+fileType +"\"" +file.getName()+ "\"?",
                    "����"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.encryptionFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ����ܳɹ�",
                            "�ļ�����",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ�����ʧ��",
                            "�ļ�����",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //ˢ�½���
    }

    /**
     * �����ļ�����
     */
    public void decryptFile(){
        File file = TableScrollPanel.getSelectFile();
        if(file == null){
            JOptionPane.showMessageDialog(
                    this,
                    "��ѡ��һ����Ҫ���ܵ��ļ�",
                    "�ļ�����",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }else{
            String fileType = "�ļ�";
            if(!file.getName().contains("(�����ļ�)")){
                JOptionPane.showMessageDialog(
                        this,
                        "ֻ�ܶԼ����ļ����н��ܲ���",
                        "����ʧ��",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "�Ƿ�ȷ������"+fileType +"\"" +file.getName()+ "\"?",
                    "����"+ fileType,
                    JOptionPane.YES_NO_OPTION
            );

            if(result == JOptionPane.YES_OPTION){
                if(FileManager.decryptionFile(file, new File(FileController.getPath()))){
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ����ܳɹ�",
                            "�ļ�����",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            this,
                            "�ļ�����ʧ��",
                            "�ļ�����",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        }
        renew();  //ˢ�½���
    }

    /**
     * ����Menubar�в��ְ�ť�Ƿ���Ч
     */
    public static void setButtonOFF(){
        MyMenubar.setOFF();
    }

    public static void setButtonOpen(){
        MyMenubar.setOpen();
    }



    /**
     * ���Ժ���
     * @param args
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

}




