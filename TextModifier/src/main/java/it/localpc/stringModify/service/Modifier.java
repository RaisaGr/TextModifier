package it.localpc.stringModify.service;

import java.util.Scanner;
import java.util.regex.*;

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
		 String newStr = "";
		 String tmpStr = "";
		 StringBuffer stringBuilder = new StringBuffer();
		 int i;
		 
		 if (defaultStr.length() > 0){
			 
			 tmpStr = modifySpecSymbols(defaultStr);	
			 tmpStr = changeSymbol(tmpStr);
			 tmpStr = removeDouble(tmpStr);
			 
			 String[] arrayOfString = tmpStr.split(" "); 	
			 
			 for (i = 0; i < arrayOfString.length; i++) {
				 	tmpStr = arrayOfString[i];
			
				 	if (!tmpStr.equals("")){
						tmpStr = removeSymbolEndWord(tmpStr);
						tmpStr = removeArticles(tmpStr);
						
						String pattern = "[\\W]+";
						Pattern p = Pattern.compile(pattern);
						Matcher m = p.matcher(tmpStr.trim()); 
						if (!tmpStr.equals("")){
							if(m.find()){
								stringBuilder.append(tmpStr);
							}else{
								if (stringBuilder.equals("")){
									stringBuilder.append(tmpStr);
								}else{
									stringBuilder.append(" " + tmpStr);
								}
							}
						}
				 	}
				}
			 
			 newStr = stringBuilder.toString();
		 }
		 return newStr.trim();
	 }
	 
//	 function for add " " before special symbols
	 public static String modifySpecSymbols(String tempString){
		 String tmpStr = "";
		 
		 String pattern = "[\\W]+"; // [^\\s]
		 Pattern p = Pattern.compile(pattern);
		 Matcher m = p.matcher(tempString.trim()); 
		 
		 int beg = 0;
		 while(m.find(beg)) {
			tmpStr = tmpStr + tempString.substring(beg, m.start()) + " " + tempString.substring(m.start(), m.end());
			beg = m.end();
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
		String pattern = patternFrom;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(tmpString); 

		tmpString = m.replaceAll(patternTo);
		return tmpString;
	}
	
//	function for remove double symbols
	public static String removeDouble(String tempString) {
		
		StringBuffer stringBuilder = new StringBuffer();
		int i;
		
		tempString = patternChange(tempString, "ee", "i");
		tempString = patternChange(tempString, "oo", "u");
		
		char[] characters = tempString.toCharArray();
		
		stringBuilder.append(characters[0]);
		
		for (i = 0; i < tempString.length()-1; i++) {
			
			if (characters[i] != characters[i+1]) {
				stringBuilder.append(characters[i+1]);
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
		
		tempString = patternChange(tempString, "^[Tt]h$", "");
		tempString = patternChange(tempString, "^[Aa]n$", "");
		tempString = patternChange(tempString, "^[Aa]$", "");
		
		return tempString.trim();
	}
	
}
