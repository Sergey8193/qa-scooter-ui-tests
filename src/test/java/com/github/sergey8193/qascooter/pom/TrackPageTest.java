package com.github.sergey8193.qascooter.pom;

import com.github.sergey8193.qascooter.constants.Urls;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.sergey8193.qascooter.constants.WebBrowsers.TEST_BROWSER;

@DisplayName("Check order statuses contents")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TrackPageTest extends BaseWeb {

    public TrackPageTest() { super(TEST_BROWSER, Urls.QA_SCOOTER_TRACK_PAGE_URL); }

    static Stream<Arguments> OrderSearchRequestArgsProviderFactory() {
        return Stream.of(
                Arguments.of( "26", "Самокат на складе", "existent" ),
                Arguments.of( "12", "Курьер задерживается", "existent" ),
                Arguments.of( "5", "Ордер не найден", "non-existent" )
        );
    }

    @ParameterizedTest(name = "Check status of {2} order №{0}")
    @MethodSource("OrderSearchRequestArgsProviderFactory")
    @PerformanceBenchmarks
    void trackHeaderOrderSearchRequestShouldBeCorrectOrderStatus(String orderId, String expectedStatus, String orderType) {
        String actualOrderStatus = new TrackPage(driver)
                .provideRequiredOrderNumber(orderId)
                .clickSeeButton()
                .getOrderStatus();
        String errorMessage = "Status of " + orderType + " order №" + orderId + " - expected: " + expectedStatus;
        Assertions.assertEquals(expectedStatus, actualOrderStatus, errorMessage);
    }
}
