package View.MyTree;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileFilter;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class MyJTreeNode implements TreeNode {
    //region MyJTreeNode - Variables
    /**
     * 当前结点
     */
    File file;

    /**
     * 子结点
     */
    private File[] children;

    /**
     * 父结点
     */
    private final TreeNode parent;

    /**
     * 系统盘标志
     */
    boolean isFileSystemRoot;
    //endregion MyJTreeNode - Variables

    //region MyJTreeNode - Functions
    /**
     * 分支结点的构造方法
     * @param file 当前结点的File对象
     * @param isFileSystemRoot 是否是系统盘
     * @param parent 当前结点的父结点
     */
    public MyJTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
        this.file = file;
        this.isFileSystemRoot = isFileSystemRoot;
        this.parent = parent;

        // 获取可读子目录；
        this.children = this.file.listFiles(new FileFilter() { // 匿名类：文件过滤类，将文件、隐藏文件过滤；
            @Override
            public boolean accept(File pathname) {
                return (!pathname.isHidden() && pathname.isDirectory());
            }
        });

        if (this.children == null)
            this.children = new File[0];
    }

    /**
     * 目录树根结点的构造方法
     * @param children 磁盘列表
     */
    public MyJTreeNode(File[] children) {
        this.file = null;
        this.parent = null;
        this.children = children;
    }

    /**
     * 获取所有的子结点
     * @return 所有的子结点
     */
    @SuppressWarnings("unchecked")
    public Enumeration children() {
        final int elementCount = this.children.length;
        Enumeration<?> enumeration =  new Enumeration<File>() { // 匿名类；
            int count = 0;

            /**
             * 判断是否还有未遍历的子结点
             * @return 是否还有未遍历的子结点
             */
            public boolean hasMoreElements() {
                return this.count < elementCount;
            }

            /**
             * 获取下一个子结点
             * @return 下一个子结点
             */
            public File nextElement() {
                if (this.count < elementCount) { // 还有未遍历的子结点；
                    return MyJTreeNode.this.children[this.count++];
                }

                // 没有未遍历的子结点；
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
        return enumeration;
    }

    /**
     * 判断当前结点是否可以进行生长
     * @return 是否可以生长
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * 获取根据索引返回对应的子结点
     * @param childIndex 子结点的索引
     * @return 对应的子结点
     */
    public TreeNode getChildAt(int childIndex) {
        return new MyJTreeNode(this.children[childIndex],
                this.parent == null, this);
    }

    /**
     * 获取子结点的数目
     */
    public int getChildCount() {
        return this.children.length;
    }

    /**
     * 获取指定结点的索引
     * @param node 指定结点
     * @return 对应的索引
     */
    public int getIndex(TreeNode node) {
        MyJTreeNode myJTreeNode = (MyJTreeNode) node;
        for (int i = 0; i < this.children.length; i++)
            if (myJTreeNode.file.equals(this.children[i]))
                return i;
        return -1;
    }

    /**
     * 获取当前结点的父结点
     * @return 父结点
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /**
     * 判断是否是叶结点
     * @return 是否是叶结点
     */
    public boolean isLeaf() {
        return (this.getChildCount() == 0);
    }

    /**
     * 将结点转为字符串
     * @return 结点的绝对路径
     */
    public String toString() {
        if(file != null)
            return file.getAbsolutePath();
        else
            return "";
    }
    //endregion MyJTreeNode - Functions
}