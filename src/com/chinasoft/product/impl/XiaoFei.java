package com.chinasoft.product.impl;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.chinasoft.model.LogEntry;
import com.chinasoft.parse.PathsUtil;
import com.chinasoft.product.AbstractRun;

public class XiaoFei extends AbstractRun
{

	@Override
	public void run() {
		LinkedBlockingQueue<LogEntry> queue = getQueue();


		try
		{
			Set<String> set = PathsUtil.readProps();
			ScheduledThreadPoolExecutor executor = new java.util.concurrent.ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors()+1);
			while(true)
			{
				System.err.println("xiaofei start");
				LogEntry log = queue.take();
				executor.execute(new DownLoad(log,set));
				System.err.println("xiaofei end"+log);
			}
		}
		catch(Exception e)
		{

		}
	}

}
