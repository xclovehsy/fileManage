package FileOperation;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author ���
 * @version 1.0
 * @date 2021-11-20 12:37
 */

public class FileZip {
    /**
     * zip�ļ�ѹ��
     * @param inputFile ��ѹ���ļ���/�ļ���
     * @param outputFile ���ɵ�ѹ��������
     */

    public static void ZipCompress(String inputFile, String outputFile) throws Exception {
        //����zip�����
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
        //�������������
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File input = new File(inputFile);
        compress(out, bos, input,null);
        bos.close();
        out.close();
    }
    /**
     * @param name ѹ���ļ���������дΪnull����Ĭ��
     */
    //�ݹ�ѹ��
    public static void compress(ZipOutputStream out, BufferedOutputStream bos, File input, String name) throws IOException {
        if (name == null) {
            name = input.getName();
        }
        //���·��ΪĿ¼���ļ��У�
        if (input.isDirectory()) {
            //ȡ���ļ����е��ļ��������ļ��У�
            File[] flist = input.listFiles();

            if (flist.length == 0)//����ļ���Ϊ�գ���ֻ����Ŀ�ĵ�zip�ļ���д��һ��Ŀ¼����
            {
                out.putNextEntry(new ZipEntry(name + "/"));
            } else//����ļ��в�Ϊ�գ���ݹ����compress���ļ����е�ÿһ���ļ������ļ��У�����ѹ��
            {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i], name + "/" + flist[i].getName());
                }
            }
        } else//�������Ŀ¼���ļ��У�����Ϊ�ļ�������д��Ŀ¼����㣬֮���ļ�д��zip�ļ���
        {
            out.putNextEntry(new ZipEntry(name));
            FileInputStream fos = new FileInputStream(input);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int len=-1;
            //��Դ�ļ�д�뵽zip�ļ���
            byte[] buf = new byte[1024];
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf,0,len);
            }
            bis.close();
            fos.close();
        }
    }

    /**
     * zip��ѹ
     * @param inputFile ����ѹ�ļ���
     * @param destDirPath  ��ѹ·��
     */
    public static void ZipUncompress(String inputFile,String destDirPath) throws Exception {
        File srcFile = new File(inputFile);//��ȡ��ǰѹ���ļ�
        // �ж�Դ�ļ��Ƿ����
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "��ָ�ļ�������");
        }
        //��ʼ��ѹ
        //������ѹ������
        ZipInputStream zIn = new ZipInputStream(new FileInputStream(srcFile));
        ZipEntry entry = null;
        File file = null;
        while ((entry = zIn.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                file = new File(destDirPath, entry.getName());
                if (!file.exists()) {
                    new File(file.getParent()).mkdirs();//�������ļ����ϼ�Ŀ¼
                }
                OutputStream out = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = zIn.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                // ����˳���ȴ򿪵ĺ�ر�
                bos.close();
                out.close();
            }
        }
    }


    public static void main(String[] args) {
        try {
            ZipCompress("D:\\Test", "D:\\TestbyYTT.zip");
            ZipUncompress("D:\\ytt.zip","D:\\ytt�Ľ�ѹ�ļ�");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

