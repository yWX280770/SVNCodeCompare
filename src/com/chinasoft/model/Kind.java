package com.chinasoft.model;

public enum Kind 
{
	//�ļ���
	DIR(0),
	
	//�ļ�
	FILE(1),
	
	//δ֪
	UNKNOWN(-1);
	
	Kind(int i)
	{
		this.value = i;
	}
	int value = Integer.MIN_VALUE;
	
}
