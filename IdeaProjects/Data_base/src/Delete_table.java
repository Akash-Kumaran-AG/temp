import java.io.*;
import java.util.*;

public class Delete_table {

    public static void delete_row_cond() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(Main.dataFile));
        ArrayList<String> remainingRows = new ArrayList<>();

        Select_data.size = 0;
        Select_data.ind = 0;
        Arrays.fill(Select_data.col, null);
        Arrays.fill(Select_data.val, null);
        Arrays.fill(Select_data.ops, null);

        Select_data.takeInput();

        String row;
        while ((row = reader.readLine()) != null) {

            Select_data.ind = 0;

            String[] row_data = row.split(",");

            if (!Select_data.check(row_data)) {
                remainingRows.add(row);
            }
        }

        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(Main.dataFile, false));
        for (String r : remainingRows) {
            writer.write(r);
            writer.newLine();
        }
        writer.close();

        System.out.println("Rows deleted successfully.");
    }
}
