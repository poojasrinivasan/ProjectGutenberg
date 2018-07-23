import java.io.*;
import java.util.*;
public class Main {

	public static void main(String[] args) {
		File file = new File("The-Adventures-of-Sherlock-Holmes/1661.txt");
		Main obj = new Main();
		try{
			int totalWords = obj.getTotalNumberOfWords(file);
			System.out.println("Total words in book " + totalWords);
			System.out.println("=======");
			int uniqueWords = obj.getTotalUniqueWords(file);
			System.out.println("Total unique words in book " + uniqueWords);
			System.out.println("=======");
			Object[][] top20FrequentWords = obj.get20MostFrequentWords(file);
			System.out.println("Top 20 Frequent words");
			for(Object[] row : top20FrequentWords){
				System.out.println(row[0] + " "+row[1]);
			}
			System.out.println("=======");
			Object[][] top20InterestingFrequentWords = obj.get20MostInterestingFrequentWords(file);
			System.out.println("Top 20 Interesting Frequent words");
			for(Object[] row : top20InterestingFrequentWords){
				System.out.println(row[0] + " "+row[1]);
			}
			System.out.println("=======");
			Object[][] topLeastFrequentWords = obj.get20LeastFrequentWords(file);
			System.out.println("Top 20 Least Frequent words");
			for(Object[] row : topLeastFrequentWords){
				System.out.println(row[0] + " "+row[1]);
			}
			ChapterWiseAnalysis chObj = new ChapterWiseAnalysis();
			int[] chapters = chObj.getFrequencyOfWord("young");
			System.out.println(Arrays.toString(chapters));
			String quote = "Women are naturally secretive, and they like to do their own secreting.";
			int chapterNumber = chObj.getChapterQuoteAppears(quote);
			if(chapterNumber!=-1){
				System.out.println("Chapter "+chapterNumber+" contains "+quote);
			}
			else{
				System.out.println("Quote not found");
			}
			
			
			String generateSentence = chObj.generateSentence();
			System.out.println(generateSentence);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	// returns total number of words in the text file
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
	
	//returns total unique words in a file
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
	
	
	//returns top 20 frequent words with frequencies
	public Object[][] get20MostFrequentWords(File file) throws Exception{
		Object[][] ans = new Object[20][2];
		HashMap<String,Integer> map = new HashMap<>();
		PriorityQueue<String> queue = new PriorityQueue<>(new Comparator<String>(){
			public int compare(String s1,String s2){
				return map.get(s1) - map.get(s2);
			}
		});
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line=br.readLine())!=null){
			String[] tokens = line.split("\\s+");
			for(String token : tokens){
				if(token.length()==0)continue;
					int val = map.getOrDefault(token, 0);
					map.put(token,val+1);
				}
		}
		br.close();
        Set<String> keys = map.keySet();
        int count = 0;
        for(String key : keys){
        	if(count < 20){
        		queue.offer(key);
        		count++;
        	}
        	else{
        		String topMin = queue.peek();
        		if(map.get(key) > map.get(topMin)){
        			queue.poll();
        			queue.offer(key);
        		}
        	}
        }
        int index = ans.length-1;
        while(!queue.isEmpty() && index>=0){
        	String key = queue.poll();
        	int value = map.get(key);
        	ans[index][0] = key;
        	ans[index][1] = value;
        	index--;
        	
        }
		
		return ans;
	}
	
	//return top 20 most interesting frequent words
	public Object[][] get20MostInterestingFrequentWords(File file) throws Exception{
		Object[][] ans = new Object[20][2];
		HashMap<String,Integer> map = new HashMap<>();
		PriorityQueue<String> queue = new PriorityQueue<>(new Comparator<String>(){
			public int compare(String s1,String s2){
				return map.get(s1) - map.get(s2);
			}
		});
		Set<String> stopwords = getStopWords();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line=br.readLine())!=null){
			String[] tokens = line.split("\\s+");
			for(String token : tokens){
				    if(stopwords.contains(token) || token.length()==0) continue;
					int val = map.getOrDefault(token, 0);
					map.put(token,val+1);
				}
		}
		br.close();
        Set<String> keys = map.keySet();
        int count = 0;
        for(String key : keys){
        	if(count < 20){
        		queue.offer(key);
        		count++;
        	}
        	else{
        		String topMin = queue.peek();
        		if(map.get(key) > map.get(topMin)){
        			queue.poll();
        			queue.offer(key);
        		}
        	}
        }
        int index = ans.length-1;
        while(!queue.isEmpty() && index>=0){
        	String key = queue.poll();
        	int value = map.get(key);
        	ans[index][0] = key;
        	ans[index][1] = value;
        	index--;
        	
        }
		
		return ans;
	}
	
	
	//returns least 20 frequent words
	public Object[][] get20LeastFrequentWords(File file) throws Exception{
		Object[][] ans = new Object[20][2];
		HashMap<String,Integer> map = new HashMap<>();
		PriorityQueue<String> queue = new PriorityQueue<>(new Comparator<String>(){
			public int compare(String s1,String s2){
				return -(map.get(s1) - map.get(s2));
			}
		});
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line=br.readLine())!=null){
			String[] tokens = line.split("\\s+");
			for(String token : tokens){
				if(token.length()==0)continue;
					int val = map.getOrDefault(token, 0);
					map.put(token,val+1);
				}
		}
		br.close();
        Set<String> keys = map.keySet();
        int count = 0;
        for(String key : keys){
        	if(count < 20){
        		queue.offer(key);
        		count++;
        	}
        	else{
        		String topMin = queue.peek();
        		if(map.get(key) < map.get(topMin)){
        			queue.poll();
        			queue.offer(key);
        		}
        	}
        }
        int index = ans.length-1;
        while(!queue.isEmpty() && index>=0){
        	String key = queue.poll();
        	int value = map.get(key);
        	ans[index][0] = key;
        	ans[index][1] = value;
        	index--;
        	
        }
		
		return ans;
	}
	//method to populate most commonly used words in a set
	public Set<String> getStopWords() throws Exception{
	File file = new File("stopWords/stopwords.txt");
	HashSet<String> stopwords = new HashSet<>();
	BufferedReader br = new BufferedReader(new FileReader(file));
	String line;
	while((line=br.readLine())!=null){
		String[] tokens = line.split(" ");
		for(String token : tokens){
			if(!stopwords.contains(token)){
				stopwords.add(token);
			}
		}
	}
	br.close();
	return stopwords;
	}
	
	
	
	
	
	
	
	

}
