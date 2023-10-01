package com.github.sergey8193.qascooter;

import com.github.sergey8193.qascooter.constants.Urls;
import com.github.sergey8193.qascooter.pom.TrackPage;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.sergey8193.qascooter.constants.WebBrowsers.TEST_BROWSER;

public class TrackPageTest extends BaseWeb {

    public TrackPageTest() { super(TEST_BROWSER, Urls.QA_SCOOTER_TRACK_PAGE_URL); }

    static Stream<Arguments> orderStatusArgsProviderFactory() {
        return Stream.of(
                Arguments.of( "26", "Самокат на складе", "Проверить статус существующего ордера" ),
                Arguments.of( "12", "Курьер задерживается", "Проверить статус существующего ордера" ),
                Arguments.of( "5", "Ордер не найден", "Проверить статус несуществующего ордера" )
        );
    }

    @ParameterizedTest(name = "{2} №{0} -> {1}")
    @MethodSource("orderStatusArgsProviderFactory")
    public void orderStatusTest(String orderId, String expectedOrderStatus, String testTitle) {
        String actualOrderStatus = new TrackPage(driver)
                .provideRequiredOrderNumber(orderId)
                .clickSeeButton()
                .getOrderStatus();
        String errorMessage = testTitle + " - Ожидается: " + expectedOrderStatus;
        Assertions.assertEquals(expectedOrderStatus, actualOrderStatus, errorMessage);
    }
}
