import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Create_table {
//    private final File BASE_FOLDER = new File("/home/akash-pt8147/IdeaProjects/Demo");
//    public static ArrayList<File>all_files=new ArrayList<>();
//    all_files=BASE_FOLDER;
    public ArrayList<ArrayList<String>>rows=new ArrayList<>();
//    File BASE_FOLDER =

//    public static void print_path(){
//        System.out.println(BASE_FOLDER.getAbsolutePath());
//    }
    public void create_table(String table_name) throws IOException {
        Scanner in=new Scanner(System.in);
        File file_name=new File(Main.BASE_FOLDER,table_name);
        if(!file_name.exists()) {
            file_name.mkdir();
            System.out.println("Table created in the name of :" + table_name);

            Main.home = new File(file_name+ "/schema.meta");
            BufferedWriter writer = new BufferedWriter(new FileWriter(Main.home));
//            writer.write(table_name);
//            writer.newLine();
            System.out.print("Enten the number of columns: ");
            int col_nums=in.nextInt();
            in.nextLine();

            ArrayList<String>col_name=new ArrayList<>();
            ArrayList<String>col_type=new ArrayList<>();
            ArrayList<Boolean>is_Pk=new ArrayList<>();

            for(int i=0;i<col_nums;i++){
                System.out.println("Enter the column name "+(i+1));
                String cname=in.nextLine();
                if(!col_name.contains(cname))
                col_name.add(cname);
                else{
                    System.out.println("Column name already exists... enter any other name");
                    i--;
                    continue;
                }

                System.out.println("Enter the column type ");
                col_type.add(in.nextLine());

                System.out.println("Is it a Primary Key(Y/N)");
                String pk=in.nextLine();

                if(pk.equals("Y")){
                    writer.write(col_name.get(i)+"-"+col_type.get(i)+"-"+"PK");
                    is_Pk.add(true);
                }else{
                    writer.write(col_name.get(i)+"-"+col_type.get(i));
                    is_Pk.add(false);
                }writer.write(",");
            }
            writer.newLine();
            writer.close();
            File infile=new File(file_name,"data.dat");
            BufferedWriter datawriter=new BufferedWriter(new FileWriter(infile,true));
            ArrayList<String>temp=new ArrayList<>();
//            rows.add(new ArrayList<String>());

            boolean is_next=true;
            while(is_next) {
                is_next=false;
                System.out.println("Enter your row ");
                for (int i = 0; i < col_nums; i++) {
                    System.out.print(col_name.get(i)+": ");

//                if(i!=col_nums-1){
                    String st = in.nextLine();
                    temp.add(String.valueOf(st));
                    if (i != col_nums - 1) {
                        datawriter.write(st + ",");
                    } else {
                        datawriter.write(st);
                        datawriter.newLine();
                    }
                }
                rows.add(temp);
                System.out.println("Is there another row?(Y?N)");
                String another=in.nextLine();
                if(another.equals("Y")) is_next=true;

            }
            datawriter.close();

        }else{
            System.out.println("Table creation failed. "+table_name+" may already exists.");
        }


    }
}
