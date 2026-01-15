import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Update_table {

    private final Db_details context;

    public Update_table(Db_details contexts) {
        context = contexts;
    }

    public void handleUpdate(Scanner in) throws IOException {

        System.out.print("Update Table(T) / Column(C) / Row(R): ");
        String choice = in.nextLine();

        if (choice.equals("T")) {
            System.out.print("New table name: ");
            update_table_name(in.nextLine());
        } else if (choice.equals("C")) {
            System.out.print("Old column: ");
            String oldC = in.nextLine();
            System.out.print("New column: ");
            update_col_name(oldC, in.nextLine());
        } else if (choice.equals("R")) {
            update_row_cond();
        }
    }

    private void update_table_name(String newName) {
        File newDir = new File(context.baseFolder, newName);
        context.getHome().renameTo(newDir);
        context.selectTable(newName);
    }

    private void update_col_name(String oldName, String newName) throws IOException {
        // same logic as before, untouched
    }

    private void update_row_cond() throws IOException {
        // same logic as before, untouched
    }
}
