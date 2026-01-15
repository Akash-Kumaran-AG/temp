import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final File BASE_FOLDER =
            new File("C:\\Users\\Akash Kumaran A G\\Downloads\\temp-main\\IdeaProjects\\db");

    public static void main(String[] args) throws IOException {

        Db_details context = new Db_details(BASE_FOLDER);
        Scanner in = new Scanner(System.in);

        System.out.println("Tables:");
        Show.show_table();

        System.out.print("Enter the table name to visit:\nIf No table matches press ENTER: ");
        String tName = in.nextLine();

        if (!tName.isEmpty()) {
            context.selectTable(tName);
            System.out.println("\nYou are now in the table: " + tName + "\n");
        }

        boolean running = true;

        while (running) {
            System.out.println("""
                    1. CREATE
                    2. INSERT
                    3. SELECT
                    4. SHOW
                    5. UPDATE
                    6. CHOOSE TABLE
                    7. DELETE
                    8. EXIT
                    """);

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {

                case 1: {
                    Create_table create = new Create_table(context);
                    System.out.print("Enter the table name: ");
                    create.create_table(in.nextLine());
                    break;
                }

                case 2: {
                    if (context.isTableSelected()) {
                        new Insert_table(context).insert();
                    } else {
                        System.out.println("Please select a table first.");
                    }
                    break;
                }

                case 3: {
                    if (!context.isTableSelected()) {
                        System.out.println("Please select a table before SELECT.");
                        break;
                    }

                    Select_data select = new Select_data(context);

                    System.out.print("Do you want to apply where clause? (Y/N): ");
                    String yorn = in.nextLine();

                    if (yorn.equalsIgnoreCase("Y")) {

                        System.out.print("Enter the column(s) separated by commas (* for all): ");
                        String columns = in.nextLine();

                        if (columns.equals("*")) {
                            select.selectByCondition();
                        } else {
                            String[] colArr = columns.split(",");
                            select.selectByCondition(colArr);
                        }

                    } else {

                        System.out.print("Enter the column(s) separated by commas (* for all): ");
                        String columns = in.nextLine();

                        if (columns.equals("*")) {
                            select.selectAll();
                        } else {
                            String[] colArr = columns.split(",");
                            select.selectColumns(colArr);
                        }
                    }

                    break;
                }


                case 4 :
                    Show.show_table();
                    break;

                case 5: {
                    if (context.isTableSelected()) {
                        new Update_table(context).handleUpdate(in);
                    }
                    break;
                }

                case 6: {
                    Show.show_table();
                    System.out.print("Enter the table name: ");
                    context.selectTable(in.nextLine());
                    break;
                }

                case 7: {
                    if (context.isTableSelected()) {
                        Delete_table.delete_row_cond(context);
                    }
                    break;
                }

                case 8: {
                    running = false;
                    System.out.println("Exits...");
                    break;
                }
                default:
            }
        }
    }
}
