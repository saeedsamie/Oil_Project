import java.util.ArrayList;

public class PatternMatcher {
    private ArrayList<DataPoint> dataPointsList;
    private Pattern pattern;


    PatternMatcher(ArrayList<DataPoint> dataPointsList, Pattern pattern) {
        this.dataPointsList = dataPointsList;
        this.pattern = pattern;
    }

    ArrayList<Result> run() {
        ArrayList<Result> resultArray = new ArrayList<>();
        for (int i = 0; i < dataPointsList.size() - pattern.length; i++) {
            ArrayList<Double> errorI = new ArrayList<>();
            for (int x = 0; x < pattern.length; x++) {
                errorI.add((dataPointsList.get(i + x).value - pattern.listValue.get(x)));
            }
            resultArray.add(new Result(distribution(errorI), dataPointsList.get(i), pattern.length));
        }

        return resultArray;

    }

    static private Double distribution(ArrayList<Double> input) {
//        double mean = mean(input);
        double sum = 0;
        for (Double anInput : input) {
            sum += Math.pow(Math.abs(0 - anInput), 2);
        }
        return Math.sqrt(sum / input.size());

    }

    static private Double mean(ArrayList<Double> input) {
        double temp = 0;
        for (Double anInput : input) {
            temp += anInput;
        }
        return temp / input.size();
    }
}
