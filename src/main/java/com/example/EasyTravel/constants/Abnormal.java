package com.example.EasyTravel.constants;

public enum Abnormal {
	//依照車子狀況判斷是該維修還是檢查即可
	A01("A01", "無法啟動!"), 
	A02("A02", "車體損悔!"), 
	A03("A03", " 爆胎!"),
	B01("B01", "燈號異常!"), 
	B02("B02", " 操作異常!"),
	B03("B03", "引擎譯音!"),
	C01("C01", " 煞車磨耗!"),
	C02("C02", "燈號異常!"), 
	C03("C03", "過檢修日期!"), 
	C04("C04" ,"車胎磨耗!"),
	C05("C05", "車體刮傷!"),
	E("E", "處理中!");
	private String code;
	private String message;

	private Abnormal(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
