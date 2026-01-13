import java.io.*;

public class Select_data {



    public static void Select_all() throws IOException {
//        Create_table obj = new Create_table();
//        Scanner in = new Scanner(System.in);
//        System.out.println("Select by which column");
//        String str = in.nextLine();
//        BufferedReader schemaReader = new BufferedReader(new FileReader(Main.schemaFile));
        BufferedReader dataReader = new BufferedReader(new FileReader(Main.dataFile));
        String row="";
        while((row=dataReader.readLine())!=null){
            System.out.println(row);

        }




//        reader.readLine();
    }
    public static void Select(String col_name, String data) throws IOException {
//            System.out.println("Select by which data");

        BufferedReader schemaReader = new BufferedReader(new FileReader(Main.schemaFile));
        BufferedReader dataReader = new BufferedReader(new FileReader(Main.dataFile));
//            String data = in.nextLine();
        int index = -1;
            String columns=schemaReader.readLine();
            System.out.println(columns);
            String colnames[]=columns.split(",");
            for(int i=0;i<colnames.length;i++){
                if(colnames[i].split("-")[0].equals(col_name)){
                    index=i;
                    break;
                }
            }
            if(index==-1){
                System.out.println("Column name not found");
            }else{
                String row="";
                    boolean isFound=false;
                while((row=dataReader.readLine())!=null){
//                    System.out.println(row);
                    String datas[]=row.split(",");
                    if(datas[index].equals(data)){
                        System.out.println(row);
                        isFound=true;
                    }
                }
                if(!isFound){
                    System.out.println("Data not found inside this table");
                }
            }
        }

    }

