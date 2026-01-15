
import java.io.*;
        import java.util.Scanner;

public class Insert_table {

    private final Db_details context;

    public Insert_table(Db_details context) {
        this.context = context;
    }

    public void insert() throws IOException {

        BufferedReader reader =
                new BufferedReader(new FileReader(context.getSchemaFile()));
        BufferedWriter writer =
                new BufferedWriter(new FileWriter(context.getDataFile(), true));

        String[] cols = reader.readLine().split(",");
        Scanner in = new Scanner(System.in);

        boolean more = true;

        while (more) {
            for (int i = 0; i < cols.length; i++) {
                System.out.print(cols[i] + ": ");
                writer.write(in.nextLine());
                if (i < cols.length - 1) writer.write(",");
            }
            writer.newLine();

            System.out.print("Is there another row? (Y/N): ");
            String is_more = in.nextLine();
            if(!is_more.equals("Y"))more = false;
        }
        writer.close();
    }
}
