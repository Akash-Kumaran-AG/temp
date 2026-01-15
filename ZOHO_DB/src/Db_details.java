import java.io.File;

public class Db_details {

    public final File baseFolder;
    private File home;
    private File schemaFile;
    private File dataFile;

    public Db_details(File baseFolder) {
        this.baseFolder = baseFolder;
        this.home = baseFolder;
    }

    public void selectTable(String tableName) {
        this.home = new File(baseFolder, tableName);
        this.schemaFile = new File(home, "schema.meta");
        this.dataFile = new File(home, "data.dat");
    }

    public boolean isTableSelected() {
        return !home.equals(baseFolder);
    }

    public File getHome() {
        return home;
    }

    public File getSchemaFile() {
        return schemaFile;
    }

    public File getDataFile() {
        return dataFile;
    }
}