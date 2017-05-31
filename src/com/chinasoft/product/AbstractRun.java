package com.chinasoft.product;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.chinasoft.model.LogEntry;

public abstract class AbstractRun implements Runnable
{
	
	private static LinkedBlockingQueue<LogEntry> queue = new LinkedBlockingQueue<LogEntry>();
	
	public final LinkedBlockingQueue<LogEntry> getQueue()
	{
		return AbstractRun.queue;
	}

	 public class DownLoad extends Thread
	{
		
		LogEntry log = null;
		Set<String> set = null;
		
		public DownLoad(LogEntry log , Set<String> set) 
		{
			this.log = log;
			this.set = set;
		}
		
		@Override
		public void run() 
		{
			if(null != log)
			{
				Utils.select(log, set);
			}
			
		}
	}

}
