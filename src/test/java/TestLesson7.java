import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestLesson7 {

    private Logger logger = LogManager.getLogger(TestLesson7.class);
    protected WebDriver driver;

    @Before
    public void StartUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void End(){
        if (driver!=null)
            driver.quit();
    }

    @Test
    public void checkTitle() {
        driver.get("https://otus.ru");
        logger.info("Сайт открыт");
        logger.info("Проверяем title страницы");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        assertEquals("Текст тайтла соответствует ожидаемому", expectedTitle, actualTitle);
    }

    @Test
    public void checkTitleContacts() {
        driver.get("https://otus.ru");
        logger.info("Сайт открыт");
        // driver.get("https://otus.ru/contacts");
        driver.findElement(By.xpath("//header//a[@href='/contacts/']")).click();
        logger.info("Открыта страница Контакты");
        logger.info("Проверяем title страницы Контакты");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Контакты | OTUS";
        assertEquals("Текст тайтла страницы Контакты соответствует ожидаемому", expectedTitle, actualTitle);
    }


    @Test
    public void checkAdressInContacts() {
        driver.get("https://otus.ru/contacts");
        logger.info("Открыта страница Контакты");
        assertEquals( "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02", driver.findElement(By.cssSelector("#__next > div.Page__Container-sc-2em8v9-0.hYCfxl > div > div.Page__Content-sc-2em8v9-1.iupdqh > div.Layout-sc-1hmcglv-0.hdLBmo > div:nth-child(3) > div.styles__Content-c0qfa0-5.gTwLpc")).getText());
        logger.info("Адрес соответствует ожидаемому");
    }

    @Test
    public void checkOpenMaxScreen() {
        driver.get("https://otus.ru");
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");
        logger.info(driver.manage().window().getSize());
    }

    @Test
    public void checkFAQ() {
        driver.get("https://otus.ru");
        logger.info("Сайт открыт");
        driver.findElement(By.xpath("//header//a[@href='/faq/']")).click();
        logger.info("Открыта страница FAQ");
        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.container.container-padding-bottom.js-faq > div.container__row.js-tabs > div:nth-child(2) > div:nth-child(4) > div.faq-question__question.js-faq-question-question")).click();
        assertEquals( "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями. Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”", driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.container.container-padding-bottom.js-faq > div.container__row.js-tabs > div:nth-child(2) > div:nth-child(4) > div.faq-question__answer.js-faq-answer")).getText());
        logger.info("Текст соответствует ожидаемому");

    }

    @Test
    public void Subscription() throws InterruptedException {
        driver.get("https://otus.ru");
        logger.info("Сайт открыт");
        driver.findElement(By.cssSelector(".input.footer2__subscribe-input")).clear();
        logger.info("Поле email очищено");
        driver.findElement(By.cssSelector(".input.footer2__subscribe-input")).sendKeys("test@test.com");
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector(".footer2__subscribe-button.button.button_blue.button_as-input")).click();
        logger.info("Нажата кнопка Подписаться");
        assertEquals( "Вы успешно подписались", driver.findElement(By.cssSelector("body > div.body-wrapper > div > footer > div > div > div.footer2__content > div > div:nth-child(3) > p.subscribe-modal__success")).getText());
        logger.info("Текст Вы успешно подписались");

    }

    @Test
    public void Tele2() throws InterruptedException {
        driver.get("https://msk.tele2.ru/shop/number");
        logger.info("Сайт Tele2 открыт");
        driver.findElement(By.id("searchNumber")).sendKeys("97");
        logger.info("введены 97 в поиск номера");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#root > div > div.height-holder-mobile > div > div > div > div > div.main-content > div:nth-child(1) > div > div > div > div.catalog-numbers.with-overlay.overlay-big > div > div > div.center.left-xs > a")).click();
        logger.info("Появилась кнопка Показать еще");
    }
}


