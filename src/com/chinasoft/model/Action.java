package com.chinasoft.model;

public enum Action 
{

	//�޸�
	M(0),
	
	//����
	A(1),
	
	//delete
	D(2),
	
	//δ֪
	UNKNOWN(100);
	
	int value = -1;
	
	private Action(int i) 
	{
		this.value = i;
	}

	public static Action parseAction(String act) 
	{
		switch(act)
		{
			case "M":
				return M;
			case "A":
				return A;
			default:
				return UNKNOWN;
		}
	}
}
