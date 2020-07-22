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

	
	
	//konfiguracija za radove
	public static final String PUTANJA_DO_FAJLA = "src\\main\\resources\\papers"; //relativna putanja na kojoj cuva radove u fajl sistemu
	public static final int NUMBERS_OF_FILES = 7; //broj slicnih fajlova za svaki deo dokumenta
	public static final int NUMBERS_OF_WORDS_SPLITER = 950; //na koliko reci ce da podeli dokument
	public static final int NUMBERS_OF_WORDS_TO_SHOW = 5; // koliko ce eci da prikazu korisniku da bi mogao da ctr finduje u dokumentu
	
}
