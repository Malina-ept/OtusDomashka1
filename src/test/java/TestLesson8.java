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
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");
        logger.info("Переходим в раздел Бытовая техника-Стиральные машины");
        driver.findElement(By.xpath("//div[@class='MainHeader__catalog']")).click();

        WebElement openHomeAppliances = driver.findElement(By.xpath("//svg[@class='CatalogMenu__category-icon Icon']"));
        Actions action = new Actions(driver);
        action.moveToElement(openHomeAppliances).pause(800L).perform();

        driver.findElement(By.xpath("//div[@class='CatalogMenu__subcategory-item'][3]")).click();
        logger.info("Открыта страница Стиральные машины");

        logger.info("Выберем на странице чекбокс BOSCH");
        driver.findElement(By.xpath("//input[@id='1907_325bosch']")).click();
        //Thread.sleep(10000);
        //By checkboxLG = new WebElement(By.xpath("//input[@id='1907_325lg']"));
        logger.info("Чекбокс BOSCH выбран");

        //WebElement checkboxLG = (WebElement) By.xpath("//input[@id='1907_325lg']");
        logger.info("Выберем на странице чекбокс Electrolux");
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='1907_325electrolux']")));
        driver.findElement(By.xpath("//input[@id='1907_325electrolux']")).click();
        logger.info("Чекбокс Electrolux выбран");
        //Thread.sleep(10000);

        //WebElement sortPrice = (WebElement) By.xpath("//div[@data-alias='price']");
        logger.info("Отсортируем полученные результаты по цене");
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-alias='price']")));
        driver.findElement(By.xpath("//div[@data-alias='price']")).click();


        logger.info("Выберем первую в списке BOSCH");
        driver.findElement(By.xpath("(//*[@class='ProductGroupList js--ProductGroupList']//div[contains(@data-params, 'BOSCH')]//*[contains(@class, 'ProductCardButton__button-add')])[1]")).click();
        logger.info("А теперь Выберем первую в списке Electrolux");
        driver.findElement(By.xpath("(//*[@class='ProductGroupList js--ProductGroupList']//div[contains(@data-params, 'ELECTROLUX')]//*[contains(@class, 'ProductCardButton__button-add')])[1]")).click();
        logger.info("Выбраны для сравнения первая BOSCH и первая Electrolux");

        logger.info("Перейдём на страницу сравнения");
        driver.findElement(By.xpath("//div[@class='HeaderMenu__buttons  HeaderMenu__buttons_compare']")).click();
        logger.info("Открыта страница сравнения");

        Thread.sleep(1000);

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
