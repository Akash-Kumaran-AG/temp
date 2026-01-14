import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Select_data {

    static String[] col = new String[10];
    static String[] val = new String[10];
    static String[] ops = new String[10];
    static int size = 0;

    static int ind = 0;
    public static void Select_all() throws IOException {
//        Create_table obj = new Create_table();
//        Scanner in = new Scanner(System.in);
//        System.out.println("Select by which column");
//        String str = in.nextLine();
//        BufferedReader schemaReader = new BufferedReader(new FileReader(Main.schemaFile));
        BufferedReader dataReader = new BufferedReader(new FileReader(Main.dataFile));
        String row = "";
        while ((row = dataReader.readLine()) != null) {
            System.out.println(row);

        }
    }
    public static void Select_col(String col_names[]) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Main.dataFile));
        String row = "";
        while ((row = reader.readLine()) != null) {
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < col_names.length; i++) {
                int ind = find_col(col_names[i]);
                String arr[] = row.split(",");
                temp.add(arr[ind]);
            }
            System.out.println(String.join(",",temp));
        }
    }

    public static void Select_by_cond() throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(Main.dataFile));
        takeInput();
        String row;
        while ((row = dataReader.readLine()) != null) {
            ind = 0;
            String[] row_data = row.split(",");

            if (check(row_data)) {
                System.out.println(row);
            }
        }
    }
    public static void Select_by_cond(String col_names[]) throws IOException {
        BufferedReader reader=new BufferedReader(new FileReader(Main.dataFile));
        takeInput();
        String row;
        while ((row = reader.readLine()) != null) {
            ind = 0;
            String[] row_data = row.split(",");
            if (check(row_data)) {
                ArrayList<String> temp = new ArrayList<>();
                for (int i = 0; i < col_names.length; i++) {
                    int ind = find_col(col_names[i]);
                    String arr[] = row.split(",");
                    temp.add(arr[ind]);
                }
                System.out.println(String.join(",",temp));
            }
        }

    }

    static void takeInput() {
        Scanner in = new Scanner(System.in);

        System.out.println("Simple condition / Nested (Y/N)");
        String yorn = in.nextLine();

        if (yorn.equalsIgnoreCase("Y")) {
            System.out.print("Column name: ");
            col[size] = in.nextLine();
            System.out.print("Data: ");
            val[size] = in.nextLine();
            ops[size++] = "No";
            return;
        }

        System.out.print("Operator (And / Or): ");
        ops[size++] = in.nextLine();

        System.out.println("Enter the left condition: ");
        takeInput();

        System.out.println("Enter the right condition: ");
        takeInput();
    }


    public static boolean check(String[] row_data) throws IOException {
        String op = ops[ind++];

        if (op.equals("No")) {
            int colIndex = find_col(col[ind - 1]);
            String to_check=val[ind-1];
            return row_data[colIndex].equals(to_check);
        }

        boolean left = check(row_data);
        boolean right = check(row_data);

        if (op.equalsIgnoreCase("And")) return left && right;
        else return left || right;
    }



    static int find_col(String col_name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Main.schemaFile));
        String line = reader.readLine();
        String[] columns = line.split(",");

        for (int i = 0; i < columns.length; i++) {
            if (columns[i].split("-")[0].equals(col_name)) {
                return i;
            }
        }
        return -1;
    }
}

