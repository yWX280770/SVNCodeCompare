package com.chinasoft.model;

/**
 * <msg> 
 * ���ⵥ�ţ�DTS00000001
 * �������ƣ������ַ���\n 
 * ����Ӱ��汾��UXBXX\n 
 * �����޸��汾��UXBXX\n 
 * �޸��ˣ�yanzhihao\n 
 * ��������ˣ�XXX��XXX
 * </msg>
 *
 */
public class MsgText 
{

	private String dts;
	
	private String modifyName;

	public final void setDts(String dts) {
		this.dts = dts;
	}

	public final void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public final String getDts() {
		return dts;
	}

	public final String getModifyName() {
		return modifyName;
	}
	
	
	
}
