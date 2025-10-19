package calculator.util;
import java.util.List;

public class ComputerUtil {
    public static long getInputInt(List<Integer> nums) {
        long sum = 0;
        for (int n : nums) {
            sum += n;
        }

        return sum;
    }
}
