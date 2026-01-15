import java.io.*;
import java.util.*;

public class Delete_table {

    public static void delete_row_cond(Db_details context) throws IOException {

        BufferedReader reader =
                new BufferedReader(new FileReader(context.getDataFile()));

        ArrayList<String> remaining = new ArrayList<>();
        Select_data.takeInput();

        String row;
        while ((row = reader.readLine()) != null) {
            if (!Select_data.check(row.split(","))) {
                remaining.add(row);
            }
        }

        BufferedWriter writer =
                new BufferedWriter(new FileWriter(context.getDataFile(), false));

        for (String r : remaining) {
            writer.write(r);
            writer.newLine();
        }

        writer.close();
        System.out.println("Rows deleted successfully.");
    }
}