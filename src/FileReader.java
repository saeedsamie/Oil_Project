import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    private String fileName;

    FileReader(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<DataPoint> read() {
//        System.out.println("-------- file: " + fileName);
        ArrayList<DataPoint> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("Data\\"+fileName+".csv"))) {
            while (scanner.hasNextLine()) {
                result.add(new DataPoint(getRecordFromLine(scanner.nextLine()), result.size(), fileName));
            }
        } catch (FileNotFoundException ignored) {
        }
        return result;
    }

    private static Double getRecordFromLine(String line) {
        Double values = null;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values = Double.valueOf(rowScanner.next());
            }
        }
        return values;
    }
}
