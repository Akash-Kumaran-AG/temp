import java.io.*;
import java.util.*;

static void update_row_cond_multi() throws IOException {

    Scanner in = new Scanner(System.in);

    System.out.print("How many columns to update? ");
    int n = Integer.parseInt(in.nextLine());

    String[] updateCols = new String[n];
    String[] newValues = new String[n];

    for (int i = 0; i < n; i++) {
        System.out.print("Column " + (i + 1) + " name: ");
        updateCols[i] = in.nextLine();

        System.out.print("New value: ");
        newValues[i] = in.nextLine();
    }

    BufferedReader reader = new BufferedReader(new FileReader(Main.dataFile));
    ArrayList<String> updatedRows = new ArrayList<>();

    // 🔴 reset condition state
    Select_data.size = 0;
    Select_data.ind = 0;
    Arrays.fill(Select_data.col, null);
    Arrays.fill(Select_data.val, null);
    Arrays.fill(Select_data.ops, null);

    // reuse nested condition logic
    Select_data.takeInput();

    String row;
    while ((row = reader.readLine()) != null) {

        Select_data.ind = 0;
        String[] row_data = row.split(",");

        if (Select_data.check(row_data)) {

            for (int i = 0; i < n; i++) {
                int colIndex = Select_data.find_col(updateCols[i]);
                row_data[colIndex] = newValues[i];
            }
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

    System.out.println("Multiple columns updated successfully.");
}

void main() {
}
