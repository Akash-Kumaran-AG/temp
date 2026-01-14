import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {

    public static final File BASE_FOLDER = new File("/home/akash-pt8147/IdeaProjects/db");
    public static File home;
    public static File schemaFile;
    public static File dataFile;

    Map<String, File> allFiles = new HashMap<>();

    static boolean is_same() {
        if (home.compareTo(BASE_FOLDER) == 0) {
            System.out.println("Please select a table before you perform update");
            return true;
        }
        return false;
    }

    static void main() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Tables:");
        Show.show_table();
        System.out.print("Enter the table name to visit:\n" +
                "If No table matches press ENTER: ");
        String t_name = in.nextLine();
        if (t_name != null) {
            home = new File(BASE_FOLDER + "/" + t_name);
            schemaFile = new File(home, "schema.meta");
            dataFile = new File(home, "data.dat");
            System.out.println("\nYou are now in the table: " + t_name + "\n");
        }
        boolean is_next = true;
        while (is_next) {
            System.out.println(
                    "1. CREATE\n" +
                            "2. INSERT\n" +
                            "3. SELECT\n" +
                            "4. SHOW\n" +
                            "5. UPDATE\n" +
                            "6. CHOOSE TABLE\n" +
                            "7. DELETE\n" +
                            "8. EXIT");
            System.out.println("Enter your choice: ");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    Create_table create = new Create_table();
                    System.out.println("Enter the table name: ");
                    String tname = in.nextLine();
                    create.create_table(tname);
//                    if(Main.BASE_FOLDER.listFiles().isDirectory())
                    home = new File(BASE_FOLDER + "/" + tname);
                    schemaFile = new File(home, "schema.meta");
                    dataFile = new File(home, "data.dat");
                    break;
                case 2:
                    if (!is_same()) {
                        Insert_table insert = new Insert_table();
//                System.out.println("Which database should you update");
//                Show show=new Show();
//                show.showdb();
                        insert.insert();
                    }
                    break;
                case 3:
                    if (!is_same()) {
                        System.out.println("Do you want to apply where clause: (Y/N) ");

                        String yorn = in.nextLine();

                        if (yorn.equals("Y")) {
                            System.out.println("Enter the column seperated by commas.. If all columns enter \"*\"");
                            String columns=in.nextLine();
                            if(columns.equals("*")) {
                                Select_data.Select_by_cond();
                            }else{
                                System.out.println(columns);
                                String col_arr[] = columns.split(",");
                                Select_data.Select_by_cond(col_arr);
                            }

                        } else {
                            System.out.println("Enter the column seperated by commas.. If all columns enter \"*\"");
                            String columns=in.nextLine();
                            if(!columns.equals("*")) {
                                System.out.println(columns);
                                String col_arr[] = columns.split(",");
                                Select_data.Select_col(col_arr);
                            }else
                            Select_data.Select_all();
                        }
                    }
//                        System.out.println("Is there another condition to select");
//                        String another_condition=in.nextLine();

                    break;
                case 4:
                    Show.show_table();

                    break;
                case 5:
                    if (!is_same()) {
                        Update_table update = new Update_table();
                        System.out.println("Update table name(T) / column name(C) / row data(R)?");
                        String input = in.nextLine();
                        if (input.equals("T")) {
                            System.out.println("Enter new table name: ");
                            String new_table_name = in.nextLine();
                            update.update_table_name(new_table_name);
                            System.out.println("Table name updated to: " + new_table_name);
                        } else if (input.equals("C")) {
                            System.out.print("Enter the old column name to update: ");
                            String old_col_name = in.nextLine();
                            System.out.print("Enter the new column name to update: ");
                            String new_col_name = in.nextLine();

                            update.update_col_name(old_col_name, new_col_name);
                            System.out.println("Column name updated from " + old_col_name + " to " + new_col_name);

                        } else if (input.equals("R")) {
                            System.out.print("Update with where condition or not(Y/N) :");
                            String yorn = in.nextLine();
                            if (yorn.equals("N")) {
                                System.out.print("Enter the column name to update: ");
                                String col_name = in.nextLine();
                                System.out.print("Enter the old row data to update: ");
                                String old_row_data = in.nextLine();
                                System.out.print("Enter the new row data to update: ");
                                String new_row_data = in.nextLine();

                                update.update_row_data(col_name, old_row_data, new_row_data);
                                System.out.println("Row data updated from " + old_row_data + " to " + new_row_data);
                            }else if(yorn.equals("Y")){
                                update.update_row_cond();
                        }

                        }
                    }
                    break;
                case 6:
                    Show.show_table();
                    System.out.print("Enter the table name: ");
                    String table_name = in.nextLine();
                    home = new File(BASE_FOLDER + "/" + table_name);
                    schemaFile = new File(home, "schema.meta");
                    dataFile = new File(home, "data.dat");
                    break;
                case 7:
                    String table=in.nextLine();
                    home = new File(BASE_FOLDER + "/" + table);
//                    Delete_table.delete_table(table);
                    break;
                case 8:
                    is_next = false;
                    System.out.println("Exits...");
                    break;
                default:
            }
        }
    }
}


