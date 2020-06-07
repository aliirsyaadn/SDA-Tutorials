import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

public class WS01 {
    private static final String IK = "IK";
    private static final String SI = "SI";

    private static int X;
    private static int N;

    // TODO: Silakan implementasikan struktur data anda di sini
    private static Stack<Integer> stackSI;
    private static Stack<Integer> stackIK;
    private static Deque<Integer> jaket;

    private static InputReader in;
    private static PrintWriter out;

    public static void main(String args[]) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // TODO: Silakan implementasikan struktur data anda di sini

        readInput();
        out.close();
    }

    /**
     * jaketProcess() adalah method yang akan membangun logika untuk menaruh jaket sesuai perintah
     * yang diminta oleh user
     * 
     * @param X - bilangan yang menunjukan banyaknya jaket
     * @param jaket - suatu struktur data untuk menyimpan nomor-nomor jaket
     * @param N - bilangan yang menunjukkan banyaknya perintah yang akan dimasukkan user
     * @throws IOException - Exception untuk menangani InputStream dan OutputStream
     */
    private static void jaketProcess(int X, Deque<Integer> jaket, int N) throws IOException {
        String arah;

        arah = "SI";
        // TODO: Silakan implementasikan algoritma anda di sini

        for(int i = 0; i < N; i++){
            String command = in.next();
            if(command.equals("Reverse")){
                if(arah.equals("SI")){
                    arah = IK;
                } else {
                    arah = SI;
                }
            }else if (command.equals("Push")){
                int data2 = in.nextInt();
                

            }
        }
    }

    /**
     * Berikut adalah method untuk membaca input
     */
    private static void readInput() throws IOException {
        X = in.nextInt();
        for(int i = 0; i < X; i++){

            // TODO: lengkapi bagian ini
            int data = in.nextInt();
            jaket.addLast(data);
        
        }
        N = in.nextInt();
        jaketProcess(X, jaket, N);
    }

    /**
     * Static class berikut adalah template untuk membaca input yang lebih efisien
     * dan cepat dibandingkan jika menggunakan Scanner class. Oleh karena itu
     * disarankan untuk memanfaatkan template class ini.
     */
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

        public String nextLine() throws IOException {
            return reader.readLine();
        }

    }
}