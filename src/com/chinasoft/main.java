package com.chinasoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.chinasoft.model.LogEntry;
import com.chinasoft.parse.PathsUtil;
import com.chinasoft.props.Const;
import com.chinasoft.select.SelectModel;

public class main 
{
	/**
	 * 问题单号：DTS123456789
	 * 特性名称：XXX
	 * 问题影响版本：UXBXX
	 * 问题修复版本：UXBXX
	 * 修改人：XXX
	 * 代码检视人：XXX，XXX
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args)
	{
		try
		{
//
//			File f=new File("F:\\python\\python\\msg.txt");   
//			List<LogEntry> list = PathsUtil.readXml(f);
			long start = System.currentTimeMillis();
			Process process = Runtime.getRuntime().exec(Const.SVN_LOG_XML);
			InputStream reader = process.getInputStream();
			List<LogEntry> list = PathsUtil.readInputStream(reader);
			SelectModel.select(list);
			
			long end = System.currentTimeMillis();
			System.out.println(end-start);
			
		}
		catch(IOException e)
		{
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
	}
}
