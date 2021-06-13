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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
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
    String login = "malina.katrina@gmail.com";
    String password = "123456";
//    String locatorAuth = "//button[contains(text(), 'Вход')]";
    String locatorAuth = "body > div.body-wrapper > div > header.header2.header2_non-auth > div > div.header2__right > div.header2__auth-container > button";
    driver.findElement(By.cssSelector(locatorAuth)).click();
    driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).sendKeys(login);
    logger.info("Введен логин");
    driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password);
    logger.info("Введен пароль");
    driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
    logger.info("Авторизация успешна");
    }

    public void enterLK() throws InterruptedException {
    //String locatorLK = ".ic-blog-default-avatar";
    //WebElement avatar = driver.findElement(By.cssSelector(locatorLK));
    //Actions actions = new Actions(driver);
    //actions.moveToElement(avatar).build().perform();
    Thread.sleep(1000);
    driver.get("https://otus.ru/lk/biography/personal/");
    logger.info("Открыт личный кабинет");
    }




    @Test
    public void personalDataOfOtus() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,10);

        //1. Открыть otus.ru
        driver.get(("https://otus.ru"));
        logger.info("Открыта страница Отус");

        driver.manage().window().setSize(new Dimension(1900,800));
        logger.info("Открыто окно браузера 1900*800");

//        WebElement approval = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'ОК')]")));
//        approval.click();
//        logger.info("Убираем всплывашку про Куки");

        //2. Авторизоваться на сайте
        auth();
        //3. Войти в личный кабинет
        enterLK();
        //4. В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        driver.findElement(By.id("id_fname")).clear();
        driver.findElement(By.id("id_fname_latin")).clear();
        driver.findElement(By.id("id_lname")).clear();
        driver.findElement(By.id("id_lname_latin")).clear();
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).clear();
        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.js-lk-cv > div.container.container-padding-bottom > div.container__row > div.container__col.container__col_9.container__col_md-8.container__col_sm-12.container__col_border-left.lk-rightbar.print-block.print-wide > div > form > div:nth-child(2) > div.container__col.container__col_12 > div:nth-child(2) > div.js-formset > div > div:nth-child(1) > div.container__col.container__col_12.container__col_md-0 > div:nth-child(2) > button")).click();
        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.js-lk-cv > div.container.container-padding-bottom > div.container__row > div.container__col.container__col_9.container__col_md-8.container__col_sm-12.container__col_border-left.lk-rightbar.print-block.print-wide > div > form > div:nth-child(2) > div.container__col.container__col_12 > div:nth-child(2) > div.js-formset > div > div:nth-child(2) > div.container__col.container__col_12.container__col_md-0 > div:nth-child(2) > button")).click();


        driver.findElement(By.id("id_fname")).sendKeys("Екатерина");
        driver.findElement(By.id("id_fname_latin")).sendKeys("Ekaterina");
        driver.findElement(By.id("id_lname")).sendKeys("Малинкина");
        driver.findElement(By.id("id_lname_latin")).sendKeys("Malinkina");
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).sendKeys("01.01.2000");
        //Страна
        if(!driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText().contains("Россия"))
        {
            driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Россия')]")).click();
        }
        //Город
        if(!driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText().contains("Москва"))
        {
            driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Москва')]")).click();
        }
        //Уровень английского
        if(!driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText().contains("Начальный уровень (Beginner)"))
        {
            driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Начальный уровень (Beginner)')]")).click();
        }
        //Добавить два контакта
//        driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]")).click();
//        driver.findElement(By.xpath("//span[contains(text(), 'Способ связи')]")).click();
//        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.js-lk-cv > div.container.container-padding-bottom > div.container__row > div.container__col.container__col_9.container__col_md-8.container__col_sm-12.container__col_border-left.lk-rightbar.print-block.print-wide > div > form > div:nth-child(2) > div.container__col.container__col_12 > div:nth-child(2) > div.js-formset > div > div:nth-child(4) > div.container__col.container__col_9.container__col_ssm-12 > div > div > div > div > div > button:nth-child(2)")).click();
//        driver.findElement(By.id("id_contact-0-value")).sendKeys("malinki");
//
//        driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]")).click();
//        driver.findElement(By.xpath("//span[contains(text(), 'Способ связи')]")).click();
//        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.js-lk-cv > div.container.container-padding-bottom > div.container__row > div.container__col.container__col_9.container__col_md-8.container__col_sm-12.container__col_border-left.lk-rightbar.print-block.print-wide > div > form > div:nth-child(2) > div.container__col.container__col_12 > div:nth-child(2) > div.js-formset > div > div:nth-child(3) > div.container__col.container__col_9.container__col_ssm-12 > div > div > div > div > div > button:nth-child(5)")).click();
//        driver.findElement(By.id("id_contact-1-value")).sendKeys("malinki");

        //5. Нажать сохранить
        driver.findElement(By.xpath("//*[contains(text(), 'Сохранить и продолжить')]")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        //6. Открыть https://otus.ru в “чистом браузере”
        driver.quit();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        logger.info("Драйвер поднят");
        driver.get(("https://otus.ru"));
        //7. Авторизоваться на сайте
        auth();
        //8. Войти в личный кабинет
        enterLK();
        //9. Проверить, что в разделе о себе отображаются указанные ранее данные
        Assert.assertEquals("Екатерина", driver.findElement(By.id("id_fname")).getAttribute("value"));
        Assert.assertEquals("Ekaterina", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        Assert.assertEquals("Малинкина", driver.findElement(By.id("id_lname")).getAttribute("value"));
        Assert.assertEquals("Malinkina", driver.findElement(By.id("id_lname_latin")).getAttribute("value"));
        Assert.assertEquals("01.01.2000", driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).getAttribute("value"));
        Assert.assertEquals("Россия", driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Москва", driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Начальный уровень (Beginner)", driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText());
    }

}



