package com.chinasoft.select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.chinasoft.model.FilePath;
import com.chinasoft.model.LogEntry;
import com.chinasoft.props.Const;


public class SelectModel 
{

	/**
	 * 筛选并导出文件到本地对应目录
	 * @param list
	 * @param set
	 */
	public static void select(List<LogEntry> list,Set<String> set) 
	{
		if (null == set || 0 >= set.size())
		{
			return;
		}
		String dir = null;
		
		Map<String, ArrayList<ArrayList<String>>> map = new HashMap<String, ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> filePath = null;
		for (LogEntry log : list)
		{
			if (!jude(log,set))
			{
				continue;
			}
			
			dir = createDir(log);
//			if (null == map.get(dir))
//			{
//				filePath = new ArrayList<ArrayList<String>>();
//				map.put(dir, filePath);
//			}
//			else
//			{
//				filePath = map.get(dir);
//			}
			
			filePath = getTotalPath(log, dir);
			map.put(dir, filePath);
//			if(2 == tmp.size())
//			{
//				merge(filePath,tmp);
//			}
			
		}
		
		downLoad(map);

		
	}
	
	/**
	 * 判断是否下载当前svn的记录到本地
	 * @param log
	 * @param set
	 * @return
	 */
	private static boolean jude(LogEntry log, Set<String> set) 
	{
	
		String modif = log.getMsg().getModifyName().toLowerCase();
		for(String tmp : set)
		{
			if(modif.matches(tmp))
			{
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 下载新旧文件到本地
	 * @param map
	 */
	private static void downLoad(Map<String, ArrayList<ArrayList<String>>> map )
	{
		Runtime run = Runtime.getRuntime();
		for (Entry<String, ArrayList<ArrayList<String>>> set : map.entrySet()) 
		{
			ArrayList<ArrayList<String>> li = set.getValue();
			for(ArrayList<String> l : li)
			{
				for(String str : l)
				{
					try 
					{
						run.exec(str);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	/**
	 * 如果基础目录一样将待下载文件集合并
	 * @param filePath
	 * @param tmp
	 */
//	private static void merge(ArrayList<ArrayList<String>> filePath, ArrayList<ArrayList<String>> tmp) 
//	{
//		
//		if( 0 == filePath.size())
//		{
//			filePath.addAll(tmp);
//			return;
//		}
//		
//		if (0 < filePath.size() && filePath.size() == tmp.size())
//		{
//			for(int i=0;i<filePath.size();i++)
//			{
//				filePath.get(i).addAll(tmp.get(i));
//			}
//		}
//		
//		return;
//	}

	/**
	 * 创建待下载文件的根目录，并创建本地文件
	 * @param log
	 * @return
	 */
	private static String createDir(LogEntry log)
	{
		
		//时间秒数_提交人_单号
		StringBuffer buf = new StringBuffer();
		buf.append(Const.BASE_DIR)
		.append(System.currentTimeMillis()/1000)
		.append(Const.UNDERLINE)
		.append(log.getMsg().getModifyName())
		.append(Const.UNDERLINE)
		.append(log.getMsg().getDts());
		String dir =  buf.toString();
		
		int col = dir.length();
		File file = new File(buf.append(Const.COL).append(Const.NEW).toString());
		if(!file.exists())
		{
			file.mkdirs();
		}
		
		buf.setLength(col);
		file = new File(buf.append(Const.COL).append(Const.OLD).toString());
		if(!file.exists())
		{
			file.mkdirs();
		}
		
		return dir;
	}
	
	
	/**
	 * 获取每一个文件的完整svn记录
	 * @param log
	 * @param dir
	 * @return
	 */
	private static ArrayList<ArrayList<String>> getTotalPath(LogEntry log, String dir)
	{
		StringBuffer buf = new StringBuffer();
		List<FilePath> file = log.getPath();
		ArrayList<String> _new = new ArrayList<String>();
		ArrayList<String> old = new ArrayList<String>();
		
		ArrayList<ArrayList<String>> obj = new ArrayList<ArrayList<String>>();
		obj.add(_new);
		obj.add(old);
		
		
		for(FilePath f : file)
		{
			if (f.isFile() )
			{
				if(f.isActionAdd())
				{
					buf.setLength(0);
					buf.append(Const.SVN_HEAD)
					.append(log.getResision())
					.append(Const.BLANK)
					.append(Const.BASE_LINE)
					.append(f.getPath())
					.append("@")
					.append(log.getResision())
					.append(Const.BLANK)
					.append(dir)
					.append(Const.COL)
					.append(Const.NEW);
					_new.add(buf.toString());
					
				}
				else if(f.isActionModify() || f.isActionDel())
				{
					buf.setLength(0);
					buf.append(Const.SVN_HEAD)
					.append(log.getResision())
					.append(Const.BLANK)
					.append(Const.BASE_LINE)
					.append(f.getPath())
					.append("@")
					.append(log.getResision())
					.append(Const.BLANK)
					.append(dir)
					.append(Const.COL)
					.append(Const.NEW);
					_new.add(buf.toString());
					
					buf.setLength(0);
					buf.append(Const.SVN_HEAD)
					.append(log.getResision()-1)
					.append(Const.BLANK)
					.append(Const.BASE_LINE)
					.append(f.getPath())
					.append("@")
					.append(log.getResision()-1)
					.append(Const.BLANK)
					.append(dir)
					.append(Const.COL)
					.append(Const.OLD);
					old.add(buf.toString());
					
				}
				else if (f.isActionDel())
				{
					
				}
			}
		}
		return obj;
	}

	
	
	
}
