import javax.net.ssl.SSLContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Insert_table {
//    private static final File BASE_FOLDER = new File("/home/akash-pt8147/IdeaProjects/Demo");
//
//    public void insert(){
//        File[] files = BASE_FOLDER.listFiles();
//
//        if (files != null) {
//            for (File f : files) {
//                if (f.isDirectory()) {
//                    System.out.println(f.getName());
//                }
//            }
//        }
//    }
//    File home=new File("/home/akash-pt8147/IdeaProjects/Demo/twelve");
//    File file=new File(home,"schema.meta");
    public void insert() throws IOException {
//        BufferedWriter schemawriter = new BufferedWriter(new FileWriter(Main.schemaFile,true));
        BufferedReader reader = new BufferedReader(new FileReader(Main.schemaFile));
        BufferedWriter datawriter = new BufferedWriter(new FileWriter(Main.dataFile,true));

        String col_names=reader.readLine();
        String cols[]=col_names.split(",");
        Scanner in=new Scanner(System.in);
//        Create_table insert=new Create_table();
//        ArrayList<String>temp=new ArrayList<>();
//            rows.add(new ArrayList<String>());
        boolean is_next=true;
        while(is_next) {
            is_next=false;
//            int col_nums= Create_table.rows.get(0).size();
            System.out.println("Enter your row ");
            for (int i = 0; i < cols.length; i++) {
                System.out.print(cols[i]+": ");
//                if(i!=col_nums-1){
                String st = in.nextLine();
//                temp.add(String.valueOf(st));
                if (i != cols.length-1) {
                    datawriter.write(st + ",");
                } else {
                    datawriter.write(st);
                    datawriter.newLine();
                }
            }
//            insert.rows.add(temp);
            System.out.println("Is there another row?(Y?N)");
            String another=in.nextLine();
            if(another.equals("Y")) is_next=true;

        }
        datawriter.close();

    }
}
