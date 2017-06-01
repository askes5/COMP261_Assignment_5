import java.util.*;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public String compress(String input) {

        StringBuilder out = new StringBuilder();
        char[] charArray = input.toCharArray();
        String behind;
        int cursor = 0;
        while (cursor < charArray.length) {
            char c = charArray[cursor];
            behind = (input.substring(0,cursor+1));
            int curMatch = 0;
            int curMatchLength = -1;
            for (int i = cursor ; i < input.length(); i++) {
                String search = input.substring(cursor, i);
                int match = behind.indexOf(search);
                int matchLength = search.length();
                if (match >= 0 && matchLength > curMatchLength) {
                    curMatchLength = matchLength;
                    curMatch = match;
                }
            }
            if (curMatch - cursor == 0) {
                out.append(createTuple(curMatch - cursor, curMatchLength-1, String.valueOf(c)));
            } else if (cursor+curMatchLength < charArray.length){
                out.append(createTuple(curMatch - cursor, curMatchLength, String.valueOf(charArray[cursor+curMatchLength])));
                cursor++;
            }
            
            cursor += curMatchLength;
            if (cursor >= charArray.length-1){
                out.append(createTuple(0, 0, String.valueOf(charArray[cursor])));
                break;
            }
        }

        return out.toString();
       
    }
    
    /**
     * creates a tuple equal to [a,b,c]
     * @return the tuple
     */
    public static String createTuple(int a, int b, String c){
        return "["+a+","+b+","+c+"]";
    }

    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public String decompress(String compressed) {
        Scanner in = new Scanner(compressed);
        in.useDelimiter("]\\[|\\[|\\]|,");
        StringBuilder out = new StringBuilder();
        while (in.hasNext()){
            int index = out.length();
            int first = in.nextInt();
            int second = in.nextInt();
            String characters = in.next();
            if (first == 0 && second == 0){
                out.append(characters);
            } else {
                out.append(out.substring(index+first,index+first+second));
                out.append(characters);
            }
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
        String compressed = lempelZiv.compress("What about a larger chunk");
        System.out.println(compressed);
//        Scanner in = new Scanner(compressed);
//        in.useDelimiter("]\\[|\\[|]|,");
//        while (in.hasNext()){
//            System.out.println(in.next());
//        }
        System.out.println(lempelZiv.decompress(compressed));
    }
}
