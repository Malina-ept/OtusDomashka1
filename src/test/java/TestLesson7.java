import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestLesson7 {

    private static final String EXPECTED_OTUS_CONTACTS_TEXT = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";
    private static final String OTUS_WEBSITE_HOME_PAGE = "https://otus.ru";

    //@FindBy(xpath = "//header//a[@href='/contacts/']")
    //private WebElement contactsButton;

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
        driver.get(OTUS_WEBSITE_HOME_PAGE);
        logger.info("Сайт открыт");
        logger.info("Проверяем title страницы");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        assertEquals("Текст тайтла не соответствует ожидаемому", expectedTitle, actualTitle);
    }

    @Test
    public void checkTitleContacts() {
        driver.get(OTUS_WEBSITE_HOME_PAGE);
        logger.info("Сайт открыт");
        driver.findElement(By.xpath("//header//a[@href='/contacts/']")).click();
        logger.info("Открыта страница Контакты");
        logger.info("Проверяем title страницы Контакты");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Контакты | OTUS";
        assertEquals("Текст тайтла страницы Контакты не соответствует ожидаемому", expectedTitle, actualTitle);
    }


    @Test
    public void checkAdressInContacts() {
        driver.get("https://otus.ru/contacts");
        logger.info("Открыта страница Контакты");
        assertEquals( "Адрес не соответствует ожидаемому",EXPECTED_OTUS_CONTACTS_TEXT, driver.findElement(By.cssSelector("#__next > div.sc-2em8v9-0.etvoKo > div.sc-2em8v9-1.kiwtMy > div.sc-1hmcglv-0.iiFycX > div:nth-child(3) > div.c0qfa0-5.cXQVNI")).getText());
    }

    @Test
    public void checkOpenMaxScreen() {
        driver.get(OTUS_WEBSITE_HOME_PAGE);
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");
        logger.info(driver.manage().window().getSize());
    }

    @Test
    public void checkFAQ() {
        driver.get(OTUS_WEBSITE_HOME_PAGE);
        logger.info("Сайт открыт");
        driver.findElement(By.xpath("//header//a[@href='/faq/']")).click();
        logger.info("Открыта страница FAQ");
        driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.container.container-padding-bottom.js-faq > div.container__row.js-tabs > div:nth-child(2) > div:nth-child(4) > div.faq-question__question.js-faq-question-question")).click();
        String expectedProgram = "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями. Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”";
        assertEquals("Текст не соответствует ожидаемому", expectedProgram, driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.container.container-padding-bottom.js-faq > div.container__row.js-tabs > div:nth-child(2) > div:nth-child(4) > div.faq-question__answer.js-faq-answer")).getText());
        logger.info("Текст соответствует ожидаемому");

    }

    @Test
    public void checkSubscription() throws InterruptedException {
        driver.get(OTUS_WEBSITE_HOME_PAGE);
        logger.info("Сайт открыт");
        driver.findElement(By.cssSelector(".input.footer2__subscribe-input")).clear();
        logger.info("Поле email очищено");
        driver.findElement(By.cssSelector(".input.footer2__subscribe-input")).sendKeys("test@test.com");
        //WebDriverWait wait = new WebDriverWait(driver,10);
        //wait.until(ExpectedConditions.visibilityOf((WebElement) By.cssSelector(".footer2__subscribe-button.button.button_blue.button_as-input")));
        driver.findElement(By.cssSelector(".footer2__subscribe-button.button.button_blue.button_as-input")).click();
        logger.info("Нажата кнопка Подписаться");
        assertEquals( "Вы успешно подписались", driver.findElement(By.cssSelector("body > div.body-wrapper > div > footer > div > div > div.footer2__content > div > div:nth-child(3) > p.subscribe-modal__success")).getText());
        logger.info("Текст Вы успешно подписались");

    }

    @Test
    public void checkTheSearchByNumberTele2() throws InterruptedException {
        driver.get("https://msk.tele2.ru/shop/number");
        logger.info("Сайт Tele2 открыт");
        driver.findElement(By.id("searchNumber")).sendKeys("97");
        logger.info("введены 97 в поиск номера");
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //wait.until(ExpectedConditions.visibilityOf((WebElement) By.cssSelector("#root > div > div.height-holder-mobile > div > div > div > div > div.main-content > div:nth-child(1) > div > div > div > div.catalog-numbers.with-overlay.overlay-big > div > div > div.center.left-xs > a")));
        assertEquals( "Кнопка 'Показать еще' не появилась", "Показать еще", driver.findElement(By.cssSelector("#root > div > div.height-holder-mobile > div > div > div > div > div.main-content > div:nth-child(1) > div > div > div > div.catalog-numbers.with-overlay.overlay-big > div > div > div.center.left-xs > a")).getText());
        logger.info("Появилась кнопка 'Показать еще'");

    }
}


