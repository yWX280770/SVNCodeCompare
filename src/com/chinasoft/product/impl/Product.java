package com.chinasoft.product.impl;

import java.io.File;
import java.util.Queue;

import com.chinasoft.model.LogEntry;
import com.chinasoft.product.AbstractRun;
import com.chinasoft.product.Utils;

public class Product extends AbstractRun
{

	@Override
	public void run() {
		try
		{
			Queue<LogEntry> queue = getQueue();
			System.err.println("product start");
			File f=new File("F:\\python\\python\\msg.txt");
			Utils.readXml(f, queue);
			System.err.println("product end");
		}
		catch(Exception e)
		{
			System.err.println("error product end");
		}
	}



}
