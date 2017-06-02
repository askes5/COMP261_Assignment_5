import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
    private final int WINDOW_SIZE;
    
    /**
     * creates a new instance with a given window size
     * @param WINDOW_SIZE the size of the window
     */
    public LempelZiv(int WINDOW_SIZE) {
        this.WINDOW_SIZE = WINDOW_SIZE;
    }
    
    /**
     * creates a new instance with a default window size of 100.
     */
    public LempelZiv(){
        WINDOW_SIZE = 100;
    }
    
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public String compress(String input) {
        
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            char[] inputCharacters = input.toCharArray();
            String behind;
            outputStream.write(createTuple(0, 0, String.valueOf(inputCharacters[0])).getBytes());
            int cursor = 1;
            while (cursor < inputCharacters.length) {
                behind = (input.substring(Math.max(0, cursor - WINDOW_SIZE), cursor));
                int curMatch = 0;
                int curMatchLength = 0;
                for (int lookAHead = cursor + 1; lookAHead < Math.min(cursor + WINDOW_SIZE, input.length()); lookAHead++) {
                    String search = input.substring(cursor, lookAHead);
                    int match = behind.indexOf(search);
                    int matchLength = search.length();
                    if (match >= 0 && matchLength > curMatchLength) {
                        curMatchLength = matchLength;
                        curMatch = match;
                    }
                    if (match < 0) { // if a match is not found (speeds up the processing)
                        break;
                    }
                }
                if (curMatchLength == 0) {
                    outputStream.write(createTuple(0, curMatchLength, String.valueOf(inputCharacters[cursor + curMatchLength])).getBytes());
                } else
                if (cursor + curMatchLength < inputCharacters.length) {
                    outputStream.write(createTuple(curMatch - cursor + Math.max(0, cursor - WINDOW_SIZE), curMatchLength, String.valueOf(inputCharacters[cursor + curMatchLength])).getBytes());
                }
        
                cursor += curMatchLength+1;
                if (cursor >= inputCharacters.length - 1) {
                    if (cursor < inputCharacters.length) {
                        outputStream.write(createTuple(0, 0, String.valueOf(inputCharacters[cursor])).getBytes());
                    }
                    break;
                }
            }
    
            return new String(outputStream.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
       
    }
    
    /**
     * creates a tuple equal to [a,b,c]
     * @return the tuple
     */
    public static String createTuple(int a, int b, String c){
        return "["+a+"|"+b+"|"+c+"]";
    }

    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public String decompress(String compressed) {
        Scanner in = new Scanner(compressed);
        in.useDelimiter("]\\[|\\[|]|\\|");
        StringBuilder out = new StringBuilder();
        while (in.hasNext()){
            int index = out.length();
            int first = in.nextInt();
            int second = in.nextInt();
            String characters = in.next();
            if (first != 0 && second != 0){
                out.append(out.substring(index+first,index+first+second));
            }
            out.append(characters);
        }
        return out.toString();
    }

    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't want to. It is called on every run and its return
     * value is displayed on-screen. You can use this to print out any relevant
     * information from your compression.
     */
    public String getInformation() {
        return "";
    }

    public static void main(String args[]){
        LempelZiv lempelZiv = new LempelZiv();
        String compressed = lempelZiv.compress("text, more text");
        System.out.println(compressed);
//        Scanner in = new Scanner(compressed);
//        in.useDelimiter("]\\[|\\[|]|,");
//        while (in.hasNext()){
//            System.out.println(in.next());
//        }
        System.out.println(lempelZiv.decompress(compressed));
    }
}
