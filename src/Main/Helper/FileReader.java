package Main.Helper;

/**
 * Takes in a file path and returns the contents of the file as a string.
 */
public class FileReader {
    public FileReader() {

    }

    public String readFile(String filePath) {
        String fileContents = "";
        try {
            java.io.File file = new java.io.File(filePath);
            java.util.Scanner input = new java.util.Scanner(file);
            while (input.hasNext()) {
                fileContents += input.nextLine();
            }
            input.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return fileContents;
    }
}
