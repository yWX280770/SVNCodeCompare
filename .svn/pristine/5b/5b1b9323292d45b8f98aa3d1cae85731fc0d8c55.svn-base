package com.chinasoft.model;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import com.chinasoft.props.Const;


/**
 * <logentry  revision="9">
 * <author>yanzhihao</author>
 * <date>2017-05-29T08:02:05.781290Z</date>
 * <paths>
 * <path prop-mods="false" text-mods="true" kind="file" action="M">/trunk/src/com/chinasoft/Const.java</path>
 * <path prop-mods="false" text-mods="true" kind="file" action="M">/trunk/src/com/chinasoft/Head.java</path>
 * <path prop-mods="false" text-mods="true" kind="file" action="M">/trunk/src/com/chinasoft/Model.java</path>
 * </paths>
 * <msg> 问题单号：DTS00000001\n 特性名称：解析字符串\n 问题影响版本：UXBXX\n 问题修复版本：UXBXX\n 修改人：yanzhihao\n 代码检视人：XXX，XXX</msg>
 * </logentry>
 */

public class LogEntry 
{
	//svn no
	int resision = Integer.MIN_VALUE;
	
	String author = null;
	
	long timestamp = Long.MIN_VALUE;
	
	List<FilePath> path = null;
	
	MsgText msg = null;

	public final void setResision(int resision) {
		this.resision = resision;
	}

	public final void setAuthor(String author) {
		this.author = author;
	}

	public final void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public final void setPath(List<FilePath> path) {
		this.path = path;
	}

	public final void setMsg(MsgText msg) {
		this.msg = msg;
	}

	public List<FilePath> getPath() {
		return path;
	}
	
	
	
	public final MsgText getMsg() {
		return msg;
	}

	public void parseLog(String... str) 
	{
		if (Const.NUM_3 <= str.length)
		{
			this.resision = Integer.parseInt(str[0]);
			this.author = str[1];
//			this.timestamp = Date.parse(str[2]);
			this.timestamp = System.currentTimeMillis()/1000;
		}
	}

	public final int getResision() 
	{
		return resision;
	}
	
	
	
}
