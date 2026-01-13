import java.io.File;
import java.util.ArrayList;

public class Show {
//    private static final File BASE_FOLDER = new File("/home/akash-pt8147/IdeaProjects/Demo");

//    public static ArrayList<String> databaseNames = new ArrayList<>();

    public static void show_table() {
//        databaseNames.clear();
         File[] files = Main.BASE_FOLDER.listFiles();

//        File[] files = BASE_FOLDER.listFiles();
        if(files==null) {
            System.out.println("No DBs to show");
        }
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    System.out.println(f.getName());
                }
            }
        }
    }

}
