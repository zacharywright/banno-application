import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

public class BannoApp {
	
	public static void main(String[] args) throws Exception {
		URL url;
		Scanner scanner;
		
		String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		HashMap<Character, Integer> charCountDict = new HashMap<Character, Integer>();
		
		int firstHighInt = 0;
		int secondHighInt = 0;
		int thirdHighInt = 0;
		
		Character firstHigh = 'a';
		Character secondHigh = 'a';
		Character thirdHigh = 'a';
		
		url = new URL("https://banno.com/");
		scanner = new Scanner(url.openStream());
		
		int wordCount = 0;
		int pngCount = 0;
		int productCount = 0;
		String handle = "No Twitter Handle Found";
		

		System.out.println("\nAnalyzing " + url + "...\n");
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			
			// ------------------- Return Count of Financial Institutions ---------------------|
			if (line.contains("financial institution")) {
				if (line.contains("financial institution") || line.contains("Financial Institution") || line.contains("Financial institution")) {
					
					wordCount += 1;	
				}		
			}
			// ------------------- Return .pngs ---------------------|
			if (line.contains(".png")) {
				pngCount ++;
			}
			
			// ------------------- Return Twitter Handle ---------------------|
			if (line.contains("href=\"https://twitter.com/")) {
				int from = line.indexOf(".");
				int to = line.lastIndexOf("\"");
				handle = "@" + line.substring(from + 5, to);	
			}
			
			// -------------------- Return Number of Products ---------------------|
			if (line.contains("<h3 class=\"color-")) {
				productCount++;
			}
			
			// ------------------- Return Alphanumeric Ranking ---------------------|
			
			//Creates Dictionary //
			for (int i = 0; i < line.length(); i++) {
				if (charCountDict.containsKey(line.charAt(i))) {
					
					int foo = charCountDict.get(line.charAt(i));
					foo += 1;
					charCountDict.replace(line.charAt(i), foo);
				}
				else {
					charCountDict.put(line.charAt(i), 1);
				}
			}
				
		}
		scanner.close();
		// Calculates the top 3 occurring alphanumeric chars //
		int value = 0;
		for (Character key : charCountDict.keySet()) {
		    
		    value = charCountDict.get(key);
		    
		    if (validChars.contains(Character.toString(key))) {
		    	if (value > firstHighInt) {
			    	thirdHighInt = secondHighInt;
			    	secondHighInt = firstHighInt;
					firstHighInt = value;
					
					thirdHigh = secondHigh;
					secondHigh = firstHigh;
					firstHigh = key;
			    }
			    else if (value > secondHighInt) {
			    	thirdHighInt = secondHighInt;
			    	secondHighInt = value;
			    	
			    	thirdHigh = secondHigh;
			    	secondHigh = key;
			    }
			    else if (value > thirdHighInt) {
			    	thirdHighInt = value;
			    	thirdHigh = key;
			    }
		    }
		       	
		}
		// Print Statements //
		System.out.println("There are " + productCount + " products found.");
		System.out.println("Most Occurring Alphanumeric Character: " + firstHigh + " occurred " + firstHighInt + " times." );
		System.out.println("Second Most Occurring Alphanumeric Character: " + secondHigh + " occurred " + secondHighInt + " times." );
		System.out.println("Thrid Most Occurring Alphanumeric Character: " + thirdHigh + " occurred " + thirdHighInt + " times.");
		System.out.println("There are " + pngCount + " .png's embedded in the HTML.");
		System.out.println("The Twitter handle found is: " + handle);	
		System.out.println("The phrase \"Financial Institution\" occurs " + wordCount + " times in text on the page.");	
	}

}
