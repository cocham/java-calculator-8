package calculator.util;
import java.util.List;

public class ComputerUtil {
    public static int getInputInt(List<Integer> nums) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return sum;
    }
}
