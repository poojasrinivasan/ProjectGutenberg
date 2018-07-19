import java.io.*;
import java.util.*;
public class Main {

	public static void main(String[] args) {
		File file = new File("The-Adventures-of-Sherlock-Holmes/1661.txt");
		Main obj = new Main();
		try{
			int totalWords = obj.getTotalNumberOfWords(file);
			System.out.println(totalWords);
			int uniqueWords = obj.getTotalUniqueWords(file);
			System.out.println(uniqueWords);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	
	public int getTotalNumberOfWords(File file) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int count = 0;
		while((line=br.readLine())!=null){
			String[] tokens = line.split(" ");
			count+=tokens.length;
		}
		br.close();
		return count;
	}
	
	public int getTotalUniqueWords(File file) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		Set<String> set = new HashSet<>();
		while((line=br.readLine())!=null){
			String[] tokens = line.split(" ");
			for(String token : tokens){
				if(!set.contains(token)){
					set.add(token);
				}
			}
		}
		
		br.close();
		return set.size();
	}

}
