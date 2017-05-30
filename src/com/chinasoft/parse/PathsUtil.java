package com.chinasoft.parse;

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

public class PathsUtil 
{

	
	/**
	 * ����IO�����������ļ�DOM
	 * @param doc
	 * @return
	 */
	private static List<LogEntry> parse(Document doc)
	{
		NodeList nl = doc.getElementsByTagName("logentry");   
		LogEntry log = null;
		List<LogEntry> entryList = new ArrayList<LogEntry>();
		NodeList list = null;
		for (int i=0;i < nl.getLength();i++)
		{
			log = new LogEntry();
			entryList.add(log);
			PathsUtil.parseLog(log,doc, i);
			
			list = doc.getElementsByTagName("paths").item(i).getChildNodes();
			for(int k=1;k<list.getLength();k+=2)
			{
				PathsUtil.parsePath(log, list.item(k).getAttributes(), list.item(k).getTextContent().trim());
			}
			
			
			
		}
		return entryList;
	}

	/**
	 * ����ÿһ��ʵ���γ�logEntry����
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
	 * ����svn�ϵ�msg����
	 * @param msg
	 * @param t2
	 */
	private static void parseMsgText(MsgText msg,String t2 )
	{
		/**
		 *  ���ⵥ�ţ�DTS00000003
 			�������ƣ����xml�ı�����������Ŀ��������޸�
 			����Ӱ��汾��UXBXX
 			�����޸��汾��UXBXX
 			�޸��ˣ�yanzhihao
 			��������ˣ�XXX��XXX
		 */
		String[] tmp = t2.split("\n");
		for(String t : tmp)
		{
			String t1 = t.trim();
			if (null != t1 && !"".equals(t1))
			{
				if (t1.startsWith("���ⵥ�ţ�"))
				{
					msg.setDts(t1.substring(t1.indexOf("��") + 1));
				}
				else if (t1.startsWith("�޸��ˣ�"))
				{
					msg.setModifyName(t1.substring(t1.indexOf("��") + 1));
				}
				else
				{
					
				}
			}
			
		}
		
	}
	
	/**
	 * ���������ļ�·�����֣�Path
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
	 * ͨ��IO����ȡsvn�����еļ�¼
	 * 
	 * @param input
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static  List<LogEntry> readInputStream(InputStream input) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(input); 
		return parse(doc);
		
	}
	
	/**
	 * ͨ��xml��ȡsvn�����еļ�¼
	 * @param file
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<LogEntry> readXml(File file) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(file); 
		return parse(doc);
		
	}
	
	/**
	 * ��ȡ����conf�����comm�����ļ������а����˴����˵�svn��¼
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
}
