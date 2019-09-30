import java.util.ArrayList;
import java.util.Arrays;

public class Pattern {

    static public ArrayList<Double> DEFULT_listValue =
            new ArrayList<>(
                    Arrays.asList(0.004636235,
                            0.076461936,
                            0.076792624,
                            0.210349033,
                            0.215884319,
                            0.201544689,
                            0.194704126,
                            0.190478977,
                            0.18496032,
                            0.182272508,
                            0.179929699,
                            0.178716094,
                            0.179355014,
                            0.173864049,
                            0.181161225,
                            0.177121986,
                            0.174688929,
                            0.169525609,
                            0.167294121,
                            0.175615232,
                            0.175401263,
                            0.174687324,
                            0.175329953,
                            0.172372382,
                            0.170977156,
                            0.004636235));
    static public int DEFULT_length = DEFULT_listValue.size();

    public ArrayList<Double> listValue;
    public int length;


    private Pattern(ArrayList<Double> listValue) {
        this.listValue = listValue;
        this.length = listValue.size();
    }

    Pattern() {
        this.listValue = DEFULT_listValue;
        this.length = DEFULT_length;
    }

    Pattern resize(int goalSize) {
//        if (goalSize == input.size())
//            return input;
        ArrayList<Double> input = new ArrayList<>(listValue);
        if (goalSize < input.size()) {
            ArrayList<Double> result = new ArrayList<>();
            result.add(input.get(0));
            for (int i = 1; i < goalSize - 1; i++) {
                double e1 = (input.get(i + 1) - input.get(i));
                double e2 = goalSize * 1.0 / input.size();
                double e3 = (i - i * (goalSize * 1.0 / input.size()));
                result.add((e1 / e2 * e3) + input.get(i));
            }
            result.add(input.get(input.size() - 1));
            return new Pattern(result);
        } else if (goalSize > input.size()) {
            ArrayList<Double> result = new ArrayList<>();
            result.add(input.get(0));
            for (int i = 0; i < goalSize - 2; i++) {
                double e1 = (input.get(i + 1) - input.get(i));
                double e2 = goalSize * 1.0 / input.size();
                double e3 = (i - (i - 1) * (goalSize * 1.0 / input.size()));
                result.add((e1 / e2 * e3) + input.get(i));
            }
            result.add(input.get(input.size() - 1));

            return new Pattern(result);
        }
        return new Pattern();
    }
}
