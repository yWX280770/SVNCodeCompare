package com.chinasoft.select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.chinasoft.model.FilePath;
import com.chinasoft.model.LogEntry;
import com.chinasoft.props.Const;


public class SelectModel 
{

	public static void select(List<LogEntry> list) 
	{
		String dir = null;
		
		Map<String, ArrayList<ArrayList<String>>> map = new HashMap<String, ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> filePath = null;
		for (LogEntry log : list)
		{
			dir = createDir(log);
			if (null == map.get(dir))
			{
				filePath = new ArrayList<ArrayList<String>>();
				map.put(dir, filePath);
			}
			else
			{
				filePath = map.get(dir);
			}
			
			ArrayList<ArrayList<String>> tmp = getTotalPath(log, dir);
			if(2 == tmp.size())
			{
				merge(filePath,tmp);
			}
			
		}
		
		downLoad(map);

		
	}
	
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
	
	private static void merge(ArrayList<ArrayList<String>> filePath, ArrayList<ArrayList<String>> tmp) 
	{
		
		if( 0 == filePath.size())
		{
			filePath.addAll(tmp);
			return;
		}
		
		if (0 < filePath.size() && filePath.size() == tmp.size())
		{
			for(int i=0;i<filePath.size();i++)
			{
				filePath.get(i).addAll(tmp.get(i));
			}
		}
		
		return;
	}

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
		
		File file = new File(buf.append(Const.COL).append(Const.NEW).toString());
		file.mkdirs();
		
		file = new File(dir+Const.COL+Const.OLD);
		file.mkdirs();
		
		return dir;
	}
	
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
