package calculator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static calculator.constant.MessageConst.*;

public class InputParser {
    private static final String CUSTOM_PREFIX = "//";
    private static final String DEFAULT_DELIMITERS = "[,:]";
    private static final String CUSTOM_LINE_SEPARATOR = "\\n";
    private static final int CUSTOM_LINE_SEPARATOR_LENGTH = 2;

    /**
     * 문자열 계산식을 파싱하여 숫자 리스트를 반환합니다.
     * 이 과정에서 모든 유효성 검사가 수행됩니다.
     *
     * @param input 사용자가 입력한 원본 문자열
     * @return 계산 가능한 숫자들의 리스트(빈 입력은 빈 리스트)
     * @throws IllegalArgumentException 잘못된 형식, 음수 등 유효하지 않은 값이 포함된 경우
     */
    public List<Integer> parse(String input) {
        if (input == null || input.isBlank()) {
            return new ArrayList<>();
        }
        input = input.trim();

        if (input.startsWith(CUSTOM_PREFIX)) {
            String[] tokens = tokenizeWithCustomDelimiter(input);
            return convertToNumbers(tokens);
        }

        String[] tokens = tokenizeWithDefaultDelimiter(input);
        return convertToNumbers(tokens);
    }

    private ExpressionParts parseCustomDelimiter(String input) {
        final int separatorIndex = input.indexOf(CUSTOM_LINE_SEPARATOR);
        if (separatorIndex == -1) {
            throw new IllegalArgumentException(INPUT_CUSTOM_END_EXCEPTION_MSG);
        }

        String delimiter = input.substring(CUSTOM_PREFIX.length(), separatorIndex);
        String numbersPart = input.substring(separatorIndex + CUSTOM_LINE_SEPARATOR_LENGTH);

        validateDelimiter(delimiter);
        return new ExpressionParts(numbersPart, delimiter);
    }

    private void validateDelimiter(String delimiter) {
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException(INPUT_CUSTOM_EXCEPTION_MSG);
        }
        if (delimiter.length() > 1) {
            throw new IllegalArgumentException(INPUT_CUSTOM_LENGTH_EXCEPTION_MSG);
        }
        if (delimiter.matches(".*\\d.*")) {
            throw new IllegalArgumentException(INPUT_CUSTOM_UNVALIDATE_EXCEPTION);
        }
    }

    private String[] tokenizeWithCustomDelimiter(String input) {
        ExpressionParts parts = parseCustomDelimiter(input);
        String delimiter = parts.delimiter;
        String numbersPart = parts.numbersPart;

        if (numbersPart.isEmpty()) {
            return new String[0];
        }

        String regex = Pattern.quote(delimiter);
        return numbersPart.split(regex);
    }

    private String[] tokenizeWithDefaultDelimiter(String input) {
        return input.split(DEFAULT_DELIMITERS);
    }

    private List<Integer> convertToNumbers(String[] tokens) {
        List<Integer> nums = new ArrayList<>();
        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            final int num = parseIntStrict(token);
            if (num < 0) {
                throw new IllegalArgumentException(INPUT_MINUS_EXCEPTION_MSG);
            }
            nums.add(num);
        }

        return nums;
    }

    private int parseIntStrict(String token) {
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INPUT_FORM_EXCEPTION_MSG);
        }
    }

    private record ExpressionParts(String numbersPart, String delimiter) {
    }
}