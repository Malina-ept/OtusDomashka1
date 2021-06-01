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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLesson8 {

    private Logger logger = LogManager.getLogger(TestLesson8.class);
    protected WebDriver driver;
    private Instant wait;

    //@FindBy(xpath = "//input[@id='1907_325lg']")
    //private WebElement checkboxLG;

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
        WebDriverWait wait = new WebDriverWait(driver,10);

        driver.get("https://www.citilink.ru/");
        logger.info("Сайт открыт");
        //driver.manage().window().maximize();
        //logger.info("Открыто окно браузера на полный экран");

        driver.manage().window().setSize(new Dimension(1900,800));
        logger.info("Открыто окно браузера 1900*800");

        logger.info("Переходим в раздел Бытовая техника-Стиральные машины");
        driver.findElement(By.xpath("//div[@class='MainHeader__catalog']")).click();
        logger.info("Открыто меню Каталог товаров");

        WebElement openHomeAppliances = driver.findElement(By.xpath("//a[contains(@data-title, 'Бытовая техника')]"));
        Actions action = new Actions(driver);
        action.moveToElement(openHomeAppliances).pause(800L).perform();

        driver.findElement(By.xpath("//div[@class='CatalogMenu__subcategory-item'][3]")).click();
        logger.info("Открыта страница Стиральные машины");

        logger.info("Выберем на странице чекбокс BOSCH");
        driver.findElement(By.xpath("//input[@id='1907_325bosch']")).click();
        logger.info("Чекбокс BOSCH выбран");
        logger.info("Выберем на странице чекбокс Electrolux");
        driver.findElement(By.xpath("//input[@id='1907_325electrolux']")).click();
        logger.info("Чекбокс Electrolux выбран");

        WebElement sortPrice = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-alias='price']")));
        logger.info("Отсортируем полученные результаты по цене");
        sortPrice.click();

        //После сортировки нельзя выбрать нужные селекторы, они перехвачены и некликабельны, хз как это решить
        //Отдельно тест с этими элементами работает корректно, а как собрать вместе(переключить фокус на страницу) - хз
        logger.info("Выберем первую в списке BOSCH");
        driver.findElement(By.xpath("(//div[contains(@data-params, 'BOSCH' )]//label)[1]")).click();
        logger.info("А теперь Выберем первую в списке ELECTROLUX");
        driver.findElement(By.xpath("(//div[contains(@data-params, 'ELECTROLUX' )]//label)[1]")).click();
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

    @Test
    public void sortPrice() throws InterruptedException {

        driver.get("https://www.citilink.ru/catalog/stiralnye-mashiny/?f=discount.any%2C1907_325bosch%2C1907_325electrolux&pf=discount.any%2C1907_325bosch&sorting=relevance_asc");
        logger.info("Сайт открыт");
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");
        logger.info("Отсортируем полученные результаты по цене");
        driver.findElement(By.xpath("//div[@data-alias='price']")).click();
        logger.info("Результаты отсортированы по цене");
    }

        @Test
    public void checkСomparisons() throws InterruptedException {
        //WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get("https://www.citilink.ru/catalog/stiralnye-mashiny/?f=discount.any%2C1907_325bosch%2C1907_325electrolux&pf=discount.any%2C1907_325bosch&sorting=relevance_asc");
        logger.info("Сайт открыт");
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");
        logger.info("Выберем первую в списке BOSCH");
        driver.findElement(By.xpath("(//div[contains(@data-params, 'BOSCH' )]//label)[1]")).click();
        logger.info("А теперь Выберем первую в списке ELECTROLUX");
        driver.findElement(By.xpath("(//div[contains(@data-params, 'ELECTROLUX' )]//label)[1]")).click();
        logger.info("Выбраны для сравнения первая BOSCH и первая ELECTROLUX");
        logger.info("Перейдём на страницу сравнения");
        driver.findElement(By.xpath("//div[@class='HeaderMenu__buttons  HeaderMenu__buttons_compare']")).click();
        logger.info("Открыта страница сравнения");
        String actualNameElectrolux = driver.findElement(By.xpath("//div[@class=' Compare__product-cell js--Compare__product-cell Compare__product-cell_name']//a")).getText();
        System.out.println(actualNameElectrolux);
        String actualNameBosch = driver.findElement(By.xpath("(//div[@class=' Compare__product-cell js--Compare__product-cell Compare__product-cell_name']//a)[2]")).getText();
        System.out.println(actualNameBosch);
        assertTrue(actualNameElectrolux.contains("ELECTROLUX"));
        assertTrue(actualNameBosch.contains("BOSCH"));
        logger.info("На странице сравнения есть ELECTROLUX и BOSCH");




    }

        private void saveFile(File data) {
        String fileName = "target/" + System.currentTimeMillis() + ".png";
        try {
            FileUtils.copyFile(data, new File(fileName));
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
