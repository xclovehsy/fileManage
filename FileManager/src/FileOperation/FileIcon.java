package FileOperation;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileIcon {
	private static final FileSystemView fsv = FileSystemView.getFileSystemView(); // 获取当前系统文件视图接口的实例；

	/**
	 * 获取当前文件或文件夹的图标
	 * @param path 当前文件或文件夹所处的路径
	 * @return 返回当前文件夹或文件的图标
	 */
	public static Icon getFileIcon(String path) {
		File file = new File(path);
		return fsv.getSystemIcon(file);// 获取该文件的icon；
	}
	
	/**
	 * 获取子目录及子文件的图标
	 * @param dirPath 当前文件夹所处的路径
	 * @return 返回子文件夹及子文件的图标
	 */
	public static Map<String, Icon> getAllFileIcon(String dirPath) {
		Icon icon = null;									     // 某个文件或文件夹或磁盘分区对应的icon；
		HashMap<String, Icon> iconMap = new HashMap<String, Icon>();

		if (dirPath.equals("")) 	// 当前路径为根目录，返回各个磁盘分区的icon；
		{
			File[] disks = File.listRoots();
			for (File disk: disks)
			{
				icon = fsv.getSystemIcon(disk);
				iconMap.put(disk.getName(), icon);
			}
		}
		else // 当前路径不是根目录；
		{
			File currFile = new File(dirPath);	 // 当前文件夹；
			File[] files = currFile.listFiles(); // 子文件和子文件夹；
			if (files != null) // 文件夹不空；
			{
				for (File file: files) // 将子文件及子文件夹的icon加入；
				{
						icon = fsv.getSystemIcon(file);
						iconMap.put(file.getName(), icon);
				}
			}
		}
		return iconMap;
	}
}
