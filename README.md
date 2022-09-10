# fileManage
文件管理器的java实现

## 1. 简介

### 1.1. 项目需求

运用面向对象程序设计思想，基于Java文件管理和I/O框架，实现基于图形界面的GUI文件管理器。

### 1.2. 实现功能

1. 实现文件夹创建、删除、进入。
2. 实现当前文件夹下的内容罗列。
3. 实现文件拷贝和文件夹拷贝（文件夹拷贝指深度拷贝，包括所有子目录和文件）。
4. 实现指定文件的加密和解密。
5. 实现指定文件和文件夹的压缩。
6. 实现压缩文件的解压。
7. 文件管理器具有图形界面。

### 1.3. 开发平台

**开发语言：**
Java

**开发平台：**
Intellij IDEA 2021.2.2



## 2. 项目设计

### 2.1. MVC设计流程：

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021611627.gif)

1. MainFrame作为整个程序的主体，向用户展示GUI界面，同时接受用户的操作。通过响应事件调用FileManager中的文件操作方法，并从FileController中获取当前文件路径对GUI界面进行更新。
2. FileManager作为文件操作的主要对象，通过调用各种方法对文件进行操作，例如文件创建、删除、加密、解密等。
3. FileController中保存了文件管理器的当前节点信息以及各种设置信息，以提供MainFrame进行界面的更新。



### 2.2. 程序架构设计

#### 2.2.1. 整体结构框架

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021612017.gif)

---



#### 2.2.2. View结构

1. MyTable中包含了自己定义JTree中的部分构件；
2. MyTree中包含了自己定义的JTable中的部分构件；
3. MainFrame则为主界面使得程序具有图形界面，主界面分成了几个Panel和一个Menubar。其中ChangePositionPan负责文件管理器上方文本区域，以及跳转到对应文件夹或打开文件功能；
4. MyMenubar负责菜单栏的显示以及对应按钮的响应事件；
5. ReturnPanel中包含了两个按钮负责返回上一级界面以及磁盘界面；
6. TableScrollPanel通过JTable展示文件信息（名称、修改事件、类型、大小等）；
7. TreeScrollPanel通过JTree展示文件管理器的文件存储结构，并通过点击文件夹节点，使ManFrame界面跳转的对应的文件夹界面。

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021612021.gif)

 

对于Tree包，MyJTree负责显示目录树，MyJTreeNode负责构建目录树的结点并实现结点间的操作，而MyJTreeRender负责对目录树结点的图标渲染。

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021612029.gif)

 

对于Table包， MyJtableModel负责对数据进行整理，MyJTableCellRender负责对文件图标的渲染 

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021612034.gif)

---



#### 2.2.3. FileOperation结构

这个包中的的程序主要负责对文件的操作，其中FileController中保存了文件管理器的当前节点信息以及各种设置信息，以提供MainFrame进行界面的更新；

1. FileManager作为文件操作的主要对象，集成所有的文件操作，例如文件夹创建、删除、文件拷贝和文件夹拷贝等；
2. MainFrime通过调用FileManager中的方法间接调用其他几个类；
3. FileIcon负责获取文件的图标；
4. FileZip负责文件以及文件夹的压缩和解压缩；FileEncrypt通过IO流实现简单的文件加密操作； 

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021613183.gif)



### 2.3. UML类图

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021615668.jpg)

 

## 3. 项目展示

### 3.1. 主界面展示

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021624967.gif)



### 3.2. 创建、删除、进入

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021624582.gif)

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021625913.gif)

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021625247.gif)



### 3.3. 隐藏文件

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021625083.gif)



### 3.4. 文件压缩

![img](https://xc-figure.oss-cn-hangzhou.aliyuncs.com/img/202209021626015.gif)

---

更多功能请查看说明文档
