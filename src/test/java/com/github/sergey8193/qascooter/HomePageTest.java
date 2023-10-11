package com.github.sergey8193.qascooter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.sergey8193.qascooter.pom.HomePage;

import java.util.stream.Stream;

import static com.github.sergey8193.qascooter.constants.Urls.QA_SCOOTER_MAIN_PAGE_URL;
import static com.github.sergey8193.qascooter.constants.WebBrowsers.*;

class HomePageTest extends BaseWeb {

    HomePageTest() {
        super(TEST_BROWSER, QA_SCOOTER_MAIN_PAGE_URL);
    }

    static Stream<Arguments> faqTextArgsProviderFactory() {
        return Stream.of(
                Arguments.of(0, "Проверить содержимое ответа на вопрос №1",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                Arguments.of(1, "Проверить содержимое ответа на вопрос №2",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                ),
                Arguments.of(2, "Проверить содержимое ответа на вопрос №3",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                ),
                Arguments.of(3, "Проверить содержимое ответа на вопрос №4",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                ),
                Arguments.of(4, "Проверить содержимое ответа на вопрос №5",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                ),
                Arguments.of(5, "Проверить содержимое ответа на вопрос №6",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                ),
                Arguments.of(6, "Проверить содержимое ответа на вопрос №7",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                ),
                Arguments.of(7, "Проверить содержимое ответа на вопрос №8",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                )
        );
    }

    @ParameterizedTest(name = "{1} -> {2}")
    @MethodSource("faqTextArgsProviderFactory")
    void faqTextTest(int questionIndex, String testTitle, String answerText){
        String actualAnswer = new HomePage(driver)
                .scrollToQuestion(questionIndex)
                .clickQuestion(questionIndex)
                .getQuestionAnswer(questionIndex);
        String errorText = testTitle + " - Ожидается текст: " + answerText;
        Assertions.assertEquals(answerText, actualAnswer, errorText);
    }
}
