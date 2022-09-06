package FileOperation;

import java.io.*;

/**
 * @author ���
 * @version 1.0
 * @date 2021-11-20 13:33
 */

public class FileEncrypt {
    /**
     * �����ļ�
     *
     * @param srcFile Դ�ļ�
     * @param tarFile Ŀ���ļ�
     * @return
     */
    public static boolean EnFile(File srcFile, File tarFile) {
        BufferedReader B_reader = null;
        BufferedWriter B_writer = null;
        boolean flag = false;
        try {
            B_reader = new BufferedReader(new FileReader(srcFile));
            B_writer = new BufferedWriter(new FileWriter(tarFile));

            final byte Key = 11;
            int n = 0;
            while ((n = B_reader.read()) != -1) {
                B_writer.write(n ^ Key); // �ֽ�ֵ��50���м���
            }
            B_writer.flush();
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                B_reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                B_writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return flag;
    }

    /**
     * �����ļ�
     *
     * @param srcFile Դ�ļ�
     * @param tarFile Ŀ���ļ�
     */
    public static Boolean DecFile(File srcFile, File tarFile) {
        BufferedReader B_reader = null;
        BufferedWriter B_writer = null;
        boolean flag = false;
        try {
            B_reader = new BufferedReader(new FileReader(srcFile));
            B_writer = new BufferedWriter(new FileWriter(tarFile));

            final byte Key = 11;
            int n;
            while ((n = B_reader.read()) != -1) {
                B_writer.write(n ^ Key);
            }
            B_writer.flush();
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                B_reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                B_writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return flag;
    }
}



