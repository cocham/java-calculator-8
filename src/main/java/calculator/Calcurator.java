package calculator;
import calculator.util.ComputerUtil;
import calculator.util.InputParser;
import calculator.view.MessageView;

import java.util.List;

public class Calcurator {
    private final MessageView messageView = new MessageView();

    private final InputParser inputParser = new InputParser();

    private final ComputerUtil computerUtil = new ComputerUtil();

    public void run() {
            String input = messageView.requestInput();
            List<Integer> nums = inputParser.parse(input);
            long sum = computerUtil.getInputInt(nums);
            messageView.printResult(sum);
    }
}
