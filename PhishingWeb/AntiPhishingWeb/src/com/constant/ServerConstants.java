package com.constant;

import java.util.Map;

import com.helper.SvmClassifier;

public class ServerConstants {
	public static final String db_driver = "com.mysql.jdbc.Driver";
	public static final String db_user = "root";
	public static final String db_pwd = "";
	public static final String db_url = "jdbc:mysql://localhost/antiphishing";

	public static final SvmClassifier svm = new SvmClassifier();
	static Map<String, String> env = System.getenv();
	public static final String GET_ENERVMENT = env.get("PROJECT_PATH").toString();

	public static final String PROJECT_DATASET_DIR = GET_ENERVMENT+"\\AntiPhishingWeb\\PhishingWeb\\AntiPhishingWeb\\";
	
	
	public static final String PARSING_FILE="";
	public static final String NEURAL_DIR = GET_ENERVMENT+"\\AntiPhishingWeb\\PhishingPython";
	public static final String NEURAL_MODEL_FILE = NEURAL_DIR + "neural.model";
	public static final String NEURAL_TRAINING_SET = NEURAL_DIR + "dataset.arff";
	public static final String APPLICATION_NAME = "AntiPhishingWeb";

}
