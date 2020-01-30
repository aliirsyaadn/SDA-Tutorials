import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class RakDonat{
    static ArrayList<LinkedList> listBaris = new ArrayList<LinkedList>();

    public static void main(String[] args) throws IOException{
        InputStream inputStream = System.in;
        InputReader input = new InputReader(inputStream);
        OutputStreamWriter o = new OutputStreamWriter(System.out);
        BufferedWriter out = new BufferedWriter(o);
        
        int banyakBaris, banyakOperasi;

        banyakBaris = input.nextInt();

        for(int i = 0; i < banyakBaris; i++){
            int panjangBaris = input.nextInt();
            LinkedList isiBaris = new LinkedList(); 
            for(int j = 0; j < panjangBaris; j++){
                isiBaris.addLast(input.nextInt());
            }

            listBaris.add(isiBaris);
        }
       
        banyakOperasi = input.nextInt();
        for(int i = 0; i < banyakOperasi; i++){
            String[] infoOperasi = input.nextLine().split(" ");
            String jenisOperasi = infoOperasi[0];
            int chips, baris, barisAsal, barisTujuan;
            LinkedList listAsal, listTujuan;
            
            switch(jenisOperasi){
                case "IN_FRONT":
                    chips = Integer.parseInt(infoOperasi[1]);
                    baris = Integer.parseInt(infoOperasi[2])-1;
                    listBaris.get(baris).addFirst(chips);
                    insertToArr(baris);
                    break;

                case "OUT_FRONT":
                    baris = Integer.parseInt(infoOperasi[1])-1;
                    listBaris.get(baris).getFirst();
                    if(listBaris.get(baris).isEmpty()){
                        listBaris.remove(baris);
                    } else{
                        insertToArr(baris);
                    }
                    break;

                case "IN_BACK":
                    chips = Integer.parseInt(infoOperasi[1]);
                    baris = Integer.parseInt(infoOperasi[2])-1;
                    listBaris.get(baris).addLast(chips);
                    insertToArr(baris);
                    break;

                case "OUT_BACK":
                    baris = Integer.parseInt(infoOperasi[1])-1;
                    listBaris.get(baris).getLast();
                    if(listBaris.get(baris).isEmpty()){
                        listBaris.remove(baris);
                    } else{
                        insertToArr(baris);
                    }
                    break;

                case "MOVE_FRONT":
                    barisAsal = Integer.parseInt(infoOperasi[1])-1;
                    barisTujuan = Integer.parseInt(infoOperasi[2])-1;
                    listAsal = listBaris.get(barisAsal);
                    listTujuan = listBaris.get(barisTujuan);
                    listAsal.addLinkedList(listTujuan);
                    listBaris.remove(barisTujuan);
                    barisAsal = barisAsal > barisTujuan ? barisAsal-1 : barisAsal;
                    insertToArr(barisAsal);
                    break;

                case "MOVE_BACK":
                    barisAsal = Integer.parseInt(infoOperasi[1])-1;
                    barisTujuan = Integer.parseInt(infoOperasi[2])-1;
                    listAsal = listBaris.get(barisAsal);
                    listTujuan = listBaris.get(barisTujuan);
                    listTujuan.addLinkedList(listAsal);
                    listBaris.remove(barisAsal); 
                    barisTujuan = barisTujuan > barisAsal ? barisTujuan-1 : barisTujuan;
                    insertToArr(barisTujuan);
                    break;

                case "NEW":
                    chips = Integer.parseInt(infoOperasi[1]);
                    LinkedList newList = new LinkedList();
                    newList.addLast(chips);
                    listBaris.add(newList);
                    insertToArr(listBaris.size()-1);
                    break;
            } 
        }
        
        // Print Hasil
        for(LinkedList arr : listBaris){
            Node iterationNode = arr.getHeader();
            while(iterationNode.hasNext()){
                iterationNode = iterationNode.getNext();
                out.write(iterationNode.getData() + " ");  
            }
            out.newLine();
        }
        out.flush();
        out.close();
    }

    /**
     * Method untuk add elemen sesuai parameter index yang berubah ke tempat yang benar 
     * pada sorted arraylist
     *  */ 
    public static void insertToArr(int index){
        LinkedList temp = listBaris.remove(index);
        int indexInsert = index;

        if(index <= listBaris.size()){
            for(int i = index-1; i >= 0; i--){
                if(!temp.lessThan(listBaris.get(i))){
                    break;
                }
                indexInsert = i;
            }
        }

        if(index >= 0){
            for(int j = index; j < listBaris.size(); j++){
                if(temp.lessThan(listBaris.get(j))){
                    break;
                }
                indexInsert = j+1;
            }
        }

        if(indexInsert > index){
            listBaris.add(indexInsert, temp);
            return;
        }
        listBaris.add(indexInsert, temp);
        
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

class LinkedList{
    private Node header;
    private Node lastNode;

    LinkedList(){
        this.header = new Node(0);
        this.lastNode = header;
    }

    void addFirst(int data){
        Node newNode = new Node(data);
        newNode.setNext(this.header.getNext());
        this.header.setNext(newNode);
    }

    void addLast(int data){
        Node newNode = new Node(data);
        this.lastNode.setNext(newNode);
        this.lastNode = newNode;
    }

    int getFirst(){
        int data = this.header.getNext().getData();
        this.header.setNext(this.header.getNext().getNext());
        return data;
    }

    int getLast(){
        int data = this.lastNode.getData();
        Node iterationNode = this.header;
        while(iterationNode.hasNext()){; 
            if(iterationNode.getNext().getNext() == null){
                break;
            }
            iterationNode = iterationNode.getNext();
        }
        
        iterationNode.setNext(null);
        this.lastNode = iterationNode;
        return data;
    }

    Node getLastNode(){
        return this.lastNode;
    }

    void addLinkedList(LinkedList newList){
        this.lastNode.setNext(newList.getHeader().getNext());
        this.lastNode = newList.getLastNode();
    }

    Node getHeader(){
        return this.header;
    }

    boolean isEmpty(){
        return header.getNext() == null;
    }

    int size(){
        int count = 0;
        Node iterationNode = this.header;
        while(iterationNode.hasNext()){; 
            count += 1;
            iterationNode = iterationNode.getNext();
        }
        return count;
    }

    boolean lessThan(LinkedList other){
        int count = this.size() < other.size() ? this.size() : other.size();

        Node iterationThis = this.header.getNext();
        Node iterationOther = other.getHeader().getNext();

        for(int i = 0; i < count; i++){
            if(iterationThis.getData() > iterationOther.getData()){
                return false;
            } else if(iterationThis.getData() < iterationOther.getData()){
                return true;
            }
            iterationThis = iterationThis.getNext();
            iterationOther = iterationOther.getNext();
        }

        return this.size() < other.size();    
    }
}

class Node{
    private int data;
    private Node next;

    Node(int data){
        this.data = data;
        this.next = null;
    }

    void setNext(Node next){
        this.next = next;
    }

    boolean hasNext(){
        return this.next != null;
    }

    Node getNext(){
        return this.next;
    }

    int getData(){
        return this.data;
    }
}