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
//	 * ���ⵥ�ţ�DTS123456789
//	 * �������ƣ�XXX
//	 * ����Ӱ��汾��UXBXX
//	 * �����޸��汾��UXBXX
//	 * �޸��ˣ�XXX
//	 * ��������ˣ�XXX��XXX
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
