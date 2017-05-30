import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
    
    private Node rootNode;
    private Map<Character, String> dictionary = new HashMap<>();
    private Map<String, Character> inverseDictionary = new HashMap<>();
    private String text;
    
    /**
     * This would be a good place to compute and store the tree.
     */
    public HuffmanCoding(String text) {
        rootNode = generateTree(text);
        this.text = text;
        fillDictionary(rootNode,"");
    }

    /**
     * Take an input string, text, and encode it with the stored tree. Should
     * return the encoded text as a binary string, that is, a string containing
     * only 1 and 0.
     */
    public String encode(String text) {
        char[] chars = text.toCharArray();
        StringBuilder encoded = new StringBuilder();
        for (char character : chars) {
            encoded.append(dictionary.get(character));
        }
        return encoded.toString();
    }

    /**
     * Take encoded input as a binary string, decode it using the stored tree,
     * and return the decoded text as a text string.
     */
    public String decode(String encoded) {
        char[] characters = encoded.toCharArray();
        StringBuilder output = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        for (char character : characters) {
            currentCode.append(character);
            if (inverseDictionary.get(currentCode.toString()) != null){
                output.append(inverseDictionary.get(currentCode.toString()));
                currentCode = new StringBuilder();
            }
        }
        return output.toString();
    }

    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't wan to. It is called on every run and its return
     * value is displayed on-screen. You could use this, for example, to print
     * out the encoding tree.
     */
    public String getInformation() {
        return "";
    }
    
    /**
     * populates the dictionary using the tree
     */
    private void fillDictionary(Node node, String currentCode){
        if (node.leftNode == null && node.rightNode == null){
            dictionary.put(node.value, currentCode);
            inverseDictionary.put(currentCode,node.value);
            System.out.println(node.value + ": " + currentCode);
        } else {
            fillDictionary(node.leftNode, currentCode+"0");
            fillDictionary(node.rightNode, currentCode+"1");
        }
    }
    
    /**
     * generates the tree for the huffman coding
     * @param text the text defining the tree
     * @return the root node of the tree
     */
    private Node generateTree(String text){
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>();
        Map<Character, Integer> tallys = new HashMap<>();
        for (char c : text.toCharArray()) {
            tallys.put(c,tallys.getOrDefault(c,0)+1);
        }
        tallys.forEach((character, integer) -> nodePriorityQueue.add(new Node(null, null, integer, character)));
        
        while (nodePriorityQueue.size()  > 1 ){
            Node node1 = nodePriorityQueue.remove();
            Node node2 = nodePriorityQueue.remove();
            int newWeight = node1.weight + node2.weight;
            nodePriorityQueue.add(new Node(node1, node2, newWeight, '\0'));
        }
        return nodePriorityQueue.remove();
    }
    
    private class Node implements Comparable<Node>{
        Node leftNode;
        Node rightNode;
        int weight;
        char value;
    
        public Node(Node leftNode, Node rightNode, int weight, char value) {
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.weight = weight;
            this.value = value;
        }
    
        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
}
