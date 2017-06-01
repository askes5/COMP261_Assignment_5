/**
 * A new search instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 * @author Matthew Askes
 */
public class BruteForceSearch {
    
    private String pattern;
    private String text;
    
    /**
     * creates a new search instance
     * @param pattern the pattern to search for
     * @param text the taxt to search in
     */
    public BruteForceSearch(String pattern, String text){
        this.pattern = pattern;
        this.text = text;
    }
    
    /**
     * searches the given text for a given pattern
     * @param pattern the pattern to look for
     * @param text the text to search
     * @return the index of the first occurrence of the patter, ot -1 if it doesn't occur
     */
    public static int search(String pattern, String text){
        for (int i = 0; i < text.length()-pattern.length(); i++) {
            boolean isMatch = true;
            for (int j = 0; j < pattern.length(); j++) {
                if(text.charAt(i+j) != pattern.charAt(j)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) return i;
        }
        return -1;
    }
    
}
