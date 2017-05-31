package com.chinasoft;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.chinasoft.model.LogEntry;
import com.chinasoft.parse.PathsUtil;
import com.chinasoft.product.impl.Product;
import com.chinasoft.product.impl.XiaoFei;
import com.chinasoft.props.Const;
import com.chinasoft.select.SelectModel;

public class main 
{
//	/**
//	 * 问题单号：DTS123456789
//	 * 特性名称：XXX
//	 * 问题影响版本：UXBXX
//	 * 问题修复版本：UXBXX
//	 * 修改人：XXX
//	 * 代码检视人：XXX，XXX
//	 * @throws IOException 
//	 * 
//	 */
//	public static void main(String[] args)
//	{
//		try
//		{
////
////			File f=new File("F:\\python\\python\\msg.txt");   
////			List<LogEntry> list = PathsUtil.readXml(f);
//			long start = System.currentTimeMillis();
//			Process process = Runtime.getRuntime().exec(Const.SVN_LOG_XML);
//			InputStream reader = process.getInputStream();
//			List<LogEntry> list = PathsUtil.readInputStream(reader);
//			
//			Set<String> set = PathsUtil.readProps();	
//			
//			
//			SelectModel.select(list, set);
//			
//			long end = System.currentTimeMillis();
//			System.out.println(end-start);
//			
//		}
//		catch(IOException e)
//		{
//			
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally
//		{
//			
//		}
//	}


	public static void main(String[] args)
	{
		Thread thread = new Thread(new Product());
		thread.start();
		
		thread = new Thread(new XiaoFei());
		thread.setName("xiaofei");
		thread.start();
		
	}
		
}
