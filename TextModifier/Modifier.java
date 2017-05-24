package it.localpc.stringModify.service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Modifier {

	 public static void main( String[] args ) {
		 	boolean isFirst = true;
	        Scanner in = new Scanner(System.in);
	        System.out.println("Enter your text: ");
	        
	        while(in.hasNext()) {

	            String inputStr = in.nextLine();
	            
	            inputStr = textModify(inputStr);
	            
	            if (isFirst){
	            	System.out.println("Text after modification: ");
	            	isFirst = false;
	            }
	            
	            System.out.println(inputStr);
	        }

	    }

//	 general function with all modification
	 public static String textModify(String defaultStr){
		 String tmpStr = "";
		 StringBuilder stringBuilder = new StringBuilder();
		 Boolean wasSpec = false;
		 
		 if (defaultStr.length() > 0){
			 
			 tmpStr = modifySpecSymbols(defaultStr);	
			 tmpStr = changeSymbol(tmpStr);
			 
			 String[] arrayOfString = tmpStr.split(" "); 	
			 
			 for (int i = 0; i < arrayOfString.length; i++) {
				 	tmpStr = arrayOfString[i];
			
				 	if (!tmpStr.equals("")){
				 		tmpStr = removeArticles(tmpStr);
				 		if (!tmpStr.equals("")){
				 			tmpStr = removeDouble(tmpStr);
				 			tmpStr = removeSymbolEndWord(tmpStr);
						
				 			String pattern = "[\\W]+";
				 			Pattern p = Pattern.compile(pattern);
				 			Matcher m = p.matcher(tmpStr);//.trim()); 
				 			if (!tmpStr.equals("")){
				 				if(m.find()){
				 					stringBuilder.append(tmpStr);
				 					wasSpec = true;
				 				}else{
				 					if (stringBuilder.toString().equals("") | wasSpec){
				 						stringBuilder.append(tmpStr);
				 					}else {
				 						stringBuilder.append(" " + tmpStr);
				 					}
				 					wasSpec = false;
				 				}
				 			}
				 		}	
				 	}
				}
		 }
		 return stringBuilder.toString();//.trim();
	 }
	 
//	 function for add " " before special symbols
	 public static String modifySpecSymbols(String tempString){
		 String tmpStr = "", textTemp = "";
		 
		 String pattern = "[\\W]+"; // [^\\s]
		 Pattern p = Pattern.compile(pattern);
		 Matcher m = p.matcher(tempString);//.trim()); 
		 
		 int beg = 0;
		 if (m.find()){
			 beg = m.start();
			 while(m.find(beg)) {
				 textTemp = tempString.substring(m.start(), m.end());
				 if(tempString.substring(m.start(), m.end()).equals("[\\S]")){
					 tmpStr = tmpStr + tempString.substring(beg, m.end());
				 }else{
					 tmpStr = tmpStr + tempString.substring(beg, m.start()) + " " + tempString.substring(m.start(), m.end()) + " ";
				 }
				 beg = m.end();
			 }
		 }
		
		 if (beg != tempString.length()){
			 tmpStr = tmpStr + tempString.substring(beg, tempString.length()); 
		 }

		 return tmpStr;
	 }
	 
//	 function for changes symbols
	public static String changeSymbol(String tempString) {
		
		tempString = patternChange(tempString, "[Cc][Ii]", "si");
		tempString = patternChange(tempString, "[Cc][Ee]", "se");
		tempString = patternChange(tempString, "[Cc][Kk]", "k");
		tempString = patternChange(tempString, "[Cc]", "k");
		
		return tempString;
	}

//	function for change using regex
	public static String patternChange(String tmpString, String patternFrom, String patternTo){
//		String pattern = patternFrom;
		Pattern p = Pattern.compile(patternFrom);
		Matcher m = p.matcher(tmpString); 

		tmpString = m.replaceAll(patternTo);
		System.out.println(tmpString);
		return tmpString;
	}
	
//	function for remove double symbols
	public static String removeDouble(String tempString) {
		
		StringBuilder stringBuilder = new StringBuilder();
		String pattern = "[\\W]+";
		Pattern p = Pattern.compile(pattern);
		
		tempString = patternChange(tempString, "ee", "i");
		tempString = patternChange(tempString, "oo", "u");
		
		char[] characters = tempString.toCharArray();
		
		stringBuilder.append(characters[0]);
		
		for (int i = 0; i < tempString.length()-1; i++) {
			if (characters[i] != characters[i+1]) {
				stringBuilder.append(characters[i+1]);
			}else{
//				check if this symbol is not a letter, save double symbol
				Matcher m = p.matcher(Character.toString(characters[i+1])); 
				if(m.find()){
					stringBuilder.append(characters[i+1]);
				}
			}
		}
		
		return stringBuilder.toString();
		
	}
	
//	function for remove symbol at the end of one word
	public static String removeSymbolEndWord(String tempString) {
		String tmpStr, symbol;
		
		if (tempString.length()>1){
			symbol = tempString.substring(tempString.length() - 1);
			
			if(symbol.equals("e")){
				tmpStr = tempString.substring(0, tempString.length() - 1);
				return tmpStr;
			}
		}
		return tempString;
	}

//	function for remove articles
	public static String removeArticles(String tempString) {
		
		tempString = patternChange(tempString, "^[Tt]he$", "");
		tempString = patternChange(tempString, "^[Aa]n$", "");
		tempString = patternChange(tempString, "^[Aa]$", "");
		
		return tempString.trim();
	}
	
}
