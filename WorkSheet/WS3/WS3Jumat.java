import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;


/*
    Anda ditugaskan untuk meimplementasikan beberapa method
    Terdapat class People yang perlu dilengkapi
    Anda bebas untuk menambah fungsi, variable, dll
    Anda juga diperbolehkan untuk tidak menggunakan template ini
 */
public class WS3Jumat {
    private static int n, m, t;

    private static InputReader in;
    private static PrintWriter out;
    private static ArrayList<Person> people = new ArrayList<Person>();

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        readInput();
        out.close();
    }

    /**
     * pada method ini Anda diminta untuk mengimplementasikan
     * fungsi untuk mencetak nama-nama orang yang ada di dalam list
     * @param ArrayList<People>
     */
    private static void printOutput(ArrayList<Person> people) throws IOException {
        for(Person person : people){
            System.out.print(person.getName() + " ");
        }
    }

    /**
     * ppada method ini Anda diminta untuk mengimplementasikan
     * fungsi yang menambahkan orang kedalam ArrayList
     * Selain itu fungsi ini juga akan mengembalikan nilai
     * berupa arraylist orang-orang yang dilewati
     * @param Person
     */
    private static ArrayList<Person> insertPeople(Person person) {
        ArrayList<Person> newPeople = new ArrayList<Person>();
        people.add(person);
        Collections.sort(people);
        int count = 0;
        for(Person p : people){
            if(p == person){
                break;
            }
            count++;
        }

        for(int i = people.size() - 1; i > count; i--){
            newPeople.add(people.get(i));
        }
        return newPeople;
    }

    private static void readInput() throws IOException {
        n = in.nextInt();
        t = in.nextInt();

        for (int i = 0; i < n; i++) {
            people.add(new Person(in.next()));
        }

        m = in.nextInt();

        ArrayList<Person> namronFriends = new ArrayList<Person>();
        for(int i = 0; i < m; i++) {
            // TO-DO
            namronFriends.add(new Person(in.next()));
            
        }


        if(t == 1){
            for(int i = 0; i < m; i++){
                ArrayList<Person> antrian = insertPeople(namronFriends.get(i));
                if(antrian.size() > 0){
                    printOutput(antrian);
                    System.out.println();
                } else{
                    System.out.println("tidak ada");
                }   
            }    
        } else if(t == 2){
            for(Person person : namronFriends){
                people.add(person);
            }
            Collections.sort(people);
            printOutput(people);
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
    }

    static class Person implements Comparable<Person> {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        /**
         * pada method ini Anda diminta untuk meng-override
         * method ini untuk membandingkan dua orang sesuai
         * dengan ketentuan yang ada di soal
         */
        @Override
        public int compareTo(Person other) {
            int sizeThis = this.name.length();
            int sizeOther = other.getName().length();
            if(sizeThis > sizeOther){
                return 1;
            } else if(sizeThis < sizeOther){
                return -1;
            }
            return this.name.compareTo(other.getName());
        }

        public String getName() {
            return this.name;
        }
    }
}