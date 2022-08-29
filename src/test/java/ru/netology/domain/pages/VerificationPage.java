package ru.netology.domain.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private SelenideElement verificationErrorMessage = $("[data-test-id='error-notification']");
    private SelenideElement manyTriesErrorMessage = $("[data-test-id='error-notification']");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void verifyAs(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        verifyAs(verificationCode.getCode());
        return new DashboardPage();
    }

    public void invalidVerify(DataHelper.VerificationCode verificationCode) {
        verifyAs(verificationCode.getCode());
        verificationErrorMessage.shouldHave(text("Ошибка " + "Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void invalidVerifyMore3Times(DataHelper.VerificationCode verificationCode) {
        verifyAs(verificationCode.getCode());
        manyTriesErrorMessage.shouldHave(text("Ошибка " + "Ошибка! Превышено количество попыток ввода кода!"));
    }
}