package calculator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static calculator.constant.MessageConst.*;

public class InputParser {
    private static final String CUSTOM_PREFIX = "//";
    private static final String DEFAULT_DELIMITERS = "[,:]";

    /**
     * 문자열 계산식을 파싱하여 숫자 리스트를 반환합니다.
     * 이 과정에서 모든 유효성 검사가 수행됩니다.
     *
     * @param input 사용자가 입력한 원본 문자열
     * @return 계산 가능한 숫자들의 리스트(빈 입력은 빈 리스트)
     * @throws IllegalArgumentException 잘못된 형식/값
     */
    public List<Integer> parse(String input) {
        if (input == null || input.isBlank()) {
            return new ArrayList<>();
        }

        input = input.trim();
        String[] tokens;
        if (input.startsWith(CUSTOM_PREFIX)) {
            tokens = tokenizeWithCustomDelimiter(input);
        } else {
            tokens = tokenizeWithDefaultDelimiter(input);
        }

        return convertToNumbers(tokens);
    }

    // 커스텀 구분자 검증 후 구분자 및 문자열 추출
    private ExpressionParts extractCustomDelimiter(String input) {
        int endIdx = input.indexOf("\n");
        if (endIdx == -1 ) {
            throw new IllegalArgumentException(INPUT_CUSTOM_END_EXCEPTION_MSG);
        }
        if (endIdx == input.length() - 1) {
            throw new IllegalArgumentException(INPUT_CUSTOM_NAN_NUMS_EXCEPTION_MSG);
        }

        String delimiter = input.substring(2, endIdx);
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException(INPUT_CUSTOM_EXCEPTION_MSG);
        }
        if (delimiter.matches(".*\\d.*")) {
            throw new IllegalArgumentException(INPUT_CUSTOM_UNVALIDATE_EXCEPTION);
        }

        String numbersPart = input.substring(endIdx + 1);

        return new ExpressionParts(numbersPart, delimiter);
    }

    // 커스텀 구분자 기반 파싱
    private String[] tokenizeWithCustomDelimiter(String input) {
        ExpressionParts parts = extractCustomDelimiter(input);
        String delimiter = parts.delimiter;
        String numbersPart = parts.numbersPart;
        String regex = Pattern.quote(delimiter);
        String[] tokens = numbersPart.split(regex);

        return tokens;
    }

    // 기본 구분자(쉼표/콜론) 기반 파싱
    private String[] tokenizeWithDefaultDelimiter(String input) {
        String[] tokens = input.split(DEFAULT_DELIMITERS);

        return tokens;
    }

    // 숫자가 유효한지 검증 후 숫자 리스트 반환
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

    // 레코드는 불변값.
    private record ExpressionParts (String numbersPart, String delimiter) {
    }

}