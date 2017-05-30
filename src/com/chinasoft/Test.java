package com.chinasoft;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Test 
{

	public static void main(String[] args) throws IOException 
	{
		Properties props = new Properties();
		FileInputStream input = new FileInputStream(new File("conf/comm.properties"));
		props.load(input);
		String t = props.getProperty("userName");
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(t.toLowerCase().split(",")));
		
		System.out.println(set);
	}
}
