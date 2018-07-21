import java.io.*;
import java.util.*;
public class ChapterWiseAnalysis {
	List<String> chapters;
    public int[] getFrequencyOfWord(String input) throws Exception {
    	chapters = getChapterWiseText();
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
    
    public List<String> getChapterWiseText() throws Exception{
    	String filePath = "The-Adventures-of-Sherlock-Holmes/1661.txt";
    	String book = readFileAsString(filePath);
    	book = book.trim();
    	 ArrayList<String> chapters = new ArrayList<String>(Arrays.asList(book.split("ADVENTURE [IVX]+.\\s")));
    	 chapters.remove(0);
    	 return chapters;
    }
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
    
    public int getChapterQuoteAppears(String quote){
    	for(int i = 0;i<chapters.size();i++){
    		String temp = chapters.get(i).replaceAll("[\\t\\n\\r]+"," ");
    		if(temp.contains(quote)){
    			System.out.println(temp);
    			return i+1;
    		}
    	}
    	return -1;
    }
}

