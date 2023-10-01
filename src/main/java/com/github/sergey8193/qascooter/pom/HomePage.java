package com.github.sergey8193.qascooter.pom;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

import static com.github.sergey8193.qascooter.constants.Durations.*;

public class HomePage extends BasePage {
    private final By upperNewOrderButton = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");
    private final By lowerNewOrderButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");
    private final By Questions = By.xpath(".//div[starts-with(@id, 'accordion__heading-')]");
    private final By Answers = By.xpath(".//div[starts-with(@id, 'accordion__panel-')]");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage scrollToNewOrderButton(String buttonType) {
        new WebDriverWait(driver, PAGE_TIMEOUT).until(driver -> driver.findElement(upperNewOrderButton).isDisplayed());
        new WebDriverWait(driver, PAGE_TIMEOUT).until(driver -> driver.findElement(lowerNewOrderButton).isDisplayed());
        By buttonLocator = Objects.equals(buttonType, "lower") ? lowerNewOrderButton : upperNewOrderButton;
        WebElement element = driver.findElement(buttonLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        return this;
    }

    public OrderPage clickNewOrderButton(String buttonType) {
        By buttonLocator = Objects.equals(buttonType, "lower") ? lowerNewOrderButton : upperNewOrderButton;
        new WebDriverWait(driver, ELEMENT_TIMEOUT).until(driver -> driver.findElement(buttonLocator).isDisplayed());
        driver.findElement(buttonLocator).click();
        return (new OrderPage(driver));
    }

    public HomePage scrollToQuestion(int index) {
        new WebDriverWait(driver, PAGE_TIMEOUT).until(driver -> driver.findElement(upperNewOrderButton).isDisplayed());
        WebElement element = driver.findElements(Questions).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return this;
    }

    public HomePage clickQuestion(int index) {
        new WebDriverWait(driver, SECTION_TIMEOUT).until(driver -> driver.findElements(Questions).get(index).isDisplayed());
        driver.findElements(Questions).get(index).click();
        return this;
    }

    public String getQuestionAnswer(int index) {
        new WebDriverWait(driver, SECTION_TIMEOUT).until(driver -> driver.findElements(Answers).get(index).isDisplayed());
        return driver.findElements(Answers).get(index).getText(); }
}
