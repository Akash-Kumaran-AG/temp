import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Update_table{

    static void update_table_name(String newName) {
//        String loc=
        File newFile = new File(Main.BASE_FOLDER + "/" + newName);
//        Main.home=new File(Main.BASE_FOLDER+"");
        Main.home.renameTo(newFile);
        Main.home=new File(newFile,"");
        Main.schemaFile=new File(newFile,"schema.meta");
        Main.dataFile=new File(newFile,"data.dat");
    }

    static void update_col_name(String old_col_name, String new_col_name) throws IOException {
//        File files[]= BASE_FOLDER.listFiles();
//        File home=new File("/home/akash-pt8147/IdeaProjects/Demo/twelve");
//        File schemaFile= new File(home,"schema.meta");
        File original = new File(Main.home, "schema.meta");
        File temp = new File(Main.home, "schema_temp.meta");


        BufferedReader reader = new BufferedReader(new FileReader(original));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String line = reader.readLine();
//            if (line != null) {
        String[] cols = line.split(",");

        for (int i = 0; i < cols.length; i++) {
            String parts[] = cols[i].split("-");
            if (parts[0].equals(old_col_name)) {
                parts[0] = new_col_name;
                for (int j = 0; j < parts[i].length(); j++) {
                    cols[i] = parts[i] + "-";

                }
            }
        }
        writer.write(String.join(",", cols));
        writer.newLine();
//            }

        Files.move(
                temp.toPath(),
                original.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

    }

    static void update_row_cond() throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.print("Enter column name to update: ");
        String updateCol = in.nextLine();

        System.out.print("Enter new value: ");
        String newValue = in.nextLine();

        BufferedReader reader = new BufferedReader(new FileReader(Main.dataFile));
        ArrayList<String> updatedRows = new ArrayList<>();

        Select_data.size = 0;
        Select_data.ind = 0;

        Select_data.takeInput();

        String row;
        while ((row = reader.readLine()) != null) {

            Select_data.ind = 0;

            String[] row_data = row.split(",");

            if (Select_data.check(row_data)) {
                int colIndex = Select_data.find_col(updateCol);
                row_data[colIndex] = newValue;
            }

            updatedRows.add(String.join(",", row_data));
        }

        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(Main.dataFile, false));
        for (String r : updatedRows) {
            writer.write(r);
            writer.newLine();
        }
        writer.close();

        System.out.println("Rows updated successfully.");
    }

    static void update_row_data(String col_name, String old_data, String new_data) throws IOException {

        int ind = find_col(col_name);

        File original = Main.dataFile;
        File tempFile = new File(Main.home, "data_temp.dat");


        BufferedReader reader = new BufferedReader(new FileReader(original));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String row;
        while ((row = reader.readLine()) != null) {
            String[] datas = row.split(",");

            datas[ind] = datas[ind].replace(old_data, new_data);

            writer.write(String.join(",", datas));
            writer.newLine();
        }


        Files.move(
                tempFile.toPath(),
                original.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

    }

    static int find_col(String col_name) throws IOException {
        int index = -1;
//        File home=new File("/home/akash-pt8147/IdeaProjects/Demo/twelve");
//        File schemaFile= new File(home,"schema.meta");
        BufferedReader reader = new BufferedReader(new FileReader(Main.schemaFile));
        String colnames = reader.readLine();
        String columns[] = colnames.split(",");
        for (int str = 0; str < columns.length; str++) {
            if (columns[str].split("-")[0].equals(col_name)) {
                index = str;
                break;
            }
        }
        return index;
    }
}
