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

    // Simple internal class for condition
    static class Condition {
        boolean isLeaf; // true if simple condition, false if composite
        String column, value; // for leaf
        Condition left, right; // for composite
        String operator; // AND / OR for composite

        // Leaf constructor
        Condition(String col, String val) {
            isLeaf = true;
            column = col;
            value = val;
        }

        // Composite constructor
        Condition(Condition l, Condition r, String op) {
            isLeaf = false;
            left = l;
            right = r;
            operator = op;
        }

        // Evaluate this condition against a data row
        boolean evaluate(String[] row, Map<String, Integer> colIndex) {
            if (isLeaf) {
                return row[colIndex.get(column)].equals(value);
            } else {
                if (operator.equalsIgnoreCase("AND")) {
                    return left.evaluate(row, colIndex) && right.evaluate(row, colIndex);
                } else {
                    return left.evaluate(row, colIndex) || right.evaluate(row, colIndex);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        System.out.println("Build WHERE condition:");
        Condition root = readCondition(in);

        select(root);
    }

    // Recursive input for nested conditions
    static Condition readCondition(Scanner in) {
        System.out.print("Is this a simple condition? (Y/N): ");
        if (in.nextLine().equalsIgnoreCase("Y")) {
            System.out.print("Column: ");
            String col = in.nextLine();
            System.out.print("Value: ");
            String val = in.nextLine();
            return new Condition(col, val);
        }

        System.out.println("Left side:");
        Condition left = readCondition(in);

        System.out.print("Operator (AND/OR): ");
        String op = in.nextLine();

        System.out.println("Right side:");
        Condition right = readCondition(in);

        return new Condition(left, right, op);
    }

    // Select rows matching the condition
    static void select(Condition cond) throws IOException {
        BufferedReader schemaReader = new BufferedReader(new FileReader(schemaFile));
        BufferedReader dataReader = new BufferedReader(new FileReader(dataFile));

        String[] cols = schemaReader.readLine().split(",");
        Map<String, Integer> colIndex = new HashMap<>();
        for (int i = 0; i < cols.length; i++) {
            colIndex.put(cols[i].split("-")[0], i);
        }

        String row;
        boolean found = false;
        while ((row = dataReader.readLine()) != null) {
            String[] data = row.split(",");
            if (cond.evaluate(data, colIndex)) {
                System.out.println(row);
                found = true;
            }
        }
        if (!found) System.out.println("No matching records found");
    }
}
