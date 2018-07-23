import java.io.*;
import java.util.*;
public class ChapterWiseAnalysis {
	List<List<String>> generatedSentences = new ArrayList<>();
	//method to return frequency of word chapter wise
    public int[] getFrequencyOfWord(String input) throws Exception {
    	List<String> chapters = getChapterWiseText();
    	int[] ans = new int[chapters.size()];
    	int index = 0;
    	for(String chapter : chapters){
    		int count = 0;
    	    String[] words = chapter.split("\\s+");
    	    for(String word : words){
    	    	if(word.equals(input)){
    	    		count++;
    	    	}
    	    }
    	    ans[index++] = count;
    	}
    	return ans;
    	
    }
    
    // splits text into chapters
    public List<String> getChapterWiseText() throws Exception{
    	String filePath = "The-Adventures-of-Sherlock-Holmes/1661.txt";
    	String book = readFileAsString(filePath);
    	book = book.trim();
    	 ArrayList<String> chapters = new ArrayList<String>(Arrays.asList(book.split("ADVENTURE [IVX]+.\\s")));
    	 chapters.remove(0);
    	 return chapters;
    }
    
    //convert file to String
    public String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
    
    //return chapter number if a a given quote appears in it
    public int getChapterQuoteAppears(String quote) throws Exception{
    	List<String> chapters = getChapterWiseText();
    	for(int i = 0;i<chapters.size();i++){
    		String temp = chapters.get(i).replaceAll("[\\t\\n\\r]+"," ");
    		if(temp.contains(quote)){
    			System.out.println(temp);
    			return i+1;
    		}
    	}
    	return -1;
    }
    
    //randomly generate a sentence on author's tone
    public String generateSentence() throws Exception{
		HashMap<String,Set<String>> adjList = new HashMap<>();
		generateSentence(adjList,"The",new ArrayList<String>());
		StringBuffer sb = new StringBuffer();
		for(List<String> s : generatedSentences){
			for(String token : s){
				sb.append(token);
				sb.append(" ");
			}
		break;	
		}
		return sb.toString();
	}
    
    public List<String> generateSentence(HashMap<String,Set<String>> adjList,String word,List<String> sentence) throws Exception{
    if(sentence.size() > 20 || generatedSentences.size()>=1) return sentence;
    sentence.add(word);
    if(sentence.size()==20){
    	generatedSentences.add(new ArrayList<>(sentence));
    }
    Set<String> neighbors = nextWord(word);
    adjList.put(word,neighbors);
    
   // choosing random element from Set
    List<String> neighborList = new ArrayList<>(); 
    neighborList.addAll(neighbors);
    Double randomNumber = Math.random();
    Integer size = neighborList.size();
    Integer randomIndex = (int)(randomNumber * (size-1));
	generateSentence(adjList,neighborList.get(randomIndex),sentence);
    sentence.remove(sentence.size()-1);
    return sentence;
    }
    
 
    // method to return the next word for the given word in the book	
	public Set<String> nextWord(String word) throws Exception{
		File file = new File("The-Adventures-of-Sherlock-Holmes/1661.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		boolean flag = false;
		Set<String> neighbors = new HashSet<>();
		while((line = br.readLine())!=null){
			String[] tokens  = line.split(" ");
			for(String token : tokens){
				if(token.equals(word)){
					flag = true;
					continue;
				}
				if(flag){
					neighbors.add(token);
					flag = false;
				}
			}
		}
		br.close();
		return neighbors;
	}
}

