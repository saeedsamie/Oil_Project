public class Result {
    double error;
    DataPoint startDataPoint;
    int patternLength;

    Result(double error, DataPoint startDataPoint, int patternLength) {
        this.error = error;
        this.startDataPoint = startDataPoint;
        this.patternLength = patternLength;
    }
}
