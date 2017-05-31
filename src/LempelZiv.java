/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public String compress(String input) {
//        StringBuilder s = new StringBuilder();
        StringBuilder out = new StringBuilder();
//        char ch;
//        List<char[]> dictionary = new ArrayList<>();
//        char[] in = input.toCharArray();
//        for (int i = 0; i < in.length; i++) {
//            dictionary.add(new char[]{in[i]});
//        }
//        for (int i = 0; i < in.length ; i++){
//            ch = in[i];
//            if (dictionary.contains((s.toString() + ch).toCharArray())) {
//                s.append(ch);
//            } else {
//                out.append("[").append(s.toString()).append("]");
//                dictionary.add((s.toString()+ch).toCharArray());
//                s = new StringBuilder().append(ch);
//            }
//        }
//        out.append(s.toString());
//        return out.toString();

//        cursor <- 0
        int cursor = 0;
//        windowSize <- 100 // some suitable size
        final int WINDOW_SIZE = 1;//input.length()/2;
//        while cursor < text.size
        while (cursor < input.length()) {
//            lookahead <- 0
            int lookAHead = 0;
//            prevMatch <- 0
            int prevMatch = 0;
//            loop
            while (true) {
//                match <- stringMatch( text[cursor.. cursor+lookahead],
//                text[(cursor<windowSize)?0:cursor-windowSize .. cursor-1] )
                int match = input.substring(cursor,cursor+lookAHead).indexOf(input.substring((cursor<WINDOW_SIZE)?0:cursor-WINDOW_SIZE, cursor-1));
//                if match succeeded then
                if (match >= 0) {
//                    prevMatch <- match
                    prevMatch = match;
//                    lookahead <- lookahead + 1
                    lookAHead++;
//                else
                }else {
//                    output( [suitable value for prevMatch, lookahead, text[cursor+lookahead ]])
                    out.append("[").append(prevMatch).append(",").append(lookAHead).append(",").append(input.charAt(cursor+lookAHead)).append("]");
//                    cursor <- cursor + lookahead + 1
                    cursor = cursor + lookAHead + 1;
//                break
                    break;
                }
            }
        }

        return out.toString();
    }

    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public String decompress(String compressed) {
        return compressed;
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
        System.out.println(lempelZiv.compress("aaaaaaaaaaaa"));
    }
}
