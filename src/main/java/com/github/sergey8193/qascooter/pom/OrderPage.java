package com.github.sergey8193.qascooter.pom;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.sergey8193.qascooter.constants.NewOrderFormInputField;

import java.util.ArrayList;
import java.util.List;

import static com.github.sergey8193.qascooter.constants.Durations.ELEMENT_TIMEOUT;
import static com.github.sergey8193.qascooter.constants.Durations.SECTION_TIMEOUT;

public class OrderPage extends BasePage {
    // New order initial section
    private final By nameInput = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN' and @placeholder='* Имя']");
    private final By surnameInput = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN' and @placeholder='* Фамилия']");
    private final By addressInput = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN' and @placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationInput = By.xpath(".//input[@class='select-search__input' and @placeholder='* Станция метро']");
    private final By telephoneInput = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN' and @placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Далее']");
    private final By nameInputErrorMessage = By.xpath( ".//div[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6' and text()='Введите корректное имя']");
    private final By surnameInputErrorMessage = By.xpath( ".//div[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6' and text()='Введите корректную фамилию']");
    private final By addressInputErrorMessage = By.xpath( ".//div[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6' and text()='Введите корректный адрес']");
    private final By phoneInputErrorMessage = By.xpath( ".//div[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6' and text()='Введите корректный номер']");
    // New order lease section
    private final By leaseStartTimeInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By leaseDurationDropdownControl = By.xpath(".//div[@class='Dropdown-control']");
    private final By leaseDurationDropdownOptions = By.xpath(".//div[@class='Dropdown-option']");
    private final By blackColorCheckbox = By.xpath(".//input[@class='Checkbox_Input__14A2w' and @id='black']");
    private final By greyColorCheckbox = By.xpath(".//input[@class='Checkbox_Input__14A2w' and @id='grey']");
    private final By commentsInput = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN' and @placeholder='Комментарий для курьера']");
    private final By placeOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    // New order confirmation form
    private final By modalWindowOverlay = By.className("Order_Overlay__3KW-T");
    private final By modalWindow = By.xpath(".//div[@class='Order_Modal__YZ-d3']");
    private final By modalWindowHeader = By.className("Order_ModalHeader__3FDaJ");
    private final By orderConfirmationButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    public OrderPage(WebDriver driver){
        super(driver);
    }

    // New order initial section
    public String getInputErrorMessage(NewOrderFormInputField fieldName) {
        By locator;
        switch(fieldName) {
            case NAME: locator = nameInputErrorMessage; break;
            case SURNAME: locator = surnameInputErrorMessage; break;
            case ADDRESS: locator = addressInputErrorMessage; break;
            case PHONE: locator = phoneInputErrorMessage; break;
            default: locator = null;
        }
        return (locator != null) ? driver.findElement(locator).getText() : "";
    }

    public OrderPage provideNewOrderCustomerName(String customerName) {
        new WebDriverWait(driver, SECTION_TIMEOUT)
                .until(driver -> driver.findElement(nameInput).isEnabled());
        driver.findElement(nameInput).sendKeys(customerName);
        return this;
    }

    public OrderPage provideNewOrderCustomerSurname(String customerSurname) {
        new WebDriverWait(driver, ELEMENT_TIMEOUT)
                .until(driver -> driver.findElement(surnameInput).isEnabled());
        driver.findElement(surnameInput).sendKeys(customerSurname);
        return this;
    }

    public OrderPage provideNewOrderAddress(String address) {
        new WebDriverWait(driver, ELEMENT_TIMEOUT)
                .until(driver -> driver.findElement(addressInput).isEnabled());
        driver.findElement(addressInput).sendKeys(address);
        return this;
    }

    public OrderPage provideNewOrderMetroStation(String metroStation) {
        new WebDriverWait(driver, ELEMENT_TIMEOUT)
                .until(driver -> driver.findElement(metroStationInput).isEnabled());
        driver.findElement(metroStationInput).click();
        String metroStationOptionXpath = ".//*[contains(text(), '" + metroStation + "')]";
        boolean isPresent = driver.findElements(By.xpath(metroStationOptionXpath)).size() > 0;
        if (isPresent) { driver.findElement(By.xpath(metroStationOptionXpath)).click(); }
        return this;
    }

    public OrderPage provideNewOrderTelephone(String telephone) {
        new WebDriverWait(driver, ELEMENT_TIMEOUT)
                .until(driver -> driver.findElement(telephoneInput).isEnabled());
        driver.findElement(telephoneInput).sendKeys(telephone);
       ((JavascriptExecutor) driver).executeScript("(document.getElementsByTagName('input'))[0].focus();");
        return this;
    }

    public OrderPage clickNewOrderNextButton() {
        new WebDriverWait(driver, ELEMENT_TIMEOUT)
                .until(driver -> driver.findElement(nextButton).isEnabled());
        driver.findElement(nextButton).click();
        return this;
    }

    // New order lease section
    public OrderPage provideNewOrderLeaseStartTime(String leaseStartTime) {
        new WebDriverWait(driver, SECTION_TIMEOUT)
                .until(driver -> driver.findElement(leaseStartTimeInput).isEnabled());
        driver.findElement(leaseStartTimeInput).sendKeys(leaseStartTime);
        driver.findElement(leaseStartTimeInput).sendKeys(Keys.ENTER);
        return this;
    }
    public OrderPage provideNewOrderLeaseDuration(String leaseDurationInDays) {
        driver.findElement(leaseDurationDropdownControl).click();
        int intLeaseDurationInDays = Integer.parseInt(leaseDurationInDays) - 1;
        int maxListIndex = driver.findElements(leaseDurationDropdownOptions).size() - 1;
        int listIndex = Math.max(Math.min(intLeaseDurationInDays, maxListIndex), 0);
        driver.findElements(leaseDurationDropdownOptions).get(listIndex).click();
        return this;
    }

    public OrderPage provideNewOrderScooterColor(String scooterColor) {
        if (scooterColor.contains("black")) { driver.findElement(blackColorCheckbox).click(); }
        if (scooterColor.contains("grey")) { driver.findElement(greyColorCheckbox).click(); }
        return this;
    }

    public OrderPage provideNewOrderCommentForCourier(String CommentForCourier) {
        driver.findElement(commentsInput).sendKeys(CommentForCourier);
        return this;
    }

    public OrderPage clickPlaceOrderButton() {
        new WebDriverWait(driver, ELEMENT_TIMEOUT)
                .until(driver -> driver.findElement(placeOrderButton).isEnabled());
        driver.findElement(placeOrderButton).click();
        return this;
    }

    // New order confirmation form
    public OrderPage clickConfirmationButton(boolean unlock) {
        new WebDriverWait(driver, SECTION_TIMEOUT)
                .until(driver -> driver.findElement(orderConfirmationButton).isEnabled());
        if (unlock) {unlockConfirmationButton();}
        driver.findElement(orderConfirmationButton).click();
        return this;
    }

    public String getModalWindowsHeaderMessage() {
        return driver.findElement(modalWindowHeader).getText();
    }

    private void unlockConfirmationButton() {
        List<WebElement> Lockers = new ArrayList<>();
        Lockers.add(driver.findElement(modalWindowOverlay));
        Lockers.add(driver.findElement(modalWindow));
        Lockers.add(driver.findElement(modalWindowHeader));
        for (WebElement locker :Lockers) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].className = '';", locker);
        }
    }
}
