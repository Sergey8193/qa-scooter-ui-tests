package com.github.sergey8193.qascooter.pom;

import com.github.sergey8193.qascooter.constants.Durations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TrackPage extends BasePage {
    private final By roadMapInfoTitle = By.xpath(".//div[@class='Track_Order__1S6E9']");
    private final By trackOrderIdInput = By.xpath(".//div[@class='Track_Form__N4FE3']/div[@class='Input_InputContainer__3NykH']/input");
    private final By seeButton = By.xpath(".//div[@class='Track_Form__N4FE3']//button[text()='Посмотреть']");
    private final By trackNotFoundImageWrapper = By.xpath(".//div[@class='Track_NotFound__6oaoY']");
    private final static String ROAD_MAP_REGULAR_ACTIVE_COLOR_1 = "rgb(26, 27, 34)";
    private final static String ROAD_MAP_REGULAR_ACTIVE_COLOR_2 = "rgba(26, 27, 34, 1)";
    private final static String ROAD_MAP_IRREGULAR_ACTIVE_COLOR_1 = "rgb(253, 110, 112)";
    private final static String ROAD_MAP_IRREGULAR_ACTIVE_COLOR_2 = "rgba(253, 110, 112, 1)";

    public TrackPage(WebDriver driver) {
        super(driver);
    }

    public TrackPage provideRequiredOrderNumber(String orderId) {
        driver.findElement(trackOrderIdInput).sendKeys(orderId);
        return this;
    }

    public TrackPage clickSeeButton() {
        driver.findElement(seeButton).click();
        return new TrackPage(driver);
    }

    public String getOrderStatus() {
        try { new WebDriverWait(driver, Durations.SECTION_TIMEOUT)
                .until(driver -> driver.findElement(elusiveElement).isEnabled());
        }
        catch (Exception e) {
            List<WebElement> roadMapTitles = driver.findElements(roadMapInfoTitle);
            if (roadMapTitles.size() > 0) {
                for (WebElement title : roadMapTitles) {
                    String color = title.getCssValue("color");
                    if (color.equals(ROAD_MAP_REGULAR_ACTIVE_COLOR_1) || color.equals(ROAD_MAP_REGULAR_ACTIVE_COLOR_2) ||
                            color.equals(ROAD_MAP_IRREGULAR_ACTIVE_COLOR_1) || color.equals(ROAD_MAP_IRREGULAR_ACTIVE_COLOR_2)) {
                        return title.getText();
                    }
                }
            }
        }
        return "Ордер не найден";
    }

    public boolean checkIfOrderNotFoundImageIsDisplayed() {
        try { new WebDriverWait(driver, Durations.SECTION_TIMEOUT)
                .until(driver -> driver.findElement(elusiveElement).isEnabled());
        }
        catch (Exception e) {
            List<WebElement> trackNotFoundImages = driver.findElements(trackNotFoundImageWrapper);
            if (trackNotFoundImages.size() > 0) {
                new WebDriverWait(driver, Durations.ELEMENT_TIMEOUT)
                        .until(driver -> driver.findElement(trackNotFoundImageWrapper).isEnabled());
                return true;
            }
        }
        return false;
    }
}
