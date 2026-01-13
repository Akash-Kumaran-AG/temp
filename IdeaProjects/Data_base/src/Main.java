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


        static void main () throws IOException {
        Scanner in = new Scanner(System.in);

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
                            "7. EXIT");
            System.out.println("Enter your choice: ");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    Create_table create = new Create_table();
                    System.out.println("Enter the table name: ");
                    String tname = in.nextLine();
                    create.create_table(tname);
                    break;
                case 2:
                    Insert_table insert = new Insert_table();
//                System.out.println("Which database should you update");
//                Show show=new Show();
//                show.showdb();
                    insert.insert();
                    break;
                case 3:
                    System.out.println("Do you want to apply where clause: (Y/N) ");

                    String yorn = in.nextLine();

                    boolean is_another = true;
                    if (yorn.equals("Y")) {
                        System.out.print("Select the column: ");
                        String colname = in.nextLine();
                        while (is_another) {
                            System.out.print("Provide data to select: ");
                            String seldata = in.nextLine();
                            Select_data.Select(colname, seldata);

                        }
                    } else {
                        Select_data.Select_all();
                    }
                    is_another = false;
//                        System.out.println("Is there another condition to select");
//                        String another_condition=in.nextLine();

                    break;
                case 4:
                    Show.show_table();

                    break;
                case 5:
                    Update_table update = new Update_table();
                    System.out.println("Update table name(T) / column name(C) / row data(R)?");
                    String input = in.nextLine();
                    if (input.equals("T")) {
                        System.out.println("Enter new table name: ");
                        String new_table_name=in.nextLine();
                        update.update_table_name(new_table_name);
                    } else if (input.equals("C")) {
                        System.out.print("Enter the old column name to update: ");
                        String old_col_name=in.nextLine();
                        System.out.print("Enter the new column name to update: ");
                        String new_col_name=in.nextLine();

                        update.update_col_name(old_col_name,new_col_name);

                    }else if(input.equals("R")){
                        System.out.print("Enter the column name to update: ");
                        String col_name=in.nextLine();
                        System.out.print("Enter the old row data to update: ");
                        String old_row_data=in.nextLine();
                        System.out.print("Enter the new row data to update: ");
                        String new_row_data=in.nextLine();

                        update.update_row_data(col_name,old_row_data,new_row_data);
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
                    is_next = false;
                    System.out.println("Exits...");
                    break;
                default:
            }
        }
    }
    }

