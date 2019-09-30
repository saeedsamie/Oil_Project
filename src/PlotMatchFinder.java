import java.util.ArrayList;

public class PlotMatchFinder {
    private ArrayList<DataPoint> dataPoints = new ArrayList<>();
    private ArrayList<Result> matchResults = new ArrayList<>();
    private ArrayList<Result> sortedMatchResults = new ArrayList<>();

    void start() {

        //read from file 98.csv - 199.csv
        for (int i = 98; i < 200; i++)
            try {
                dataPoints.addAll(new FileReader(String.valueOf(i)).read());
            } catch (Exception ignored) {
            }

        //get match errors from every single matching
        for (int i = -1; i < 2; i++)
            matchResults.addAll(new PatternMatcher(dataPoints, new Pattern().resize(Pattern.DEFULT_length + i)).run());

        //sort results to find out k minimum errors and print them
        sortedMatchResults = Sorter.sort(matchResults);
        for (int i = 0; sortedMatchResults.get(i).error < 0.03; i++) {
            System.out.println(i + 1 + " minimum error = " + sortedMatchResults.get(i).error
                    + " in day " + sortedMatchResults.get(i).startDataPoint.day
                    + " in well " + sortedMatchResults.get(i).startDataPoint.well_name
                    + " with pattern length " + sortedMatchResults.get(i).patternLength);
        }
        ArrayList<Dot> dots = new ArrayList<>();
        for (int i = 0; i < Pattern.DEFULT_listValue.size(); i++) {
            dots.add(new Dot(i, Pattern.DEFULT_listValue.get(i)));
        }
        new PointGrapher(dots, "Pattern", "This is the Pattern with size " + Pattern.DEFULT_length);

        for (int i = 1; i < 4; i++) {
            String well_name = sortedMatchResults.get(i).startDataPoint.well_name;
            int day = sortedMatchResults.get(i).startDataPoint.day;
            int pattern_length = sortedMatchResults.get(i).patternLength;

            for (int j = 0; j < dataPoints.size(); j++) {
                DataPoint point = dataPoints.get(j);
                if (point.well_name.equals(well_name) &&
                        point.day == day) {
                    ArrayList<Dot> list = new ArrayList<>();
                    for (int k = 0; k < 5 * pattern_length; k++)
                        try {
                            list.add(new Dot(k, dataPoints.get(j + k).value));
                        } catch (Exception ignored) {
                        }

                    new PointGrapher(list, "Result " + i,
                            "   Well Name = " + well_name
                                    + "     Day = " + day
                                    + "     Pattern length = " + pattern_length
                                    + "     error = " + sortedMatchResults.get(i).error);
                }
            }
        }
    }
}
