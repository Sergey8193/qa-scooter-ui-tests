package com.github.sergey8193.qascooter.pom;

import com.github.sergey8193.qascooter.constants.Urls;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;

import static com.github.sergey8193.qascooter.constants.WebBrowsers.TEST_BROWSER;

@DisplayName("Check page header functionality")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PageHeaderTest extends BaseWeb {
    private final static String NON_EXISTENT_ORDER_ID = "5";

    public PageHeaderTest() {
        super(TEST_BROWSER, Urls.QA_SCOOTER_TRACK_PAGE_URL);
    }

    @Test
    @DisplayName("Check Yandex home page link functionality")
    @PerformanceBenchmarks
    void pageHeaderYandexLogoClickShouldBeTransitionToYandexPage() {
        String actualUrl = new BasePage(driver)
                .clickYandexLogo()
                .waitForPageInNewTabIsLoaded()
                .getYandexUrl();
        String errorMessage ="Go to Yandex home page" +
                " - expected URL: " + Urls.YANDEX_DZEN_URL;
        Assertions.assertEquals(Urls.YANDEX_DZEN_URL, actualUrl, errorMessage);
    }

    @Test
    @DisplayName("Check qa_scooter home page link functionality")
    @PerformanceBenchmarks
    void pageHeaderScooterLogoClickShouldBeTransitionToScooterPage() {
        String actualUrl = new BasePage(driver)
                .clickScooterLogo()
                .getCurrentUrl();
        String errorMessage ="Go to qa_scooter home page" +
                " - expected URL: " + Urls.QA_SCOOTER_MAIN_PAGE_URL;
        Assertions.assertEquals(Urls.QA_SCOOTER_MAIN_PAGE_URL, actualUrl, errorMessage);
    }

    @Test
    @DisplayName("Check non-existent order search request")
    @PerformanceBenchmarks
    void pageHeaderNonExistentOrderSearchRequestShouldBeNotFoundOrderImage() {
        boolean notFoundMessageIsDisplayed = new BasePage(driver)
                .clickScooterLogo()
                .clickOrderStatusButton()
                .provideOrderNumber(NON_EXISTENT_ORDER_ID)
                .clickGoButton()
                .checkIfOrderNotFoundImageIsDisplayed();
        String errorMessage = "Search request for non-existent order" +
                " - expected 'NotFoundOrder' image";
        Assertions.assertTrue(notFoundMessageIsDisplayed, errorMessage);
    }
}
