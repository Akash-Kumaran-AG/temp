import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class File_handling {

    public static void main(String[] args) throws IOException {
        Scanner in=new Scanner(System.in);
        while(true) {
            String str = in.nextLine();
            File folder = new File(str);
            if (!folder.exists()) {
                folder.mkdir();
                System.out.println("Created successfully");
            } else {
                System.out.println(folder.getAbsolutePath());
                System.out.println("Failed creating. This file may already exist");
            }
//            System.out.println("Enter the input file name");
//            String filename = in.nextLine();
//            File file = new File(folder, filename);
//            System.out.println(file.getAbsolutePath());
//            BufferedWriter writer=new BufferedWriter(new FileWriter(file,true));
//            writer.write("My first line");
//            writer.close();
        }
    }
}
