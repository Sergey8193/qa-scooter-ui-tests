package com.github.sergey8193.qascooter.pom;

import com.github.sergey8193.qascooter.constants.Durations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class BasePage {
    protected final WebDriver driver;

    private final By logoYandex = By.xpath(".//a[@class='Header_LogoYandex__3TSOI' and @href='//yandex.ru']");
    private final By logoScooter = By.xpath(".//a[@class='Header_LogoScooter__3lsAR' and @href='/']");
    private final By orderStatusButton = By.xpath(".//button[@class='Header_Link__1TAG7']");
    private final By orderIdInput = By.xpath(".//div[@class='Header_SearchInput__3YRIQ']/div[@class='Input_InputContainer__3NykH']/input");
    private final By goButton = By.xpath(".//button[@class='Button_Button__ra12g Header_Button__28dPO']");
    protected final By elusiveElement = By.xpath(".//div[@class='Home_Track_Order']");

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    public BasePage clickYandexLogo() {
        driver.findElement(logoYandex).click();
        return this;
    }

    public BasePage waitForPageInNewTabIsLoaded() {
        new WebDriverWait(driver, Durations.PAGE_TIMEOUT).until(driver -> (driver.getWindowHandles().size() == 2));
        try { new WebDriverWait(driver, Durations.SECTION_TIMEOUT)
                .until(driver -> driver.findElement(elusiveElement).isEnabled());
        } catch (Exception e) { return this; }
        return this;
    }

    public String getYandexUrl() {
        ArrayList<String> allTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(allTabs.get(1));
        String actualUrl = driver.getCurrentUrl();
        driver.switchTo().window(allTabs.get(0));
        return actualUrl;
    }

    public BasePage clickScooterLogo() {
        driver.findElement(logoScooter).click();
        return this;
    }

    protected void waitForPageIsLoaded() {
        new WebDriverWait(driver, Durations.PAGE_TIMEOUT)
                .until(driver -> (driver.findElement(orderStatusButton).isDisplayed() &&
                        (driver.findElement(logoScooter).isDisplayed())));
    }

    public String getCurrentUrl() {
        waitForPageIsLoaded();
        return  driver.getCurrentUrl();
    }

    public BasePage clickOrderStatusButton() {
        waitForPageIsLoaded();
        driver.findElement(orderStatusButton).click();
        return this;
    }

    public BasePage provideOrderNumber(String orderId) {
        new WebDriverWait(driver, Durations.ELEMENT_TIMEOUT)
                .until((driver -> (driver.findElement(orderIdInput).isDisplayed()) &&
                        (driver.findElement(orderIdInput).isEnabled())));
        driver.findElement(orderIdInput).sendKeys(orderId);
        return this;
    }

    public TrackPage clickGoButton() {
        driver.findElement(goButton).click();
        return new TrackPage(driver);
    }
}
