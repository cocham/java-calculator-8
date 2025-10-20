package calculator.view;
import camp.nextstep.edu.missionutils.Console;
import static calculator.constant.MessageConst.*;

public class MessageView {
    public String requestInput() {
        System.out.println(START_MSG);
        return Console.readLine();
    }
    public void printResult(long result) {
        System.out.print(OUTPUT_MSG + result);
    }
}
