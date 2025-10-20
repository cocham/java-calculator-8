package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Nested
    @DisplayName("공백 문자 처리 테스트")
    class WhitespaceHandlingTest {

        @Test
        @DisplayName("커스텀 구분자 사용 시, 숫자 사이에 공백이 포함된 경우")
        void 커스텀_구분자_공백무시() {
            assertSimpleTest(() -> {
                run("//;\\n1 ; 2");
                assertThat(output()).contains("결과 : 3");
            });
        }

        @Test
        @DisplayName("기본 구분자 사용 시, 숫자 앞에 공백이 포함된 경우")
        void 기본_구분자_숫자앞_공백무시() {
            assertSimpleTest(() -> {
                run("1,, 2");
                assertThat(output()).contains("결과 : 3");
            });
        }

        @Test
        @DisplayName("기본 구분자 사용 시, 숫자 앞에 공백이 포함된 경우")
        void 기본_구분자_숫자뒤_공백무시() {
            assertSimpleTest(() -> {
                run("2  ,3");
                assertThat(output()).contains("결과 : 5");
            });
        }
    }

    @Nested
    @DisplayName("기본 구분자 처리 테스트")
    class DelimiterHandlingTest {

        @Test
        @DisplayName("구분자가 연속으로 오는 경우")
        void 구분자가_연속된_경우() {
            assertSimpleTest(() -> {
                run("1,,2");
                assertThat(output()).contains("결과 : 3");
            });
        }

        @Test
        @DisplayName("입력이 구분자로 시작하는 경우")
        void 구분자로_시작하는_경우() {
            assertSimpleTest(() -> {
                run(",1,2");
                assertThat(output()).contains("결과 : 3");
            });
        }

        @Test
        @DisplayName("입력이 구분자로 끝나는 경우")
        void 구분자로_끝나는_경우() {
            assertSimpleTest(() -> {
                run("1,2,");
                assertThat(output()).contains("결과 : 3");
            });
        }
    }

    @Nested
    @DisplayName("커스텀 구분자 처리 테스트")
    class CustomDelimiterHandlingTest {

        @Test
        @DisplayName("구분자가 연속으로 오는 경우")
        void 커스텀_구분자가_연속된_경우() {
            assertSimpleTest(() -> {
                run("//;\\n1;;2");
                assertThat(output()).contains("결과 : 3");
            });
        }

        @Test
        void 커스텀_구분자_지정안한경우() {
            assertSimpleTest(() ->
                assertThatThrownBy (() -> runException("//\\n1;2"))
                    .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @Test
        void 커스텀_구분자만_있는경우() {
            assertSimpleTest(() -> {
                run("//;\\n");
                assertThat(output()).contains("결과 : 0");
            });
        }

        @Test
        void 커스텀_구분자가_메타문자인경우() {
            assertSimpleTest(() -> {
                run("//|\\n1|2|");
                assertThat(output()).contains("결과 : 3");
            });
        }

        @Test
        void 커스텀_구분자와_기본구분자가_섞인경우() {
            assertSimpleTest(() ->
                    assertThatThrownBy (() -> runException("//.\\n1.2,"))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
