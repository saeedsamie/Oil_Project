import java.util.ArrayList;
import java.util.Collections;

public class Sorter {
    public static ArrayList<Result> sort(ArrayList<Result> errors) {
        final ArrayList<Double> storedErrorsArray = new ArrayList<>(), tmpArray = new ArrayList<>();
        for (Result error : errors) {
            storedErrorsArray.add(error.error);
            tmpArray.add(error.error);
        }
        Collections.sort(tmpArray);
        ArrayList<Result> result = new ArrayList<>();

        for (Double aTmpArray : tmpArray) {
            int k = storedErrorsArray.indexOf(aTmpArray);
            Result r = errors.get(k);
            result.add(new Result(r.error, r.startDataPoint, r.patternLength));
        }
        return result;
    }
}
