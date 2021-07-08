import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ContactsPage;
import pages.LoginPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLesson9 {


    private Logger logger = LogManager.getLogger(TestLesson9.class);
    protected WebDriver driver;
    private Instant wait;

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @After
    public void End() {
        if (driver != null)
            driver.quit();
    }

    public void auth() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
//        FileInputStream fis;
//        Properties property = new Properties();
//
//        try {
//            fis = new FileInputStream("src/main/resources/config.properties");
//            property.load(fis);
//            String login = property.getProperty("login");
//            String password = property.getProperty("password");

    String login = "malina.katrina@gmail.com";
    String password = "123456";
    loginPage.ButtonEntrance.click();
//    loginPage.loginInput.sendKeys(property.getProperty("login"));
    loginPage.loginInput.sendKeys(login);
    logger.info("Введен логин");
//    loginPage.passwordInput.sendKeys(property.getProperty("password"));
    loginPage.passwordInput.sendKeys(password);
    logger.info("Введен пароль");
    loginPage.submitForm.submit();
    logger.info("Авторизация успешна");
    }

    public void enterLK() throws InterruptedException {

    Thread.sleep(1000);
    driver.get("https://otus.ru/lk/biography/personal/");
    logger.info("Открыт личный кабинет");
    }




    @Test
    public void personalDataOfOtus() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,10);
        ContactsPage contactsPage = PageFactory.initElements(driver, ContactsPage.class);
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);

        //1. Открыть otus.ru
        driver.get(("https://otus.ru"));
        logger.info("Открыта страница Отус");

        driver.manage().window().setSize(new Dimension(1900,800));
        logger.info("Открыто окно браузера 1900*800");

//        WebElement approval = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'ОК')]")));
//        approval.click();
//        logger.info("Убираем всплывашку про Куки");

        //2. Авторизоваться на сайте
        loginPage.auth();
        //3. Войти в личный кабинет
        contactsPage.enterLK();
        //4. В разделе "О себе" заполнить все поля "Личные данные"
        contactsPage.name.clear();
        contactsPage.nameLatin.clear();
        contactsPage.lastName.clear();
        contactsPage.lastNameLatin.clear();
        contactsPage.dateOfBirth.clear();
//        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.js-lk-cv > div.container.container-padding-bottom > div.container__row > div.container__col.container__col_9.container__col_md-8.container__col_sm-12.container__col_border-left.lk-rightbar.print-block.print-wide > div > form > div:nth-child(2) > div.container__col.container__col_12 > div:nth-child(2) > div.js-formset > div > div:nth-child(1) > div.container__col.container__col_12.container__col_md-0 > div:nth-child(2) > button")).click();
//        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.js-lk-cv > div.container.container-padding-bottom > div.container__row > div.container__col.container__col_9.container__col_md-8.container__col_sm-12.container__col_border-left.lk-rightbar.print-block.print-wide > div > form > div:nth-child(2) > div.container__col.container__col_12 > div:nth-child(2) > div.js-formset > div > div:nth-child(2) > div.container__col.container__col_12.container__col_md-0 > div:nth-child(2) > button")).click();


        contactsPage.name.sendKeys("Екатерина");
        contactsPage.nameLatin.sendKeys("Ekaterina");
        contactsPage.lastName.sendKeys("Малинкина");
        contactsPage.lastNameLatin.sendKeys("Malinkina");
        contactsPage.dateOfBirth.sendKeys("01.01.2000");
        //Страна
        if(!contactsPage.country.getText().contains("Россия"))
        {
            contactsPage.country.click();
            contactsPage.countryRussia.click();
        }
        //Город
        if(!contactsPage.city.getText().contains("Москва"))
        {
            contactsPage.city.click();
            contactsPage.cityMoscow.click();
        }
        //Уровень английского
        if(!contactsPage.englishLevel.getText().contains("Начальный уровень (Beginner)"))
        {
            contactsPage.englishLevel.click();
            contactsPage.englishLevelBeginner.click();
        }

        //5. Нажать сохранить
        contactsPage.saveButton.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        //6. Открыть https://otus.ru в “чистом браузере”
        driver.quit();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        logger.info("Драйвер поднят");
        driver.get(("https://otus.ru"));

        //7. Авторизоваться на сайте
//        loginPage.auth();
        auth();
        //8. Войти в личный кабинет
        enterLK();
        //9. Проверить, что в разделе о себе отображаются указанные ранее данные
        System.out.println("Привет!");
        Assert.assertEquals( "Ошибка в имени","Екатерина", driver.findElement(By.id("id_fname")).getAttribute("value"));
        Assert.assertEquals("Ошибка в имени на латинском","Ekaterina", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        Assert.assertEquals("Ошибка в фамилии","Малинкина", driver.findElement(By.id("id_lname")).getAttribute("value"));
        Assert.assertEquals("Ошибка в фамилии на латинском","Malinkina", driver.findElement(By.id("id_lname_latin")).getAttribute("value"));
        Assert.assertEquals("Ошибка в дате рождения","01.01.2000", driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).getAttribute("value"));
        Assert.assertEquals("Ошибка в стране","Россия", driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Ошибка в городе","Москва", driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Ошибка в уровне англ.","Начальный уровень (Beginner)", driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText());
    }

}



