package ru.netology.domain.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import ru.netology.domain.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Value

public class LoginPage {
    SelenideElement loginField = $("[data-test-id='login'] input");
    SelenideElement passwordField = $("[data-test-id='password'] input");
    SelenideElement loginButton = $("[data-test-id='action-login']");
    SelenideElement loginErrorMessage = $("[data-test-id='error-notification']");

    public void loginAs(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginAs(info.getLogin(), info.getPassword());
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        loginAs(info.getLogin(), info.getPassword());
        loginErrorMessage.shouldHave(text(" Ошибка! Неверно указан логин или пароль"));
    }
}