package com.chinasoft.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.chinasoft.model.FilePath;
import com.chinasoft.model.LogEntry;
import com.chinasoft.model.MsgText;
import com.chinasoft.props.Const;

public class Utils 
{

	/**
	 * 解析IO下载下来的文件DOM
	 * @param doc
	 * @return
	 */
	private static void parse(Document doc, Queue<LogEntry> queue)
	{
		NodeList nl = doc.getElementsByTagName("logentry");   
		LogEntry log = null;
		NodeList list = null;
		for (int i=0;i < nl.getLength();i++)
		{
			log = new LogEntry();

			parseLog(log,doc, i);

			list = doc.getElementsByTagName("paths").item(i).getChildNodes();
			for(int k=1;k<list.getLength();k+=2)
			{
				parsePath(log, list.item(k).getAttributes(), list.item(k).getTextContent().trim());
			}
			queue.offer(log);

		}
	}

	/**
	 * 解析每一个实体形成logEntry对象
	 * @param log
	 * @param doc
	 * @param i
	 */
	private static void parseLog(LogEntry log, Document doc, int i)
	{
		String t = doc.getElementsByTagName("logentry").item(i).getAttributes().getNamedItem("revision").getTextContent().trim();
		String t1 = doc.getElementsByTagName("author").item(i).getTextContent().trim();
		String t3 = doc.getElementsByTagName("date").item(i).getTextContent().trim();
		String t2 = doc.getElementsByTagName("msg").item(i).getTextContent().trim();
		log.parseLog(t,t1,t3);
		MsgText msg = null;
		if (null == log.getMsg())
		{
			msg = new MsgText();
			log.setMsg(msg);
		}
		else 
		{
			msg = log.getMsg();
		}

		parseMsgText(msg, t2);
	}

	/**
	 * 解析svn上的msg部分
	 * @param msg
	 * @param t2
	 */
	private static void parseMsgText(MsgText msg,String t2 )
	{
		/**
		 *  Defect：DTS00000003
	 			Defect：完成xml文本解析，对项目框架做了修改
	 			BaseLine：UXBXX
	 			FixLine：UXBXX
	 			Author：yanzhihao
	 			Reviewer：XXX，XXX
		 */
		String[] tmp = t2.split("\n");
		int loc = -1;
		for(int i=0;i<tmp.length;i++)
		{	
			String tm = tmp[i].trim();
			if(0 == i && -1 == (loc = tmp[i].indexOf("Defect")))
			{
				msg.setDefect(tm.substring(loc+1));
			}
			else if( -1 == (loc = tmp[i].indexOf("Defect")))
			{
				msg.setDefect1(tm.substring(loc+1));
			}
			else if( -1 == (loc = tmp[i].indexOf("BaseLine")))
			{
				msg.setBaseLine(tm.substring(loc+1));
			}
			else if( -1 == (loc = tmp[i].indexOf("FixLine")))
			{
				msg.setFixLine(tm.substring(loc+1));
			}
			else if( -1 == (loc = tmp[i].indexOf("Author")))
			{
				msg.setAuthor(tm.substring(loc+1));
			}
			else if( -1 == (loc = tmp[i].indexOf("Reviewer")))
			{
				msg.setReviewer(tm.substring(loc+1));
			}
		}

	}

	/**
	 * 解析其中文件路径部分，Path
	 * @param log
	 * @param namedNodeMap
	 * @param trim2
	 */
	private static void parsePath(LogEntry log,NamedNodeMap namedNodeMap, String trim2) 
	{
		List<FilePath> list = null;
		if (null == log.getPath())
		{
			list = new ArrayList<FilePath>();
			log.setPath(list);
		}
		else
		{
			list = log.getPath();
		}

		FilePath file = new FilePath();
		file.setKind(namedNodeMap.getNamedItem("kind").getTextContent().trim());
		file.setAct(namedNodeMap.getNamedItem("action").getTextContent().trim());
		file.setPath(trim2);
		list.add(file);


	}

	/**
	 * 通过IO流获取svn上所有的记录
	 * 
	 * @param input
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static  void readInputStream(InputStream input, Queue<LogEntry> queue) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(input); 
		parse(doc, queue);

	}

	/**
	 * 通过xml获取svn上所有的记录
	 * @param file
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void readXml(File file, Queue<LogEntry> queue) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(file); 
		parse(doc, queue);

	}

	/**
	 * 读取本地conf下面的comm配置文件，其中包含了带过滤的svn记录
	 * @return
	 */
	public static Set<String> readProps()
	{
		try
		{
			Properties props = new Properties();
			FileInputStream input = new FileInputStream(new File("conf/comm.properties"));
			props.load(input);
			String t = props.getProperty("userName");
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(t.toLowerCase().split(",")));
			return set;
		}
		catch(FileNotFoundException e)
		{

		}
		catch(IOException e)
		{

		}
		return null;

	}




	/**
	 * 筛选并导出文件到本地对应目录
	 * @param list
	 * @param set
	 */
	public static void select(LogEntry log,Set<String> set) 
	{
		if (null == set || 0 >= set.size())
		{
			return;
		}
		String dir = null;

		ArrayList<String> str = new ArrayList<String>();
		if (!jude(log,set))
		{
			return;
		}

		dir = createDir(log);

		getTotalPath(log, dir,str);
		downLoad(str);


	}

	private static void downLoad(ArrayList<String> str) {

		try
		{
			for(String s : str)
			{
				Runtime.getRuntime().exec(s);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void getTotalPath(LogEntry log, String dir,
			ArrayList<String> str) {


		StringBuffer buf = new StringBuffer();
		List<FilePath> file = log.getPath();

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
					.append(Const.STR_)
					.append(Const.BASE_LINE)
					.append(f.getPath())
					.append("@")
					.append(log.getResision())
					.append(Const.STR_)
					.append(Const.BLANK)
					.append(dir)
					.append(Const.COL)
					.append(Const.NEW);
					str.add(buf.toString());

				}
				else if(f.isActionModify() || f.isActionDel())
				{
					buf.setLength(0);
					buf.append(Const.SVN_HEAD)
					.append(log.getResision())
					.append(Const.BLANK)
					.append(Const.STR_)
					.append(Const.BASE_LINE)
					.append(f.getPath())
					.append("@")
					.append(log.getResision())
					.append(Const.STR_)
					.append(Const.BLANK)
					.append(dir)
					.append(Const.COL)
					.append(Const.NEW);
					str.add(buf.toString());

					buf.setLength(0);
					buf.append(Const.SVN_HEAD)
					.append(log.getResision()-1)
					.append(Const.BLANK)
					.append(Const.STR_)
					.append(Const.BASE_LINE)
					.append(f.getPath())
					.append("@")
					.append(log.getResision()-1)
					.append(Const.STR_)
					.append(Const.BLANK)
					.append(dir)
					.append(Const.COL)
					.append(Const.OLD);
					str.add(buf.toString());

				}

			}
		}
	}

	/**
	 * 判断是否下载当前svn的记录到本地
	 * @param log
	 * @param set
	 * @return
	 */
	private static boolean jude(LogEntry log, Set<String> set) 
	{

		String modif = log.getMsg().getAuthor().toLowerCase();
		for(String tmp : set)
		{
			if(modif.contains(tmp))
			{
				return true;
			}
		}
		return false;
	}

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
		.append(log.getMsg().getAuthor())
		.append(Const.UNDERLINE)
		.append(log.getMsg().getDefect1());
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
}
