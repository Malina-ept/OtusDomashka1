package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Properties;

public class LoginPage {

    private final WebDriver driver;
    private Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = "body > div.body-wrapper > div > header.header2.header2_non-auth > div > div.header2__right > div.header2__auth-container > button")
    public WebElement ButtonEntrance;

    @FindBy(css = "div.new-input-line_slim:nth-child(3) > input:nth-child(1)")
    public WebElement loginInput;

    @FindBy(css = ".js-psw-input")
    public WebElement passwordInput;

    @FindBy(css = "div.new-input-line_last:nth-child(5) > button:nth-child(1)")
    public WebElement submitForm;

    public void auth() {
//        Properties property = new Properties();
//
//        String login = property.getProperty("login");
//        String password = property.getProperty("password");
        String login = "malina.katrina@gmail.com";
        String password = "123456";
        ButtonEntrance.click();
        loginInput.sendKeys(login);
        logger.info("Введен логин");
        passwordInput.sendKeys(password);
        logger.info("Введен пароль");
        submitForm.submit();
        logger.info("Авторизация успешна");
    }

}
