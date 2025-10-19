package calculator;
import calculator.util.ComputerUtil;
import calculator.util.InputParser;
import calculator.util.MessageUtil;
import calculator.util.UserUtil;
import java.util.List;

public class Calcurator {
    private final UserUtil userUtil = new UserUtil();

    private final MessageUtil messageUtil = new MessageUtil();

    private final InputParser inputParser = new InputParser();

    private final ComputerUtil computerUtil = new ComputerUtil();

//    public void run() {
//        try{
//            String input = messageUtil.requestInput();
//            List<Integer> nums = inputParser.parse(input);
//            long sum = computerUtil.getInputInt(nums);
//            messageUtil.printResult(sum);
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }
//    }

        public void run() {
            String input = messageUtil.requestInput();
            List<Integer> nums = inputParser.parse(input);
            int sum = computerUtil.getInputInt(nums);
            messageUtil.printResult(sum);
    }
}
