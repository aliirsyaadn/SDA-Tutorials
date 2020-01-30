import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class DonatDuar{

    // Declare Variable
    static ArrayList<Toko> listToko = new ArrayList<Toko>(); // Unused
    static ArrayList<Donat> listDonat = new ArrayList<Donat>();
    static HashMap<String, Toko> dictToko = new HashMap<String, Toko>();
    public static void main(String[] args){
        int banyakToko,jumlahHari;
        Scanner input; 

        input = new Scanner(System.in);

        // System.out.print("Input banyak toko : ");
        banyakToko = Integer.parseInt(input.nextLine());

        // Looping untuk banyakToko
        for(int i = 0; i < banyakToko; i++){
            // System.out.println("Input infoToko ['namaToko' 'jumlahJenisDonat']");

            String[] infoToko = input.nextLine().split(" ");

            // Validasi format infoToko
            if (infoToko.length != 2){
                // System.out.println("Input format salah");
                return;
            }

            String namaToko = infoToko[0];
            int jumlahJenisDonat = Integer.parseInt(infoToko[1]);

            Toko toko = new Toko(namaToko);
            listToko.add(toko); // Unused

            dictToko.put(namaToko, toko);

            for(int j = 0; j < jumlahJenisDonat; j++){
                // System.out.println("Input infoDonat ['namaDonat' 'stockDonat' 'jumlahChocochips']");

                String[] infoDonat = input.nextLine().split(" ");

                // Validasi format infoDonat
                if (infoDonat.length != 3){
                    // System.out.println("Input format salah");
                    return;
                } 

                String namaDonat = infoDonat[0];
                int stockDonat = Integer.parseInt(infoDonat[1]);
                int jumlahChocochips = Integer.parseInt(infoDonat[2]);

                Donat donat = new Donat(namaDonat, stockDonat, jumlahChocochips);
                toko.addDonat(donat);

                listDonat.add(donat);
            }
        }
        // Akhir dari looping banyakToko

        // System.out.print("Input jumlah hari : ");
        jumlahHari = Integer.parseInt(input.nextLine());

        // Menampung banyak kemungkinan per hari
        long[] listKemungkinanKombinasiDonat = new long[jumlahHari];

        // Looping untuk jumlahHari
        for(int i = 0; i < jumlahHari; i++){
            

            // System.out.println("Input banyakTokoBuka P1 P2 P3 ... Pn");
            int banyakTokoBuka = Integer.parseInt(input.nextLine());

            // Validasi format listTokoBuka
            String[] listTokoBuka = new String[banyakTokoBuka];
            if(banyakTokoBuka > 0){
                listTokoBuka = input.nextLine().split(" ");
                if(banyakTokoBuka != listTokoBuka.length){
                    // System.out.println("Input format salah");
                    return;
                }
            }
            
            // System.out.print("Target ");
            String[] jumlahTarget = input.nextLine().split(" ");
            int targetChocochips = Integer.parseInt(jumlahTarget[1]);

            // Algoritma untuk menghitung banyak kemungkinan dari donat DUARRRRRRR
            long kemungkinanKombinasiDonat = 0;

            

            // Algoritma lain...
            ArrayList<Donat> listDonatBuka = new ArrayList<Donat>();
            
            for(int z = 0; z < banyakTokoBuka; z++){
                // Tolong nanti jadiin arraylist add to arraylist
                for(int y = 0; y < dictToko.get(listTokoBuka[z]).getListDonat().size(); y++){
                    listDonatBuka.add(dictToko.get(listTokoBuka[z]).getListDonat().get(y));
                }

            }
            // System.out.println("====================");
            // for(int g = 0; g < listDonatBuka.size(); g++){
            //     System.out.println("Target chocochips " + targetChocochips);
            //     System.out.print(listDonatBuka.get(g).getJumlahChocochips() + " ");
            //     System.out.println(listDonatBuka.get(g).getStockDonat());
            // }
            // System.out.println("=====================");

            kemungkinanKombinasiDonat = kombinasiDonat(listDonatBuka, listDonatBuka.size(), targetChocochips);

            
            listKemungkinanKombinasiDonat[i] = kemungkinanKombinasiDonat;

            // Sore hari
            // System.out.print("Duar ");
            // int banyakAktivitasDuar = Integer.parseInt(input.nextLine());

            String[] jumlahDuar = input.nextLine().split(" ");
            int banyakAktivitasDuar = Integer.parseInt(jumlahDuar[1]);
            

            for(int j = 0; j < banyakAktivitasDuar; j++){
                // System.out.println("Input infoDuar ['namaTokoDuar' 'namaDonatDuar' 'stockDonarDuar']");

                String[] infoDuar = input.nextLine().split(" ");
                String namaTokoDuar = infoDuar[0];
                String namaDonatDuar = infoDuar[1];
                int stockDonarDuar = Integer.parseInt(infoDuar[2]);

                // Code untuk membuat perubahan pada donat saat Duar
                dictToko.get(namaTokoDuar).getDonat(namaDonatDuar).duarDonat(stockDonarDuar);
            }

            // System.out.print("Restock ");
            // int banyakAktivitasRestock = Integer.parseInt(input.nextLine());

            String[] jumlahRestock = input.nextLine().split(" ");
            int banyakAktivitasRestock = Integer.parseInt(jumlahRestock[1]);

            for(int k = 0; k < banyakAktivitasRestock; k++){
                // System.out.println("Input infoRestock ['namaTokoRestock' 'namaDonatRestock' 'stockDonatRestock']");

                String[] infoRestock = input.nextLine().split(" ");
                String namaTokoRestock = infoRestock[0];
                String namaDonatRestock = infoRestock[1];
                int stockDonatRestock = Integer.parseInt(infoRestock[2]);
                int chocochipsDonatRestock = Integer.parseInt(infoRestock[3]);

                // Code untuk membuat perubahan pada donat saat Restock
                Toko tokoRestock = dictToko.get(namaTokoRestock);
                
                if(tokoRestock.getDictDonat().containsKey(namaDonatRestock)){
                    Donat donatRestock = tokoRestock.getDonat(namaDonatRestock);
                    if (donatRestock.getJumlahChocochips() != chocochipsDonatRestock){
                        if (donatRestock.getStockDonat() == 0){
                            donatRestock.restockDonat(stockDonatRestock);
                            donatRestock.setJumlahChocochips(chocochipsDonatRestock);
                        } //else{
                            // System.out.println("Restock gagal karena jumlah chocochips tidak sama");
                        // }
                        
                    } else {
                        donatRestock.restockDonat(stockDonatRestock);
                    }
                } else {
                    Donat newDonat = new Donat(namaDonatRestock,stockDonatRestock,chocochipsDonatRestock);
                    tokoRestock.addDonat(newDonat);

                    listDonat.add(newDonat);
                }
            }

            // System.out.print("Transfer ");
            // int banyakAktivitasTransfer = Integer.parseInt(input.nextLine());

            String[] jumlahTransfer = input.nextLine().split(" ");
            int banyakAktivitasTransfer = Integer.parseInt(jumlahTransfer[1]);

            for(int k = 0; k < banyakAktivitasTransfer; k++){
                // System.out.println("Input infoTransfer ['namaTokoAsal' 'namaTokoTujuan' 'namaDonatTransfer' 'stockDonatTransfer']");

                String[] infoTransfer = input.nextLine().split(" ");
                String namaTokoAsal = infoTransfer[0];
                String namaTokoTujuan = infoTransfer[1];
                String namaDonatTransfer = infoTransfer[2];
                int stockDonatTransfer = Integer.parseInt(infoTransfer[3]);

                // Code untuk membuat perubahan pada donat saat Transfer
                Toko tokoAsal = dictToko.get(namaTokoAsal);
                Toko tokoTujuan = dictToko.get(namaTokoTujuan);
                if(tokoTujuan.getDictDonat().containsKey(namaDonatTransfer)){
                    if(tokoAsal.getDonat(namaDonatTransfer).getJumlahChocochips() != tokoTujuan.getDonat(namaDonatTransfer).getJumlahChocochips()){
                        if(tokoTujuan.getDonat(namaDonatTransfer).getStockDonat() == 0){
                            tokoAsal.getDonat(namaDonatTransfer).duarDonat(stockDonatTransfer);
                            tokoTujuan.getDonat(namaDonatTransfer).restockDonat(stockDonatTransfer);

                            tokoTujuan.getDonat(namaDonatTransfer).setJumlahChocochips(tokoAsal.getDonat(namaDonatTransfer).getJumlahChocochips());
                        }
                        // System.out.println("Transfer gagal karena jumlah chocochips tidak sama");
                    } else{
                        tokoAsal.getDonat(namaDonatTransfer).duarDonat(stockDonatTransfer);
                        tokoTujuan.getDonat(namaDonatTransfer).restockDonat(stockDonatTransfer);
                    }
                } else {
                    int jumlahChocochipsDonatAsal = tokoAsal.getDonat(namaDonatTransfer).getJumlahChocochips();
                    Donat newDonat = new Donat(namaDonatTransfer, stockDonatTransfer, jumlahChocochipsDonatAsal);
                    tokoAsal.getDonat(namaDonatTransfer).duarDonat(stockDonatTransfer);
                    tokoTujuan.addDonat(newDonat);

                    listDonat.add(newDonat);
                }
                
            }

           
        }
        // Akhir dari looping jumlahHari

        // Looping untuk mencetak hasil
        for(int i = 0; i < jumlahHari; i++){
            System.out.println(listKemungkinanKombinasiDonat[i]);
        }

        input.close();
        

    }

    public static long kombinasiDonat(ArrayList<Donat> listDonatBuka, int index, int target){
        int[][] tabel = new int[index+1][target+1];
        for(int row = 0; row <= index; row++){
            tabel[row][0] = 1;
        }

        for(int col = 1; col <= target; col++){
            tabel[0][col] = 0;
        }

        for(int row = 1; row <= index; row++){
            for(int col = 1; col <= target; col++){
                int hasil;
                int choco = listDonatBuka.get(row-1).getJumlahChocochips();
                for(int jmlh = 0; jmlh <= listDonatBuka.get(row-1).getStockDonat(); jmlh++){
                    hasil = choco * jmlh;
                    
                    if(col-hasil < 0){
                        break;
                    } else{
                        tabel[row][col] = (tabel[row][col] + tabel[row-1][col-hasil] % 1000000007) % 1000000007;
                    }
                }
            }
        }
        return tabel[index][target] % 1000000007;
    }
}

class Toko{
    String namaToko;
    int jumlahJenisDonat;
    ArrayList<Donat> listDonat = new ArrayList<Donat>();
    HashMap<String, Donat> dictDonat = new HashMap<String, Donat>();


    public Toko(String namaToko){
        this.namaToko = namaToko;
        this.jumlahJenisDonat = 0;
    }

    public void addDonat(Donat donat){
        listDonat.add(donat);
        dictDonat.put(donat.getNamaDonat(), donat);
        jumlahJenisDonat += 1;
    }

    public Donat getDonat(String namaDonat){
        return dictDonat.get(namaDonat);
    }

    public HashMap<String, Donat> getDictDonat(){
        return this.dictDonat;
    }

    public ArrayList<Donat> getListDonat(){
        return this.listDonat;
    }

    public int getJumlahJenisDonat(){
        return this.jumlahJenisDonat;
    }
}

class Donat{
    String namaDonat;
    int stockDonat;
    int jumlahChocochips;

    public Donat(String namaDonat, int stockDonat, int jumlahChocochips){
        this.namaDonat = namaDonat;
        this.stockDonat = stockDonat;
        this.jumlahChocochips = jumlahChocochips;
    }

    public String getNamaDonat(){
        return this.namaDonat;
    }

    public int getStockDonat(){
        return this.stockDonat;
    }

    // Unused
    public void setStokDonat(int stockDonat){
        this.stockDonat = stockDonat;
    }

    public void setJumlahChocochips(int jumlahChocochips){
        this.jumlahChocochips = jumlahChocochips;
    }

    public void restockDonat(int stockDonatRestock){
        this.stockDonat += stockDonatRestock;
    }

    public void duarDonat(int stockDonarDuar){
        this.stockDonat -= stockDonarDuar;
    }

    public int getJumlahChocochips(){
        return jumlahChocochips;
    }
}