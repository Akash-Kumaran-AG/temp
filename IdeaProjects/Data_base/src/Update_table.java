import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Update_table {

    void update_table_name(String newName){
//        String loc=
        File newFile=new File(Main.BASE_FOLDER+"/"+newName);
//        Main.home=new File(Main.BASE_FOLDER+"");
        Main.home.renameTo(newFile);
    }
    void update_col_name(String old_col_name,String new_col_name) throws IOException {
//        File files[]= BASE_FOLDER.listFiles();
//        File home=new File("/home/akash-pt8147/IdeaProjects/Demo/twelve");
//        File schemaFile= new File(home,"schema.meta");
        File original = new File(Main.home, "schema.meta");
        File temp = new File(Main.home, "schema_temp.meta");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(original));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp))
        ) {
            String line = reader.readLine();
//            if (line != null) {
                String[] cols = line.split(",");

                for (int i = 0; i < cols.length; i++) {
                    String parts[]=cols[i].split("-");
                    if (parts[0].equals(old_col_name)) {
                        parts[0] = new_col_name;
                        for(int j=0;j<parts[i].length();j++) {
                            cols[i] = parts[i]+"-";

                        }
                    }
                }
                writer.write(String.join(",", cols));
                writer.newLine();
//            }
        }
        Files.move(
                temp.toPath(),
                original.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

    }
    void update_row_data(String col_name,String old_data,String new_data) throws IOException {
        int ind = find_col(col_name);

        File original = Main.dataFile;
        File tempFile = new File(Main.home, "data_temp.dat");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(original));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] datas = row.split(",");

                datas[ind] = datas[ind].replace(old_data, new_data);

                writer.write(String.join(",", datas));
                writer.newLine();
            }
        }

        Files.move(
                tempFile.toPath(),
                original.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

    }
    int find_col(String col_name) throws IOException {
        int index=-1;
//        File home=new File("/home/akash-pt8147/IdeaProjects/Demo/twelve");
//        File schemaFile= new File(home,"schema.meta");
        BufferedReader reader=new BufferedReader(new FileReader(Main.schemaFile));
        String colnames= reader.readLine();
        String columns[]=colnames.split(",");
        for(int str=0;str<columns.length;str++){
           if( columns[str].split("-")[0].equals(col_name)){
                index=str;
                break;
            }
        }
        return index;
    }
}
