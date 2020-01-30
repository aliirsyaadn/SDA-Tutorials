import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.io.IOException;


public class PemiluDonatRayaV4{

    public static void main(String[] args) throws IOException{
        InputStream inputStream = System.in;
        InputReader in = new InputReader(inputStream);
        OutputStreamWriter o = new OutputStreamWriter(System.out);
        BufferedWriter out = new BufferedWriter(o);
        
        String kandidat1 = in.next();
        String kandidat2 = in.next();

        int jumlahWilayah = in.nextInt();

        Tree selisih = new Tree();
        Tree persen = new Tree();

        String namaNasional = in.next();
        Wilayah nasional = new Wilayah(namaNasional);

        Pemilu Pemilu = new Pemilu(nasional, kandidat1, kandidat2); 

        selisih.insert(0D);
        persen.insert(50D);

        int jumlahProvinsi = in.nextInt();
        for(int i = 0; i < jumlahProvinsi; i++){
            String namaProvinsi = in.next();
            Wilayah provinsi = new Wilayah(namaProvinsi, nasional);

            Pemilu.tambahProvinsi(namaProvinsi, provinsi);
            selisih.insert(0D);
            persen.insert(50D);
        }

        for(int i = 1; i < jumlahWilayah; i++){
            String namaWilayah = in.next();
            Wilayah wilayah = Pemilu.getWilayah(namaWilayah);
            Pemilu.tambahWilayah(namaWilayah, wilayah);

            int jumlahSubWilayah = in.nextInt();
            for(int j = 0; j < jumlahSubWilayah; j++){
                String namaSubWilayah = in.next();
                Wilayah subWilayah = new Wilayah(namaSubWilayah, wilayah);
                Pemilu.tambahWilayah(namaSubWilayah, subWilayah);

                selisih.insert(0D);
                persen.insert(50D);
            }

            
        }
        
        int jumlahPerintah = in.nextInt();
        for(int i = 0; i < jumlahPerintah; i++){
            String perintah = in.next();
            String namaWilayah;
            Long selisihSekarang, selisihSebelum, totalSuara, suara1, suara2;
            Double persenSekarang, persenSebelum;
            Wilayah wilayah;
            switch(perintah){
                case "TAMBAH":
                    namaWilayah = in.next();
                    suara1 = in.nextLong();
                    suara2 = in.nextLong();
                    totalSuara = suara1 + suara2;
                    Pemilu.tambahSuara(namaWilayah, suara1, suara2);

                    wilayah = Pemilu.getWilayah(namaWilayah);          
                    while(wilayah != null){
                        selisihSekarang = wilayah.getSuaraKandidat1() - wilayah.getSuaraKandidat2(); 
                        selisihSebelum = (wilayah.getSuaraKandidat1() - suara1) - (wilayah.getSuaraKandidat2() - suara2);
                        
                        persenSekarang = wilayah.getTotalSuara() == 0 ? 50D : (double) (wilayah.getSuaraKandidat1() * 100) / (double) wilayah.getTotalSuara();
                        persenSebelum = (wilayah.getTotalSuara() - totalSuara) == 0 ? 50D :(double) ((wilayah.getSuaraKandidat1() - suara1) * 100) / (double) (wilayah.getTotalSuara() - totalSuara);
      
                        selisih.remove((double) Math.abs(selisihSebelum));
                        selisih.insert((double) Math.abs(selisihSekarang));
                        
                        persen.remove(persenSebelum);
                        persen.insert(persenSekarang);    

                        wilayah = wilayah.superWilayah;
                    }
                    break;
                case "ANULIR":
                    namaWilayah = in.next();
                    suara1 = in.nextLong();
                    suara2 = in.nextLong();
                    totalSuara = suara1 + suara2;
                    Pemilu.anulirSuara(namaWilayah, suara1, suara2);

                    wilayah = Pemilu.getWilayah(namaWilayah);          
                    while(wilayah != null){
                        selisihSekarang = wilayah.getSuaraKandidat1() - wilayah.getSuaraKandidat2(); 
                        selisihSebelum = (wilayah.getSuaraKandidat1() + suara1) - (wilayah.getSuaraKandidat2() + suara2);

                        persenSekarang = wilayah.getTotalSuara() == 0 ? 50D : (double) (wilayah.getSuaraKandidat1() * 100) / (double) wilayah.getTotalSuara();
                        persenSebelum = (wilayah.getTotalSuara() + totalSuara) == 0 ? 50D :(double) ((wilayah.getSuaraKandidat1() + suara1) * 100) / (double) (wilayah.getTotalSuara() + totalSuara);
        
                        selisih.remove((double) Math.abs(selisihSebelum));
                        selisih.insert((double) Math.abs(selisihSekarang));

                        persen.remove(persenSebelum);
                        persen.insert(persenSekarang);

                        wilayah = wilayah.superWilayah;
                    }
                    break;
                case "CEK_SUARA":
                    out.write(Pemilu.cekSuara(in.next()));
                    out.newLine();
                    break;
                case "CEK_SUARA_PROVINSI":
                    for(Wilayah provinsi : Pemilu.getListProvinsi()){
                        out.write(String.format("%s %s", provinsi.namaWilayah, provinsi.cekSuara()));
                        out.newLine();
                    }
                    break;
                case "WILAYAH_MENANG":
                    namaWilayah = in.next();

                    out.write(Pemilu.jumlahWilayahMenang(persen, namaWilayah));
                    out.newLine();
                    break;
                case "WILAYAH_MINIMAL":
                    namaWilayah = in.next();
                    persenSekarang = in.nextDouble();

                    out.write(Pemilu.jumlahWilayahMinimal(persen, namaWilayah, persenSekarang));
                    out.newLine();
                    break;
                case "WILAYAH_SELISIH":
                    selisihSekarang = in.nextLong();
                    
                    out.write(Pemilu.jumlahWilayahSelisih(selisih, selisihSekarang));
                    out.newLine();
                    break;
            }
        }
       
        
        out.flush();
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

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public Long nextLong() {
            return Long.parseLong(next());
        }

        public Double nextDouble(){
            return Double.parseDouble(next());
        }

    }
}

class Wilayah{
    Wilayah superWilayah;
    String namaWilayah;
    Long suaraKandidat1, suaraKandidat2;
    
    Wilayah(String namaWilayah, Wilayah superWilayah){
        this.namaWilayah = namaWilayah;
        this.suaraKandidat1 = 0L;
        this.suaraKandidat2 = 0L;
        this.superWilayah = superWilayah;
    }

    Wilayah(String namaWilayah){
        this(namaWilayah, null);
    }

    void tambahSuara(Long suaraKandidat1, Long suaraKandidat2){
        this.suaraKandidat1 += suaraKandidat1;
        this.suaraKandidat2 += suaraKandidat2;
        if(superWilayah != null){
            superWilayah.tambahSuara(suaraKandidat1, suaraKandidat2);
        }
    }

    void anulirSuara(Long suaraKandidat1, Long suaraKandidat2){
        this.suaraKandidat1 -= suaraKandidat1;
        this.suaraKandidat2 -= suaraKandidat2;
        if(this.superWilayah != null){
            this.superWilayah.anulirSuara(suaraKandidat1, suaraKandidat2);
        }
    }

    String cekSuara(){
        return String.format("%d %d", this.suaraKandidat1, this.suaraKandidat2);
    }

    Long getSuaraKandidat1(){
        return this.suaraKandidat1;
    }

    Long getSuaraKandidat2(){
        return this.suaraKandidat2;
    }

    Long getTotalSuara(){
        return this.suaraKandidat1 + this.suaraKandidat2;
    }
}

class Pemilu{
    Wilayah nasional;
    HashMap<String, Wilayah> dictWilayah = new HashMap<>();
    ArrayList<Wilayah> listProvinsi = new ArrayList<>();
    String kandidat1, kandidat2;

    Pemilu(Wilayah nasional, String kandidat1, String kandidat2){
        this.nasional = nasional;
        this.dictWilayah.put(nasional.namaWilayah, nasional);
        this.kandidat1 = kandidat1;
        this.kandidat2 = kandidat2;
    }

    void tambahWilayah(String namaWilayah, Wilayah wilayah){
        this.dictWilayah.put(namaWilayah, wilayah);
    }

    void tambahProvinsi(String namaProvinsi, Wilayah provinsi){
        this.dictWilayah.put(namaProvinsi, provinsi);
        this.listProvinsi.add(provinsi);
    }

    Wilayah getWilayah(String namaWilayah){
        return this.dictWilayah.get(namaWilayah);
    }

    void tambahSuara(String namaWilayah, Long suaraKandidat1, Long suaraKandidat2){
        Wilayah WilayahWilayah = this.dictWilayah.get(namaWilayah);
        WilayahWilayah.tambahSuara(suaraKandidat1, suaraKandidat2);
    }

    void anulirSuara(String namaWilayah, Long suaraKandidat1, Long suaraKandidat2){
        Wilayah WilayahWilayah = this.dictWilayah.get(namaWilayah);
        WilayahWilayah.anulirSuara(suaraKandidat1, suaraKandidat2);
    }

    String cekSuara(String namaWilayah){
        Wilayah WilayahWilayah = this.dictWilayah.get(namaWilayah);
        return WilayahWilayah.cekSuara();
    }

    ArrayList<Wilayah> getListProvinsi(){
        return this.listProvinsi;
    }

    String wilayahMenang(String kandidat){
        Long count = 0L;
        if(kandidat.equals(this.kandidat1)){
            for(Wilayah wilayah : this.dictWilayah.values()){
                if(wilayah.suaraKandidat1 > wilayah.suaraKandidat2){
                    count++;
                }
            }
        } else if(kandidat.equals(this.kandidat2)){
            for(Wilayah wilayah : this.dictWilayah.values()){
                if(wilayah.suaraKandidat2 > wilayah.suaraKandidat1){
                    count++;
                }
            }
        }
        return Long.toString(count);
    }

    String jumlahWilayahMenang(Tree tree, String kandidat){
        if(this.kandidat1.equals(kandidat)){
            return Long.toString(jumlahWilayahMinimal(tree.root, 50.00000000000001D));
        } else if(this.kandidat2.equals(kandidat)){
            return Long.toString(jumlahWilayahMaksimal(tree.root, 49.99999999999999D));
        }
        return "";
    }

    String jumlahWilayahSelisih(Tree tree, Long selisih){
        return Long.toString(jumlahWilayahSelisih(tree.root, selisih));
    }

    private Long jumlahWilayahSelisih(Node node, Long selisih){
        if(node == null){
            return 0L;
        } else if(selisih <= node.value){
            Long totalWeightKanan = (node.right == null) ? 0L : (long) node.right.totalWeight();
            return node.count + totalWeightKanan + jumlahWilayahSelisih(node.left, selisih);
        }
        return jumlahWilayahSelisih(node.right, selisih);  
    }

    String jumlahWilayahMinimal(Tree tree, String kandidat, Double persenMinimal){
        if(this.kandidat1.equals(kandidat)){
            return Long.toString(jumlahWilayahMinimal(tree.root, persenMinimal));
        } else if(this.kandidat2.equals(kandidat)){
            Double persenMaksimal = 100D - persenMinimal;
            return Long.toString(jumlahWilayahMaksimal(tree.root, persenMaksimal));
        }
        return "";
    }

    private Long jumlahWilayahMinimal(Node node, Double persen){
        if(node == null){
            return 0L;
        } else if(persen <= node.value){
            Long totalWeightRight = (node.right == null) ? 0L : (long) node.right.totalWeight();
            return node.count + totalWeightRight + jumlahWilayahMinimal(node.left, persen); 
        }
        return jumlahWilayahMinimal(node.right, persen);
    }

    private Long jumlahWilayahMaksimal(Node node, Double persen){
        if(node == null){
            return 0L;
        } else if(persen >= node.value){
            Long totalWeightLeft = (node.left == null) ? 0L : (long) node.left.totalWeight();
            return node.count + totalWeightLeft + jumlahWilayahMaksimal(node.right, persen);
        }
        return jumlahWilayahMaksimal(node.left, persen);  
    }


    

}

class Tree{
    Node root;

    Tree(Node root){
        this.root = root;
    }

    Tree(){
        this(null);
    }

    void insert(Double value){
        this.root = insert(value, this.root);
    }

    private Node insert(Double value, Node node){
        if(node == null){
            return new Node(value);
        } else if(value.equals(node.value)){
            node.count += 1;
            node.updateWeight();
            return node;
        } else if(value < node.value){
            node.left = insert(value, node.left);
        } else if(value > node.value){
            node.right = insert(value, node.right);
        }
        
        // Update height
        node.updateHeight();

        // Update weight
        node.updateWeight();

        int balance = node.getBalance();

        
        if(balance > 1 && value < node.left.value){ // Left Left Case
            return singleRotateWithLeftChild(node);
        } else if(balance > 1 && value > node.left.value){ // Left Right Case
            return doubleRotateWithLeftChild(node);
        } else if(balance < -1 && value > node.right.value){ // Right Right Case
            return singleRotateWithRightChild(node);
        } else if(balance < -1 && value < node.right.value){ // Right Left Case
            return doubleRotateWithRightChild(node);
        }

        return node;
    }

    void remove(Double value){
        this.root = remove(value, this.root);

    }

    private Node remove(Double value, Node node) {     
        if(node == null){
            return node;
        } else if(value < node.value){         
            node.left = remove(value, node.left);     
        } else if(value > node.value){         
            node.right = remove(value, node.right);
        } else {
            if(node.count > 1){
                node.count -= 1;
                node.updateWeight();
                return node;
            } else if((node.left == null) || (node.right == null)){
                Node temp = node.left != null ? node.left : node.right;

                // Tidak punya child
                if(temp == null){
                    temp = node;
                    node = null;
                } else { // Punya child 1
                    node = temp;
                }           
            } else { // Punya child 2
                // Successor Inorder
                Node temp = findMin(node.right);

                node.value = temp.value;
                node.count = temp.count;
                temp.count = 1;

                // Hapus Successor Inorder
                node.right = remove(temp.value, node.right);

            }
        }
        
        if(node == null){
            return node;
        }

        // Update height
        node.updateHeight();

        // Update weight
        node.updateWeight();

        // Cek Balance
        int balance = node.getBalance();

        
        if(balance > 1 && node.left.getBalance() >= 0){ // Left Left Case
            return singleRotateWithLeftChild(node);
        } else if(balance > 1 && node.left.getBalance() < 0){ // Left Right Case
            return doubleRotateWithLeftChild(node);
        } else if(balance < -1 && node.right.getBalance() <= 0){ // Right Right Case
            return singleRotateWithRightChild(node);
        } else if(balance < -1 && node.right.getBalance() > 0){ // Right Left Case
            return doubleRotateWithRightChild(node);
        }

        return node; 
    }

    Node findMin(Node node){
        if(node == null) return node;
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    Node singleRotateWithLeftChild(Node k2) {
        Node k1 = k2.left;  
        Node t2 = k1.right;
             
        k1.right = k2;
        k2.left = t2;

        k2.updateHeight();
        k1.updateHeight();

        k2.updateWeight();
        k1.updateWeight();

        return k1;
    } 

    Node singleRotateWithRightChild(Node k2) {
        Node k1 = k2.right;
        Node t2 = k1.left;

        k1.left = k2;
        k2.right = t2;
        
        k2.updateHeight();
        k1.updateHeight();

        k2.updateWeight();
        k1.updateWeight();

        return k1;
    } 

    Node doubleRotateWithLeftChild(Node k3 ) {     
        k3.left = singleRotateWithRightChild( k3.left );     
        return singleRotateWithLeftChild( k3 ); 
    } 

    Node doubleRotateWithRightChild(Node k3 ) {     
        k3.right = singleRotateWithLeftChild( k3.right );     
        return singleRotateWithRightChild( k3 ); 
    }
}

class Node{
    Double value;
    int height, count, weightLeft, weightRight;
    Node left, right;

    Node(Double value){
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1;
        this.count = 1;
        this.weightLeft = 0;
        this.weightRight = 0;
    }

    void updateWeight(){
        this.weightLeft = (this.left == null) ? 0 : this.left.totalWeight();
        this.weightRight = (this.right == null) ? 0 : this.right.totalWeight();
    }

    int totalWeight(){
        return this.weightLeft + this.weightRight + this.count;    
    }

    void updateHeight(){
        int heightLeft = (this.left == null) ? 0 : this.left.height;
        int heightRight = (this.right == null) ? 0 : this.right.height;
        this.height = Math.max(heightLeft, heightRight) + 1;
    }

    int getBalance(){
        int heightLeft = (this.left == null) ? 0 : this.left.height;
        int heightRight = (this.right == null) ? 0 : this.right.height;

        return heightLeft - heightRight;
    }
 
}