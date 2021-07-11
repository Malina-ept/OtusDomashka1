import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class TestLesson8 {

    private Logger logger = LogManager.getLogger(TestLesson8.class);
    protected WebDriver driver;


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

    @Test
    public void compareTwoProductsInCitylink() throws InterruptedException {

        driver.get("https://www.citilink.ru/");
        logger.info("Сайт открыт");
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");

        logger.info("Переходим в раздел Бытовая техника-Стиральные машины");
        driver.findElement(By.xpath("//div[@class='MainHeader__catalog']")).click();
        logger.info("Открыто меню Каталог товаров");

        WebElement openHomeAppliances = driver.findElement(By.xpath("//a[contains(@data-title, 'Бытовая техника')]"));
        Actions action = new Actions(driver);
        action.moveToElement(openHomeAppliances).pause(800L).perform();

        driver.findElement(By.xpath("//div[@class='CatalogMenu__subcategory-item'][3]")).click();
        logger.info("Открыта страница Стиральные машины");

        logger.info("Выберем на странице чекбокс BOSCH");
        WebElement boshCheckbox = driver.findElement(By.xpath("//input[@id='bosch']"));

        JavascriptExecutor ex=(JavascriptExecutor)driver;
        ex.executeScript("arguments[0].click()", boshCheckbox);
        logger.info("Чекбокс BOSCH выбран");


        logger.info("Выберем на странице чекбокс Electrolux");
        WebElement electroluxCheckbox = driver.findElement(By.xpath("//input[@id='electrolux']"));
        ex.executeScript("arguments[0].click()", electroluxCheckbox);
        logger.info("Чекбокс Electrolux выбран");

        WebElement sortPrice = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-alias='price']")));
        logger.info("Отсортируем полученные результаты по цене");
        sortPrice.click();

        logger.info("Выберем первую в списке BOSCH");
        WebElement boshCompareBtn = driver.findElement(By.xpath("(//div[contains(@data-params, 'BOSCH' )]//label)[1]"));
        ex.executeScript("arguments[0].click()", boshCompareBtn);

        logger.info("А теперь Выберем первую в списке ELECTROLUX");
        WebElement electroluxCompareBtn = driver.findElement(By.xpath("(//div[contains(@data-params, 'ELECTROLUX' )]//label)[1]"));
        ex.executeScript("arguments[0].click()", electroluxCompareBtn);
        logger.info("Выбраны для сравнения первая BOSCH и первая ELECTROLUX");

        logger.info("Перейдём на страницу сравнения");
        driver.findElement(By.xpath("//div[@class='HeaderMenu__buttons  HeaderMenu__buttons_compare']")).click();
        logger.info("Открыта страница сравнения");
        String actualName = driver.findElement(By.cssSelector("body > div.MainWrapper > div.MainLayout.js--MainLayout.HeaderFixer > main > div > div > div.Compare.js--Compare > div.Compare__content.js--Compare__content > div.Compare__content-wrap > div.Compare__content-main-wrap.js--Compare__content-main-wrap > div > div.Compare__content-head.js--Compare__content-head > div.Compare__product-table.js--Compare__product-table > div.Compare__product-row.Compare__product-name-render.js--Compare__product-name-render")).getText();
        //System.out.println(actualName);
        assertTrue(actualName.contains("ELECTROLUX"));
        assertTrue(actualName.contains("BOSCH"));
        logger.info("На странице сравнения есть ELECTROLUX и BOSCH");
    }

}
