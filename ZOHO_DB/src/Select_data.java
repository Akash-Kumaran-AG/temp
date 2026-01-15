import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Select_data {

    private final Db_details context;

    private String[] col = new String[10];
    private String[] val = new String[10];
    private String[] ops = new String[10];
    private int size = 0;
    private int ind = 0;

    public Select_data(Db_details context) {
        this.context = context;
    }


    public void selectAll() throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader(context.getDataFile()));

        String row;
        while ((row = reader.readLine()) != null) {
            System.out.println(row);
        }
        reader.close();
    }

    public void selectColumns(String[] colNames) throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader(context.getDataFile()));

        String row;
        while ((row = reader.readLine()) != null) {
            ArrayList<String> temp = new ArrayList<>();
            String[] data = row.split(",");

            for (String name : colNames) {
                int index = findColumn(name);
                temp.add(data[index]);
            }
            System.out.println(String.join(",", temp));
        }
        reader.close();
    }

    public void selectByCondition() throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader(context.getDataFile()));

        takeInput();

        String row;
        while ((row = reader.readLine()) != null) {
            ind = 0;
            if (check(row.split(","))) {
                System.out.println(row);
            }
        }
        reader.close();
    }

    public void selectByCondition(String[] colNames) throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader(context.getDataFile()));

        takeInput();

        String row;
        while ((row = reader.readLine()) != null) {
            ind = 0;
            String[] rowData = row.split(",");

            if (check(rowData)) {
                ArrayList<String> temp = new ArrayList<>();
                for (String name : colNames) {
                    int index = findColumn(name);
                    temp.add(rowData[index]);
                }
                System.out.println(String.join(",", temp));
            }
        }
        reader.close();
    }

    private void takeInput() {
        Scanner in = new Scanner(System.in);

        System.out.print("Simple condition / Nested (Y/N): ");
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

        System.out.println("Enter the left condition:");
        takeInput();

        System.out.println("Enter the right condition:");
        takeInput();
    }

    private boolean check(String[] rowData) throws IOException {
        String op = ops[ind++];

        if (op.equals("No")) {
            int colIndex = findColumn(col[ind - 1]);
            return rowData[colIndex].equals(val[ind - 1]);
        }

        boolean left = check(rowData);
        boolean right = check(rowData);

        if (op.equalsIgnoreCase("And")) return left && right;
        else return left || right;
    }


    private int findColumn(String colName) throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader(context.getSchemaFile()));

        String[] columns = reader.readLine().split(",");
        reader.close();

        for (int i = 0; i < columns.length; i++) {
            if (columns[i].split("-")[0].equals(colName)) {
                return i;
            }
        }
        return -1;
    }
}
