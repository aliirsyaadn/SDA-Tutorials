import java.io.*;
import java.util.*;

class Node {
    Node left;
    Node right;
    Long value;
    
    Node(Long value) {
        this.value = value;
        left = null;
        right = null;
    }
    
}

class Tree {
    Node root;
    static int level = 0;
    static Long result = Long.parseLong("0");
    
    Tree() {
        root = null;
    }

    Tree(Long value){
        root = new Node(value);
    }

    private Node insert(Node subroot, Long value) {
        // TODO: lengkapi method insert pada binary search tree
        if (subroot == null) {
            subroot = new Node(value);
            result += level * value;
        } else if (value < subroot.value) {
            level++;
            subroot.left = insert(subroot.left, value);
        } else if (value > subroot.value) {
            level++;
            subroot.right = insert(subroot.right, value);
        } 
        return subroot;
    }
    
    // method insert yang dapat digunakan di main
    // dapat dipanggil dengan cara tree.insert(x)
    public void insert(Long value) {
        root = insert(root, value);
    }

    public Node getRoot(){
        return root;
    }

    public void resetLevel(){
        level = 0;
    }
    
    public Long getResult(){
        return result;
    }
     
}

public class WS4Jumat {
    private static InputReader in;
    private static PrintWriter out;
    private static int N;
    
    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new PrintWriter(System.out);

        N = in.nextInt();
        ArrayList<Long> A = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            A.add(in.nextLong());
        }
        
        // TODO: lakukan sesuatu dengan N dan larik A di sini
        Tree tree = makeBinarySearchTree(A);

        System.out.print(tree.getResult());

        out.close();
    }

    public static Tree makeBinarySearchTree(ArrayList<Long> arr){
        Tree newTree = new Tree(arr.get(0));
        
        for(int i = 1; i < arr.size(); i++){
            newTree.resetLevel();
            newTree.insert(arr.get(i));
        }

        return newTree;
    }


    // taken from https://codeforces.com/submissions/Petr
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }

        public Long nextLong() {
            return Long.parseLong(next());
        }
 
    }
}
