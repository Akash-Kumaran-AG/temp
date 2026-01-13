import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class temp{
    private static final File BASE_FOLDER = new File("/home/akash-pt8147/IdeaProjects/Demo");;
    ArrayList<ArrayList<String>>rows=new ArrayList<>();
//    File BASE_FOLDER =

    //    public static void print_path(){
//        System.out.println(BASE_FOLDER.getAbsolutePath());
//    }
    public void create_table(String table_name) throws IOException {
        Scanner in=new Scanner(System.in);
        File file_name=new File(BASE_FOLDER,table_name);
        if(!file_name.exists()) {
            file_name.mkdir();
            System.out.println("Table created in the name of " + table_name);

            File schema = new File(file_name, "schema.meta");
            schema.mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(schema));
            writer.write(table_name);
            writer.newLine();
            System.out.println("Enten the number of columns: ");
            int col_nums=in.nextInt();
            in.nextLine();

            ArrayList<String>col_name=new ArrayList<>();
            ArrayList<String>col_type=new ArrayList<>();
            ArrayList<Boolean>is_Pk=new ArrayList<>();

            for(int i=0;i<col_nums;i++){
                System.out.println("Enter the column name "+(i+1));
                col_name.add(in.nextLine());

                System.out.println("Enter the column type ");
                col_type.add(in.nextLine());

                System.out.println("Is it a Primary Key(Y/N)");
                String pk=in.nextLine();

                if(pk.equals("Y")){
                    writer.write(col_name.get(i)+" - "+col_type.get(i)+" - "+"PK");
                    is_Pk.add(true);
                }else{
                    writer.write(col_name.get(i)+" - "+col_type.get(i));
                    is_Pk.add(false);
                }writer.write("|");
            }
            writer.newLine();
            writer.close();
            File infile=new File(schema,table_name);
            BufferedWriter datawriter=new BufferedWriter(new FileWriter(infile,true));
            ArrayList<String>temp=new ArrayList<>();
//            rows.add(new ArrayList<String>());
            System.out.println("Enter your row ");
            boolean is_next=true;
            while(is_next) {
                is_next=false;
                for (int i = 0; i < col_nums; i++) {

//                if(i!=col_nums-1){
                    String st = in.nextLine();
                    temp.add(String.valueOf(st));
                    if (i != col_nums - 1) {
                        datawriter.write(st + " | ");
                    } else {
                        datawriter.write(st);
                        datawriter.newLine();
                    }
                }
                rows.add(temp);
                System.out.println("Is there another row?(Y?N)");
                String another=in.nextLine();
                if(another.equals("Y")) is_next=true;

            }
            datawriter.close();

        }else{
            System.out.println("Table creation failed. "+table_name+" may already exists.");
        }


    }
}
import java.io.*;
import java.util.*;

public class Main {

    static String schemaFile = "schema.txt";
    static String dataFile = "data.txt";

    // ---------------- CONDITION STRUCTURE ----------------
    static class Condition {
        boolean isLeaf;          // true = simple condition
        String column;           // column name
        String value;            // value to compare
        Condition left, right;   // for nested conditions
        String operator;         // AND / OR

        // Simple condition
        Condition(String col, String val) {
            isLeaf = true;
            column = col;
            value = val;
        }

        // Composite condition
        Condition(Condition l, Condition r, String op) {
            isLeaf = false;
            left = l;
            right = r;
            operator = op;
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        System.out.println("Build WHERE condition:");
        Condition root = readCondition(in);

        select(root);
    }

    // ---------------- RECURSIVE INPUT ----------------
    static Condition readCondition(Scanner in) {

        System.out.print("Is this a simple condition? (Y/N): ");
        if (in.nextLine().equalsIgnoreCase("Y")) {

            System.out.print("Column name: ");
            String col = in.nextLine();

            System.out.print("Value: ");
            String val = in.nextLine();

            return new Condition(col, val);
        }

        System.out.println("Left condition:");
        Condition left = readCondition(in);

        System.out.print("Operator (AND/OR): ");
        String op = in.nextLine();

        System.out.println("Right condition:");
        Condition right = readCondition(in);

        return new Condition(left, right, op);
    }

    // ---------------- SELECT ----------------
    static void select(Condition condition) throws IOException {

        BufferedReader schemaReader = new BufferedReader(new FileReader(schemaFile));
        BufferedReader dataReader = new BufferedReader(new FileReader(dataFile));

        String[] columns = schemaReader.readLine().split(",");

        String row;
        boolean found = false;

        while ((row = dataReader.readLine()) != null) {
            String[] data = row.split(",");

            if (evaluate(condition, data, columns)) {
                System.out.println(row);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching records found");
        }
    }

    // ---------------- EVALUATE CONDITION ----------------
    static boolean evaluate(Condition c, String[] row, String[] columns) {

        if (c.isLeaf) {
            int idx = getColumnIndex(columns, c.column);
            if (idx == -1) {
                System.out.println("Column not found: " + c.column);
                return false;
            }
            return row[idx].equals(c.value);
        }

        if (c.operator.equalsIgnoreCase("AND")) {
            return evaluate(c.left, row, columns)
                    && evaluate(c.right, row, columns);
        } else {
            return evaluate(c.left, row, columns)
                    || evaluate(c.right, row, columns);
        }
    }

    // ---------------- COLUMN INDEX SEARCH (OPTION 1) ----------------
    static int getColumnIndex(String[] columns, String colName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].split("-")[0].equals(colName)) {
                return i;
            }
        }
        return -1;
    }
}


import java.io.*;
import java.util.*;

public class Main {

    static String schemaFile = "schema.txt";
    static String dataFile = "data.txt";

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        BufferedReader schemaReader = new BufferedReader(new FileReader(schemaFile));
        String[] columns = schemaReader.readLine().split(",");

        BufferedReader dataReader = new BufferedReader(new FileReader(dataFile));

        System.out.println("Build WHERE condition:");
        boolean found = false;

        String row;
        while ((row = dataReader.readLine()) != null) {
            String[] data = row.split(",");

            if (evaluateCondition(in, data, columns)) {
                System.out.println(row);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching records found");
        }
    }

    // ---------------- RECURSIVE EVALUATION ----------------
    static boolean evaluateCondition(Scanner in, String[] row, String[] columns) {

        System.out.print("Is this a simple condition? (Y/N): ");
        if (in.nextLine().equalsIgnoreCase("Y")) {

            System.out.print("Column name: ");
            String col = in.nextLine();

            System.out.print("Value: ");
            String val = in.nextLine();

            int idx = getColumnIndex(columns, col);
            if (idx == -1) {
                System.out.println("Column not found: " + col);
                return false;
            }
            return row[idx].equals(val);
        }

        System.out.println("Left condition:");
        boolean left = evaluateCondition(in, row, columns);

        System.out.print("Operator (AND/OR): ");
        String op = in.nextLine();

        System.out.println("Right condition:");
        boolean right = evaluateCondition(in, row, columns);

        if (op.equalsIgnoreCase("AND")) {
            return left && right;
        } else {
            return left || right;
        }
    }

    // ---------------- COLUMN INDEX SEARCH ----------------
    static int getColumnIndex(String[] columns, String colName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].split("-")[0].equals(colName)) {
                return i;
            }
        }
        return -1;
    }
}



import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Main {

    static String schemaFile = "schema.txt";
    static String dataFile = "data.txt";

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        // Read schema
        BufferedReader schemaReader = new BufferedReader(new FileReader(schemaFile));
        String[] columns = schemaReader.readLine().split(",");

        // 🔴 Build condition ONCE
        System.out.println("Build WHERE condition:");
        Predicate<String[]> condition = readCondition(in, columns);

        // Read data and PRINT matching rows
        BufferedReader dataReader = new BufferedReader(new FileReader(dataFile));

        String row;
        boolean found = false;

        while ((row = dataReader.readLine()) != null) {
            String[] data = row.split(",");

            if (condition.test(data)) {
                System.out.println(row);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching records found");
        }
    }

    // ---------------- RECURSIVE CONDITION INPUT ----------------
    static Predicate<String[]> readCondition(Scanner in, String[] columns) {

        System.out.print("Is this a simple condition? (Y/N): ");
        if (in.nextLine().equalsIgnoreCase("Y")) {

            System.out.print("Column name: ");
            String col = in.nextLine();

            System.out.print("Value: ");
            String val = in.nextLine();

            int idx = getColumnIndex(columns, col);

            return row -> idx != -1 && row[idx].equals(val);
        }

        System.out.println("Left condition:");
        Predicate<String[]> left = readCondition(in, columns);

        System.out.print("Operator (AND/OR): ");
        String op = in.nextLine();

        System.out.println("Right condition:");
        Predicate<String[]> right = readCondition(in, columns);

        if (op.equalsIgnoreCase("AND")) {
            return row -> left.test(row) && right.test(row);
        } else {
            return row -> left.test(row) || right.test(row);
        }
    }

    // ---------------- COLUMN SEARCH ----------------
    static int getColumnIndex(String[] columns, String colName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].split("-")[0].equals(colName)) {
                return i;
            }
        }
        return -1;
    }
}

