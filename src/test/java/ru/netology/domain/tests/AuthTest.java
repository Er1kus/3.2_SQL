package ru.netology.domain.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.pages.LoginPage;


import static com.codeborne.selenide.Selenide.open;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTest {
    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @AfterAll
    static void clearData() {
        DataHelper.DropData();
    }

    @Order(3)
    @RepeatedTest(2)
    void shouldLoginWithWrongVerificationCode() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autoInfo);
        var verificationCode = DataHelper.getWrongVerificationCodeFor();
        verificationPage.invalidVerify(verificationCode);
    }

    @Order(4)
    @Test()
    void shouldLoginWithWrongVerificationCodeMoreThan3TimesInARow() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autoInfo);
        var verificationCode = DataHelper.getWrongVerificationCodeFor();
        verificationPage.invalidVerifyMore3Times(verificationCode);
    }

    @Order(2)
    @Test
    void shouldSetWrongLogin() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getWrongAuthInfo();
        loginPage.invalidLogin(autoInfo);
    }

    @Order(1)
    @Test
    void shouldLoginByHappyPath() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autoInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();
        verificationPage.validVerify(verificationCode);
    }
}

