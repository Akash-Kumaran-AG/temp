import java.io.*;
import java.util.*;

public class Create_table {

    private final Db_details context;

    public Create_table(Db_details context) {
        this.context = context;
    }

    public void create_table(String tableName) throws IOException {

        File tableDir = new File(context.baseFolder, tableName);
        if (tableDir.exists()) {
            System.out.println("Table already exists.");
            return;
        }

        tableDir.mkdir();
        context.selectTable(tableName);

        Scanner in = new Scanner(System.in);
        BufferedWriter schemaWriter =
                new BufferedWriter(new FileWriter(context.getSchemaFile()));

        System.out.print("Enter number of columns: ");
        int cols = in.nextInt();
        in.nextLine();

        for (int i = 0; i < cols; i++) {
            System.out.print("Column name: ");
            String name = in.nextLine();

            System.out.print("Column type: ");
            String type = in.nextLine();

            System.out.print("Primary key? (Y/N): ");
            String pk = in.nextLine();

            schemaWriter.write(name + "-" + type + (pk.equals("Y") ? "-PK" : ""));
            if (i < cols - 1) schemaWriter.write(",");
        }

        schemaWriter.newLine();
        schemaWriter.close();

        new File(context.getDataFile().getPath()).createNewFile();
        System.out.println("Table created successfully.");
    }
}
