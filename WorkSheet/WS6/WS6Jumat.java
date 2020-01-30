import java.util.*;
import java.io.*;

interface Hashable {
    boolean equals(Hashable other);
    int getHash(int mod);
}

class MyMap<K extends Hashable, V> {
    int size;
    K[] keys;
    Integer[] table;

    public MyMap(int size) {
        this.size = size;
        this.table = new Integer[size];
        this.keys = (K[]) new Hashable[size];
    }

    int put(K key, Integer value) {
        int hash = key.getHash(size);
        int index = hash;
        int prob = 1;
        // TODO
        while(this.keys[index] != null && !key.equals(this.keys[index])){
            index = hash + (int) Math.pow(prob,2);
            prob++;
            if(index >= this.size){
                index %= this.size;
            }
        }

        if(this.keys[index] == null){
            this.keys[index] = key;
            this.table[index] = value;
            return index;
        } else if(key.equals(this.keys[index])){
            this.table[index] += value;
            return index;
        }

        return -1;
    }

    int get(K key) {
        int hash = key.getHash(size);
        int index = hash;
        int prob = 1;
        // TODO
        
        while(!key.equals(this.keys[index])){
            if(this.keys[index] == null){
                return -1;
            }

            index = hash + (int) Math.pow(prob,2);
            prob++;

            if(index >= this.size){
                index %= this.size;
            }
        }
        
        if(this.keys[index] == null){
            return -1;
        }

        return this.table[index];
    }

    int dec(K key, Integer value) {
        int hash = key.getHash(size);
        int index = hash;
        int prob = 1;
        // TODO
        if(this.keys[index] == null){
            return -1;
        }

        while(!key.equals(this.keys[index])){
            if(this.keys[index] == null){
                return -1;
            }

            index = hash + (int) Math.pow(prob,2);
            prob++;

            if(index >= this.size){
                index %= this.size;
            }
        }
        
        if(this.keys[index] == null){
            return -1;
        }

        if(key.equals(this.keys[index])){
            this.table[index] -= value;
            return index;
        }

        return -1;
    }
}

class MyString implements Hashable {
    private final int BASE = 31;
    public String s;

    public MyString(String s) {
        this.s = s;
    }

    public int getHash(int mod) {
        int hash = 0;
        // TODO
        for(int i = 0; i < this.s.length(); i++){
            hash += ((this.s.charAt(i) - 96) * Math.pow(BASE, i)) % mod; 
        }

        return hash % mod;
    }

    public boolean equals(Hashable other) {
        if(other == null){
            return false;
        }
        return s.equals(((MyString) other).s);
    }

}

public class WS6Jumat {
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) {
        int N = in.nextInt();
        int Q = in.nextInt();

        MyMap<MyString, Integer> myMap = new MyMap<>(N);

        for (int i = 0; i < Q; i++) {
            String command = in.next();
            String type;
            int add,sub;

            if (command.equals("INC")) {
                type = in.next();
                add = in.nextInt();
                MyString typeString = new MyString(type);
                out.println(myMap.put(typeString, add));

            } else if (command.equals("GET")) {
                type = in.next();
                MyString typeString = new MyString(type);
                Integer result = myMap.get(typeString);
                if(result == -1){
                    out.println("not found");
                } else{
                    out.println(result);
                }

            } else if (command.equals("DEC")) {
                type = in.next();
                sub = in.nextInt();
                MyString typeString = new MyString(type);
                Integer result = myMap.dec(typeString, sub);
                if(result == -1){
                    out.println("not found");
                } else{
                    out.println(result);
                }

            }
        }
        
        out.close();
    }
    
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

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }
}