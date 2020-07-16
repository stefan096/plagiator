package com.ftn.plagiator.validation;

import java.util.regex.Pattern;

public class StaticData {
	public static final int lengthValue = 30;
	public static final int lengthDescription = 140;
	public static final int minLength = 0;
	public static final int minLengthEmail = 10;
	public static final int maxLengthEmail = 60;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static final Pattern VALID_PASSWORD_NUMBERS = Pattern.compile("[0-9]+");
	public static final Pattern VALID_PASSWORD_SPECIAL_CHARACTERS = Pattern.compile("[+-/*/.,!?+_#%^]+");
	public static final Pattern VALID_PASSWORD_UPPER_CASE = Pattern.compile("[A-Z]+");
	public static final Pattern VALID_PASSWORD_LOWER_CASE = Pattern.compile("[a-z]+");
	
	public static final String PUTANJA_DO_FAJLA = 
			"D:\\plagiator\\src\\main\\resources\\papers";
	public static final int NUMBERS_OF_FILES = 3;
	public static final int NUMBERS_OF_WORDS_SPLITER = 950;
	
	public static final int INITIAL_DELAY = 1000; //1second = 1000milisecunds
	public static final int FIXED_DELAY = 1000*60; //10h == 1000*10*60*60
}
