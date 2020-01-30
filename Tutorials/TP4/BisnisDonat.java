import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.LinkedList;
import java.lang.Comparable;

class Graph{
    private int JUMLAH_VERTEX;
    private HashMap<Integer, Edge>[] adjList;

    Graph(int N){
        JUMLAH_VERTEX = N; 
        adjList = new HashMap[JUMLAH_VERTEX+1]; 
        for (int i = 0; i < JUMLAH_VERTEX+1; i++) 
            adjList[i] = new HashMap<>();
    }

    void printGraph(){
        for(int i = 1; i < JUMLAH_VERTEX+1; i++){
            System.out.println("kota : " + i + " punya adj dan beberapa jalan dengan jarak :");
            for(int adj : adjList[i].keySet()){
                System.out.println(">>> adj : " + adj + " jarak :"+ adjList[i].get(adj).getJarakMin());
            }
        }
    }

    void addEdge(int kota1, int kota2, int jarak){
        if(adjList[kota1].keySet().contains(kota2)){
            adjList[kota1].get(kota2).add(jarak);
            adjList[kota2].get(kota1).add(jarak);
            return;
        }

        Edge edgeKota1 = new Edge(kota2, jarak);
        Edge edgeKota2 = new Edge(kota1, jarak);
        
        adjList[kota1].put(kota2, edgeKota1);
        adjList[kota2].put(kota1, edgeKota2);
        return;
    }


    int OS(int[] kota){
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();
        for(int k : kota){
            queue.add(k);
        }

        while(!queue.isEmpty()){
            int current = queue.poll();

            if(!seen.contains(current)){
                seen.add(current);
            }

            for(int adj : this.adjList[current].keySet()){
                if(!seen.contains(adj)){
                    queue.add(adj);
                }
            }
        }

        return seen.size();
    }

    int CCWG(int kota, int jarakMin){
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();
        
        queue.add(kota);

        while(!queue.isEmpty()){
            int current = queue.poll();

            if(!seen.contains(current)){
                seen.add(current);
            }

            for(int adj : this.adjList[current].keySet()){
                if(!seen.contains(adj) && this.adjList[current].get(adj).getJarakMin() <= jarakMin){
                    queue.add(adj);
                }
            }
        }

        return seen.size() - 1;
    }

    private ArrayList<int[]> bfs(int kota){
        Queue<Integer> queue = new LinkedList<>();
        int[] distance = new int[this.JUMLAH_VERTEX+1];
        int[] paths = new int[this.JUMLAH_VERTEX+1];

        for(int i = 1; i <= this.JUMLAH_VERTEX; i++){
            distance[i] = -1;
        }

        paths[kota] = 1;
        distance[kota] = 0;
        queue.add(kota);

        while(!queue.isEmpty()){
            int current = queue.poll();

            for(int adj : this.adjList[current].keySet()){
                if(distance[adj] == -1){
                    distance[adj] = distance[current] + 1 ;
                    queue.add(adj);
                } 
                
                if(distance[current] + 1 < distance[adj]){
                    distance[adj] = distance[current] + 1;
                    paths[adj] = paths[current] * this.adjList[current].get(adj).size();
                } else if(distance[current] + 1 == distance[adj]){
                    paths[adj] += paths[current] * this.adjList[current].get(adj).size();
                }

                paths[adj] = paths[adj] % 10001;
            }
        }
        
        ArrayList<int[]> result = new ArrayList<>();
        result.add(distance);
        result.add(paths);

        return result;
    }

    int LAOR(int kota1, int kota2){
        int[] distance = this.bfs(kota1).get(0);

        return distance[kota2];
    } 

    int LAORC(int kota1, int kota2){
        int[] paths = this.bfs(kota1).get(1);

        return paths[kota2];
    }
    
    private int[] djikstra(int kota1){
        Queue<Edge> queue = new PriorityQueue<>();
        int[] distance = new int[this.JUMLAH_VERTEX+1];
        
        for(int i = 1; i <= this.JUMLAH_VERTEX; i++){
            distance[i] = Integer.MAX_VALUE;
        }

        distance[kota1] = 0;

        queue.add(new Edge(kota1, 0));

        while(!queue.isEmpty()){
            Edge current = queue.poll();
            int curr = current.getAdj();
            for(int adj : this.adjList[curr].keySet()){
                if(distance[curr] + this.adjList[curr].get(adj).getJarakMin() < distance[adj]){
                    distance[adj] = distance[curr] + this.adjList[curr].get(adj).getJarakMin();
                    queue.add(this.adjList[curr].get(adj));
                }
            }
        }
        
        return distance;
    }

    int SR(int kota1, int kota2){
        int[] distance = this.djikstra(kota1);

        if(distance[kota2] == Integer.MAX_VALUE){
            return -1;
        }
        
        return distance[kota2];
    }

    int SM(int kota1, int kota2){
        int[] distance1 = this.djikstra(kota1);
        int[] distance2 = this.djikstra(kota2);

        int result =  Integer.MAX_VALUE;

        for(int i = 1; i <= this.JUMLAH_VERTEX; i++){
            int temp = Math.max(distance1[i], distance2[i]);
            if(temp < result){
                result = temp;
            }
        }

        if(result == Integer.MAX_VALUE){
            return -1;
        }

        return result;
    }

}

class Edge implements Comparable<Edge>{
    int adj;
    int distanceMin;
    int jumlahJalan;

    Edge(int adj, int jrk){
        this.adj = adj;
        distanceMin = jrk;
        jumlahJalan = 1;
    }

    void add(int jrk){
        jumlahJalan++;
        distanceMin = jrk < distanceMin ? jrk : distanceMin;
    }

    int size(){
        return jumlahJalan;
    }

    int getJarakMin(){
        return distanceMin;
    }

    int getAdj() { 
        return this.adj;
    }

    @Override
    public int compareTo(Edge other) {
        if(this.getJarakMin() < other.getJarakMin()){
            return -1;
        }
        return 1;
    }

}


public class BisnisDonat{
    public static void main(String[] args) throws IOException{
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = in.nextInt();
        int M = in.nextInt();
        int Q = in.nextInt();

        Graph graph = new Graph(N);

        for(int i = 0; i < M; i++){
            int U = in.nextInt();
            int V = in.nextInt();
            int T = in.nextInt();
            graph.addEdge(U, V, T);
        }

        for(int i = 0; i < Q; i++){
            String[] perintah = in.nextLine().split(" ");
            int[] kota = new int[perintah.length - 1];
            int X,Z;
            switch(perintah[0]){
                case "OS":
                    for(int j = 1; j < perintah.length; j++){
                        kota[j-1] = Integer.parseInt(perintah[j]); 
                    }
                    out.println(graph.OS(kota));
                    break;
                case "CCWGD":
                    X = Integer.parseInt(perintah[1]);
                    Z = Integer.parseInt(perintah[2]);
                    out.println(graph.CCWG(X, Z));
                    break;
                case "LAOR":
                    X = Integer.parseInt(perintah[1]);
                    Z = Integer.parseInt(perintah[2]);
                    out.println(graph.LAOR(X, Z));
                    break;
                case "LAORC":
                    X = Integer.parseInt(perintah[1]);
                    Z = Integer.parseInt(perintah[2]);
                    out.println(graph.LAORC(X, Z));
                    break;
                case "SR":
                    X = Integer.parseInt(perintah[1]);
                    Z = Integer.parseInt(perintah[2]);
                    out.println(graph.SR(X, Z));
                    break;
                case "SM":
                    X = Integer.parseInt(perintah[1]);
                    Z = Integer.parseInt(perintah[2]);
                    out.println(graph.SM(X, Z));
                    break;
            }
        }
        out.close();
    }

    static class InputReader {
        // taken from https://codeforces.com/submissions/Petr
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

        public String nextLine() throws IOException{
            return reader.readLine();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}