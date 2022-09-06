package View.MyTree;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileFilter;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class MyJTreeNode implements TreeNode {
    //region MyJTreeNode - Variables
    /**
     * ��ǰ���
     */
    File file;

    /**
     * �ӽ��
     */
    private File[] children;

    /**
     * �����
     */
    private final TreeNode parent;

    /**
     * ϵͳ�̱�־
     */
    boolean isFileSystemRoot;
    //endregion MyJTreeNode - Variables

    //region MyJTreeNode - Functions
    /**
     * ��֧���Ĺ��췽��
     * @param file ��ǰ����File����
     * @param isFileSystemRoot �Ƿ���ϵͳ��
     * @param parent ��ǰ���ĸ����
     */
    public MyJTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
        this.file = file;
        this.isFileSystemRoot = isFileSystemRoot;
        this.parent = parent;

        // ��ȡ�ɶ���Ŀ¼��
        this.children = this.file.listFiles(new FileFilter() { // �����ࣺ�ļ������࣬���ļ��������ļ����ˣ�
            @Override
            public boolean accept(File pathname) {
                return (!pathname.isHidden() && pathname.isDirectory());
            }
        });

        if (this.children == null)
            this.children = new File[0];
    }

    /**
     * Ŀ¼�������Ĺ��췽��
     * @param children �����б�
     */
    public MyJTreeNode(File[] children) {
        this.file = null;
        this.parent = null;
        this.children = children;
    }

    /**
     * ��ȡ���е��ӽ��
     * @return ���е��ӽ��
     */
    @SuppressWarnings("unchecked")
    public Enumeration children() {
        final int elementCount = this.children.length;
        Enumeration<?> enumeration =  new Enumeration<File>() { // �����ࣻ
            int count = 0;

            /**
             * �ж��Ƿ���δ�������ӽ��
             * @return �Ƿ���δ�������ӽ��
             */
            public boolean hasMoreElements() {
                return this.count < elementCount;
            }

            /**
             * ��ȡ��һ���ӽ��
             * @return ��һ���ӽ��
             */
            public File nextElement() {
                if (this.count < elementCount) { // ����δ�������ӽ�㣻
                    return MyJTreeNode.this.children[this.count++];
                }

                // û��δ�������ӽ�㣻
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
        return enumeration;
    }

    /**
     * �жϵ�ǰ����Ƿ���Խ�������
     * @return �Ƿ��������
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * ��ȡ�����������ض�Ӧ���ӽ��
     * @param childIndex �ӽ�������
     * @return ��Ӧ���ӽ��
     */
    public TreeNode getChildAt(int childIndex) {
        return new MyJTreeNode(this.children[childIndex],
                this.parent == null, this);
    }

    /**
     * ��ȡ�ӽ�����Ŀ
     */
    public int getChildCount() {
        return this.children.length;
    }

    /**
     * ��ȡָ����������
     * @param node ָ�����
     * @return ��Ӧ������
     */
    public int getIndex(TreeNode node) {
        MyJTreeNode myJTreeNode = (MyJTreeNode) node;
        for (int i = 0; i < this.children.length; i++)
            if (myJTreeNode.file.equals(this.children[i]))
                return i;
        return -1;
    }

    /**
     * ��ȡ��ǰ���ĸ����
     * @return �����
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /**
     * �ж��Ƿ���Ҷ���
     * @return �Ƿ���Ҷ���
     */
    public boolean isLeaf() {
        return (this.getChildCount() == 0);
    }

    /**
     * �����תΪ�ַ���
     * @return ���ľ���·��
     */
    public String toString() {
        if(file != null)
            return file.getAbsolutePath();
        else
            return "";
    }
    //endregion MyJTreeNode - Functions
}