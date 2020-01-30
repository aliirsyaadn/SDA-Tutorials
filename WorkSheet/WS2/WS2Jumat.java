import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Anda hanya ditugaskan untuk menyelesaikan implementasi processCommand.
 * Anda dapat menggunakan variable n dari global variable yang
 * nilainya telah diisi pada method readInput. Anda bebas menambahkan maupun
 * merubah fungsi, variable, dll dalam template ini
 */
public class WS2Jumat {
    private static int n;

    private static InputReader in;
    private static PrintWriter out;

    /** Jika ingin membuat ADT atau variabel-variabel lain,
     *  tulis dibawah ini
     */
    static Integer kotakUtama = 0;
    static ArrayList<Queue<Integer>> tabung = new ArrayList<Queue<Integer>>();
    static ArrayList<Integer> indexTabung = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        readInput();
        out.close();
    }

    private static void processCommand(String[] query) throws IOException{
        // TODO: selesaikan fungsi ini agar dapat mensimulasikan perintah
        // sistem lotre, jika perintah D dijalankan dan
        // ingin diprint maka menggunakan "printOutput(...)"
        for(int j = 0; j <= 3000; j++){
            tabung.add(new LinkedList<Integer>());
        }

        
        String perintah = query[0];
        int arg1 = Integer.parseInt(query[1]);


        if(perintah.equals("A")){
            indexTabung.add(arg1);
            tabung.get(arg1).add(Integer.parseInt(query[2]));
        } else if(perintah.equals("B")){
            for(int i = 0; i < Integer.parseInt(query[2]); i++){
                if(!tabung.get(arg1).isEmpty()){
                    kotakUtama += tabung.get(arg1).poll();
                }
            }
            
            
        } else if(perintah.equals("C")){
            for(int i = 0; i < indexTabung.size(); i++){
                for(int j = 0; j < arg1; j++){
                    if(!tabung.get(indexTabung.get(i)).isEmpty()){
                        kotakUtama += tabung.get(indexTabung.get(i)).poll();
                    }
                        
                }
            }
        } else if(perintah.equals("D")){
            if(kotakUtama >= arg1){
                printOutput("RESET " + Integer.toString(kotakUtama));
                kotakUtama = 0;
            } else{
                printOutput("GAGAL");
            }
        }
            
        

        
        

    }

    private static void printOutput(String answer) throws IOException {
        out.println(answer);
    }

    private static void readInput() throws IOException {
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            String query = in.nextLine();
            String[] command = query.split(" ");
            processCommand(command);
        }
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

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public String nextLine() throws IOException{
            return reader.readLine();
        }

    }
}