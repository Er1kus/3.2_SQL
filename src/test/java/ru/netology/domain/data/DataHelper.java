package ru.netology.domain.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import lombok.Value;

import java.sql.DriverManager;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getWrongAuthInfo() {
        Faker faker = new Faker();
        return new AuthInfo(faker.name().username(), faker.internet().password());
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        String verificationCode;
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
        ) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        }
        return new

                VerificationCode(verificationCode);

    }

    public static VerificationCode getWrongVerificationCodeFor() {
        Faker faker = new Faker();
        return new VerificationCode(faker.internet().password());
    }

    @SneakyThrows
    public static void DropData() {
        var runner = new QueryRunner();
        var deleteUsers = "DELETE FROM users";
        var deleteCards = "DELETE FROM cards";
        var deleteAuthCodes = "DELETE FROM auth_codes";
        var deleteCardTransactions = "DELETE FROM card_transactions";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
        ) {
            runner.update(conn, deleteUsers);
            runner.update(conn, deleteCards);
            runner.update(conn, deleteAuthCodes);
            runner.update(conn, deleteCardTransactions);

        }
    }
}


