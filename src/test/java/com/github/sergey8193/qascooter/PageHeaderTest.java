package com.github.sergey8193.qascooter;

import com.github.sergey8193.qascooter.constants.Urls;
import com.github.sergey8193.qascooter.pom.BasePage;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;

import static com.github.sergey8193.qascooter.constants.WebBrowsers.TEST_BROWSER;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PageHeaderTest extends BaseWeb {
    private final static String NON_EXISTENT_ORDER_ID = "5";

    PageHeaderTest() {
        super(TEST_BROWSER, Urls.QA_SCOOTER_TRACK_PAGE_URL);
    }

    @Test
    void clickYandexLogoTest() {
        String actualUrl = new BasePage(driver)
                .clickYandexLogo()
                .waitForPageInNewTabIsLoaded()
                .getYandexUrl();
        String errorMessage ="Проверить переход на страницу Яндекс" +
                " - Ожидается URL: " + Urls.YANDEX_DZEN_URL;
        Assertions.assertEquals(Urls.YANDEX_DZEN_URL, actualUrl, errorMessage);
    }

    @Test
    void clickScooterLogoTest() {
        String actualUrl = new BasePage(driver)
                .clickScooterLogo()
                .getCurrentUrl();
        String errorMessage ="Проверить переход на домашнюю страницу qa_scooter" +
                " - Ожидается URL: " + Urls.QA_SCOOTER_MAIN_PAGE_URL;
        Assertions.assertEquals(Urls.QA_SCOOTER_MAIN_PAGE_URL, actualUrl, errorMessage);
    }

    @Test
    void clickGoButtonForNonExistentOrderTest() {
        boolean notFoundMessageIsDisplayed = new BasePage(driver)
                .clickScooterLogo()
                .clickOrderStatusButton()
                .provideOrderNumber(NON_EXISTENT_ORDER_ID)
                .clickGoButton()
                .checkIfOrderNotFoundImageIsDisplayed();
        String errorMessage = "Проверить переход на страницу qa_scooter/track" +
                " - Ожидается 'NotFoundOrder' изображение";
        Assertions.assertTrue(notFoundMessageIsDisplayed, errorMessage);
    }
}
