package com.chinasoft.parse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	private static void parseMsgText(MsgText msg,String t2 )
	{
		/**
		 *  问题单号：DTS00000003
 			特性名称：完成xml文本解析，对项目框架做了修改
 			问题影响版本：UXBXX
 			问题修复版本：UXBXX
 			修改人：yanzhihao
 			代码检视人：XXX，XXX
		 */
		String[] tmp = t2.split("\n");
		for(String t : tmp)
		{
			String t1 = t.trim();
			if (null != t1 && !"".equals(t1))
			{
				if (t1.startsWith("问题单号："))
				{
					msg.setDts(t1.substring(t1.indexOf("：") + 1));
				}
				else if (t1.startsWith("修改人："))
				{
					msg.setModifyName(t1.substring(t1.indexOf("：") + 1));
				}
				else
				{
					
				}
			}
			
		}
		
	}
	
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
	
	public static  List<LogEntry> readInputStream(InputStream input) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(input); 
		return parse(doc);
		
	}
	
	public static List<LogEntry> readXml(File file) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(file); 
		return parse(doc);
		
	}
}
