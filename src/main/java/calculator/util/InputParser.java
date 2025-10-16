package calculator.util;
import java.util.ArrayList;
import java.util.List;
import static calculator.constant.MessageConst.*;

public class InputParser {
    private static final String DEFAULT_DELIMITERS = "[,:]";

    public List<Integer> parse(String input) {
        if (input == null || input.isBlank()) {
            return new ArrayList<>();
        }

        input = input.trim();
        String[] tokens = tokenizeWithDefaultDelimiter(input);
        return convertToNumbers(tokens);
    }

    private String[] tokenizeWithDefaultDelimiter(String input) {
        String[] tokens = input.split(DEFAULT_DELIMITERS);
        return tokens;
    }

    private List<Integer> convertToNumbers(String[] tokens) {
        List<Integer> nums = new ArrayList<>();
        for (String token : tokens) {
            final int num = parseIntStrict(token);
            nums.add(num);
        }
        return nums;
    }

    private int parseIntStrict (String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INPUT_FORM_EXCEPTION_MSG);
        }
    }
}