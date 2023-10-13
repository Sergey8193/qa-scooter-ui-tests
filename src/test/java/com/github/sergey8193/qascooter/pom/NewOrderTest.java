package com.github.sergey8193.qascooter.pom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.github.sergey8193.qascooter.constants.NewOrderFormInputField;

import java.util.stream.Stream;

import static com.github.sergey8193.qascooter.constants.Urls.QA_SCOOTER_MAIN_PAGE_URL;
import static com.github.sergey8193.qascooter.constants.WebBrowsers.*;
import static com.github.sergey8193.qascooter.constants.NewOrderFormInputField.*;

@DisplayName("Check the new order creation form")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class NewOrderTest extends BaseWeb {

    public NewOrderTest() {
        super(TEST_BROWSER, QA_SCOOTER_MAIN_PAGE_URL);
    }

    static Stream<Arguments> newOrderInvalidDataInputArgsProviderFactory() {
        return Stream.of(
                Arguments.of("lower",
                "Name", "Кузнецов", "Москва", "Черкизовская", "+",
                NAME, "Введите корректное имя"
                ),
                Arguments.of("lower",
                        "Иван", "Surname", "Москва", "Черкизовская", "+791311123458888888888",
                        SURNAME, "Введите корректную фамилию"
                ),
                Arguments.of("lower",
                        "Иван", "Кузнецов", "Address", "Черкизовская", "+7913",
                        ADDRESS, "Введите корректный адрес"

                ),
                Arguments.of("lower",
                        "Иван", "Кузнецов", "Москва", "Metro Station", "Telephone number",
                        SUBWAY_STATION,""
                ),
                Arguments.of("lower",
                        "Иван", "Кузнецов", "Москва", "Черкизовская", "Telephone number",
                        PHONE, "Введите корректный номер"
                )
        );
    }

    @ParameterizedTest(name = "Check the error message in the field {6}")
    @MethodSource("newOrderInvalidDataInputArgsProviderFactory")
    @PerformanceBenchmarks
    void newOrderInvalidDataInputShouldBeCorrectErrorMessage(String buttonType, String name,
                                                    String surname, String address, String station, String phone,
                                                    NewOrderFormInputField fieldName, String expectedResult) {
        String actualErrorMessage = new HomePage(driver)
                .scrollToNewOrderButton(buttonType)
                .clickNewOrderButton(buttonType)
                .provideNewOrderCustomerName(name)
                .provideNewOrderCustomerSurname(surname)
                .provideNewOrderAddress(address)
                .provideNewOrderMetroStation(station)
                .provideNewOrderTelephone(phone)
                .getInputErrorMessage(fieldName);
        String errorMessage = "Field " + fieldName + " (" + name + ") - expected error message: " + expectedResult;
        Assertions.assertEquals(expectedResult, actualErrorMessage, errorMessage);
    }

    static Stream<Arguments> newOrderValidDataInputArgsProviderFactory() {
        return  Stream.of(
                Arguments.of("lower",
                        "Иван", "Кузнецов", "Москва", "Черкизовская", "+79131112345",
                        "20.10.2023", "7", "black grey", "Доставка желательна до 11:00 по московскому времени",
                        "Заказ оформлен"
                ),
                Arguments.of("upper",
                        "Мария", "Иванова", "Москва", "Сокольники", "+79652233358",
                        "24.10.2023", "5", "grey", "Предупредить за два часа до момента доставки",
                        "Заказ оформлен"
                )
        );
    }

    @ParameterizedTest(name = "Check new order creation with {0} button")
    @MethodSource("newOrderValidDataInputArgsProviderFactory")
    @PerformanceBenchmarks
    void newOrderValidDataInputShouldBeSuccessOrderCreation(String buttonType,
                                     String name, String surname, String address, String station, String phone,
                                     String leaseTime, String leaseDuration, String color, String comment,
                                     String expectedResult) {
        String actualResult = new HomePage(driver)
                .scrollToNewOrderButton(buttonType)
                .clickNewOrderButton( buttonType)
                .provideNewOrderCustomerName(name)
                .provideNewOrderCustomerSurname(surname)
                .provideNewOrderAddress(address)
                .provideNewOrderMetroStation(station)
                .provideNewOrderTelephone(phone)
                .clickNewOrderNextButton()
                .provideNewOrderLeaseStartTime(leaseTime)
                .provideNewOrderLeaseDuration(leaseDuration)
                .provideNewOrderScooterColor(color)
                .provideNewOrderCommentForCourier(comment)
                .clickPlaceOrderButton()
                // element click intercepted error life hack: unlock -> true
                .clickConfirmationButton(true)
                .getModalWindowsHeaderMessage().substring(0, 14);
        String errorMessage = "New order creation" + " - expected message: " + expectedResult;
        Assertions.assertEquals(expectedResult, actualResult, errorMessage);
    }
}
