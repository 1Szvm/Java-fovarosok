import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    public class Varos{
        String oraszag;
        String rovidetes;
        Integer lakossag;
        String fovaros;
        Integer fovaroslakossag;

        public Varos(String sor){
            String[] s =sor.split(";");
            oraszag=s[0];
            rovidetes=s[1];
            lakossag=Integer.parseInt(s[2]);
            fovaros=s[3];
            fovaroslakossag= Integer.parseInt(s[4]);
        }
    }

    public ArrayList<Varos> varosok = new ArrayList<>();

    public  Main() {
        betolt("fovaros.csv");

        //0.feladat
        System.out.printf("0) Összesen %d ország adata lett beolvasva.\n",varosok.size());

        //1.feladat
        Varos max1=null;
        Varos max2=null;

        for(Varos v:varosok){
            if(max1==null || v.lakossag> max1.lakossag){
                max2=max1;
                max1=v;
            }else if (max2 == null || v.lakossag > max2.lakossag) {
                max2 = v;
            }
        }

        System.out.printf("1) Az ország, ahol a legtöbben élnek: %s, %,d fő\n",max1.oraszag,max1.lakossag);
        System.out.printf("   A második legnagyobb népesség: %s, %,d fő\n",max2.oraszag,max2.lakossag);

        //2.feladat
        int reference=0;
        for(Varos v:varosok){
            if(v.fovaros.equals("Budapest")) reference=v.fovaroslakossag;
        }

        int count=0;
        for(Varos v:varosok){
            if(v.fovaroslakossag>reference) count++;
        }

        System.out.printf("2) Összesen %d fővárosban élnek kevesebben, mint Budapesten!\n",count);

        //3.feladat
        TreeSet<String> c=new TreeSet<>();
        for(Varos v:varosok){
            if(v.rovidetes.contains("C")) c.add(v.rovidetes);
        }

        System.out.print("3) Országjel, amiben szerepel 'C' betű:");
        for(String rovid:c){
            System.out.print(" "+rovid);
        }
        System.out.println();

        //4.feladat
        int sum=0;
        for(Varos v:varosok){
            if(v.lakossag<20000000) sum+=v.fovaroslakossag;
        }
        System.out.printf("4) A 20 millió főnél kisebb országok fővárosainak össznépessége: %,d fő\n",sum);

        //5.feladat
        TreeMap<Integer, Integer> sort = new TreeMap<>();

        for (Varos v : varosok) {
            int lakossag = v.fovaroslakossag;
            int alsoHatar = (lakossag / 5_000_000) * 5;

            sort.put(alsoHatar, sort.getOrDefault(alsoHatar, 0) + 1);
        }

        System.out.println("5) Fővárosok népesség szerint csoportosítva (5 millió fő):");
        for (Integer also : sort.keySet()) {
            int felso = also + 4;
            System.out.printf("\t%d 000 000 - %d 999 999: %d\n", also, felso, sort.get(also));
        }

        //6.feladat
        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("nagyok.txt"),"utf-8");
            for(Varos v:varosok){
                if(v.lakossag>=200000000) {
                    ki.printf("%s (%d millio)\r\n",v.oraszag,v.lakossag/1000000);
                };
            }
        }catch (UnsupportedEncodingException e){
            System.out.println(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(ki !=null) ki.close();
        }
        System.out.printf("6) Nagy népességű országok a nagyok.txt fájlban!\n");
    }

    private void betolt(String FajNev){
        Scanner be = null;
        try{
            be= new Scanner(new File(FajNev),"utf-8");
            be.nextLine();
            while (be.hasNextLine()){
                varosok.add(new Varos(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}