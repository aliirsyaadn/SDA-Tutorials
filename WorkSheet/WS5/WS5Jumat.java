import java.util.*;
import java.io.*;

class MinHeap {
    List<Integer> heap = new ArrayList<Integer>();
    
    public void add(int val) {
        // TODO: lengkapi dengan cara menambah elemen ke minimum binary heap.
        heap.add (val);
        percolateUp (heap.size() - 1);
    }

    protected int parentOf(int i) {
        return (i - 1) / 2;
    }

    protected List<Integer> getHeap(){
        return this.heap;
    }

    protected void percolateUp (int leaf){
        int parent = parentOf(leaf);
        int value = heap.get(leaf);
        while (leaf > 0 && (value < heap.get(parent))){
            heap.set(leaf, heap.get(parent));
            leaf = parent;
            parent = parentOf(leaf);
        }
        heap.set(leaf, value);
    }
    
    // mengambil dan menghapus elemen terkecil dari heap.
    public int poll() {
        // hapus elemen teratas, tukar dengan elemen terakhir
        int result = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        
        // percolate down
        int now = 0;
        while (2*now + 1 < heap.size()) {
            int minIdx = 2*now + 1;
            if (2*now + 2 < heap.size() && heap.get(2*now + 2) < heap.get(2*now + 1)) {
                minIdx = 2*now + 2;
            }
            if (heap.get(now) > heap.get(minIdx)) {
                // swap elemen
                int temp = heap.get(minIdx);
                heap.set(minIdx, heap.get(now));
                heap.set(now, temp);
                
                // lanjut ke bawah
                now = minIdx;
            } else {
                break;
            }
        }
        
        return result;
    }
    
    // TODO: lengkapi dengan method lain jika dibutuhkan.
}

public class WS5Jumat {
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) {
        int N = in.nextInt();
        long H = in.nextLong();
        List<Integer> D = new ArrayList<>();
        List<Integer> L = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            D.add(in.nextInt());
        }
        for (int i = 0; i < N; i++) {
            L.add(in.nextInt());
        }
        
        // TODO: lengkapi dengan cara mencari level terbesar yang dapat dicapai
        int level = 1;
        List<Integer> sisaD = new ArrayList<>();
        List<Integer> sisaL = new ArrayList<>();

        for(int i = 0; i < N; i++){
            if(level >= L.get(i) &&  H > D.get(i)){
                H -= D.get(i);
                level++;
            } else{
                sisaD.add(D.get(i));
                sisaL.add(L.get(i));
            }
        }

        for(int i = sisaL.size()-1; i >= 0 ; i--){
            if(level >= sisaL.get(i) && H > sisaD.get(i)){
                H -= sisaD.get(i);
                level++;
            }
        }

        

        out.println(level);
        
  
        // TODO: lengkapi dengan memasukkan elemen-elemen D dan cetak representasi array heap-nya.
        MinHeap minHeap = new MinHeap();

        for (int damage : D){
            minHeap.add(damage);
        }

        for (int i : minHeap.getHeap()) {
            out.print(i + " ");
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