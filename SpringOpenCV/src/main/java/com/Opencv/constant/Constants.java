package com.Opencv.constant;

public class Constants {

	public static final String BASE_URL="/user";
	
	
	public static final String CHARGE_POST_URL="/charges_master";
	public static final String CHARGE_GET_URL="/charges_master";
	public static final String CHARGE_PUT_URL="/charges_master";
	public static final String CHARGE_DELETE_URL="/charges_master/{id}";
	public static final String CHARGE_GET_URL_ID="/charges_master/{id}";
	
	public static final String DISTRICT_POST_URL="/district_master";
	public static final String DISTRICT_GET_URL="/district_master";
	public static final String DISTRICT_PUT_URL="/district_master";
	public static final String DISTRICT_DELETE_URL="/district_master/{id}";
	public static final String DISTRICT_GET_URL_ID="/district_master/{id}";
	
	public static final String DIVISION_POST_URL = "/division_master";
	public static final String DIVISION_GET_URL = "/division_master";
	public static final String DIVISION_PUT_URL = "/division_master";
	public static final String DIVISION_DELETE_URL = "/division_master/{id}";
	public static final String DIVISION_GET_URL_ID = "/division_master/{id}";
	
//	public static final String MUNICIPALITY_POST_URLL="/district_master";
//	public static final String DISTRICT_GET_URL="/district_master";
//	public static final String DISTRICT_PUT_URL="/district_master";
//	public static final String DISTRICT_DELETE_URL="/district_master/{id}";
//	public static final String DISTRICT_GET_URL_ID="/district_master/{id}";
	
	public static final String DOCUMENT_VERIFICATION_POST_URL="/user/verify-document";
	
	 // Success & UnSuccess
    public static final int SUCCESSFUL = 1;
    public static final int UNSUCCESSFUL = 0;
    
    //regex
    public static final String IGNORE_CASE_AND_NEW_LINE = "si";
	
}
