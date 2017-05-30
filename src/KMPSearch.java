/**
 * A new KMPSearch instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMPSearch {

    private String text;
    private String pattern;
    private int[] KMPTable;

    /**
     * creates a new KMPSearch instance, including the search table
     * @param pattern the pattern to search for
     * @param text the text to search in
     */
    public KMPSearch(String pattern, String text) {
        this.pattern = pattern;
        this.text = text;
        KMPTable = generateKMPTable(pattern);
    }

    /**
     * generate the kmp table for a given word, will update KMPTable
     * @param word the word to generate the kmp table for
     */
    private int[] generateKMPTable(String word){
        int[] KMPTable = new int[word.length()+1];

        KMPTable[0] = -1;
        KMPTable[1] = 0;
        int tablePos = 1;
        int charPos = 0;

        while (tablePos < word.length()){
            if (word.charAt(tablePos) == word.charAt(charPos)){
                KMPTable[tablePos] = KMPTable[charPos];
                tablePos++;
                charPos++;
            } else {
                KMPTable[tablePos] = charPos;
                charPos = KMPTable[charPos];
                while (charPos >= 0 && (word.charAt(tablePos) != word.charAt(charPos))) {
                    charPos = KMPTable[charPos];
                }
                tablePos++;
                charPos++;
            }
        }
        KMPTable[tablePos] = charPos;

        return KMPTable;
    }

    /**
     * Perform KMPSearch substring search on the given text with the given pattern.
     * 
     * @return the starting index of the first substring match if it
     * exists, or -1 if it doesn't.
     */
    public int search(String pattern, String text) {
//        the beginning of the current match in the text
        int m = 0;
//        the position of the current character in the pattern
        int i = 0;
        while (m+i < text.length()-1) {
            if (pattern.charAt(i) == text.charAt(m+i)) {
                i++;
                if (i == pattern.length()) {
                    return m;
//                    m = m + i - KMPTable[i]; not needed unless looking for the next occurrence
//                    i = KMPTable[i];
                }
            } else {
                if (KMPTable[i] > -1) {
                    m = m + i - KMPTable[i];
                    i = KMPTable[i];
                } else {
                    m = m + i + 1;
                    i = 0;
                }
            }
        }
//        (if we reach here, we have searched the entire text unsuccessfully)
        return -1;
    }
}
