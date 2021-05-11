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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestLesson8 {

    private Logger logger = LogManager.getLogger(TestLesson8.class);
    protected WebDriver driver;

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void End() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void check220() throws InterruptedException {
        driver.get("https://www.220-volt.ru/");
        logger.info("Сайт открыт");
        driver.manage().window().maximize();
        logger.info("Открыто окно браузера на полный экран");
        //driver.findElement(By.cssSelector("body > div.page-container > div.transition-bg-color.bg-black-0.box-relative.box-top.phspace-10.col-12.box-left.panel.box-float-none.z-index-1000 > div > div.panel-catalog.box-relative.box-inline.v-top.col-3.text-left > ul > li > ul > li:nth-child(1)")).click();
        WebElement popover = driver.findElement(By.cssSelector("body > div.page-container > div.transition-bg-color.bg-black-0.box-relative.box-top.phspace-10.col-12.box-left.panel.box-float-none.z-index-1000 > div > div.panel-catalog.box-relative.box-inline.v-top.col-3.text-left > ul > li > ul > li:nth-child(1)"));
        Actions action = new Actions(driver);
        action.moveToElement(popover).pause(400L).perform();
        //Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector("body > div.page-container > div.transition-bg-color.bg-black-0.box-relative.box-top.phspace-10.col-12.box-left.panel.box-float-none.z-index-1000 > div > div.panel-catalog.box-relative.box-inline.v-top.col-3.text-left > ul > li > ul > li:nth-child(1) > ul > li > div > ol > li:nth-child(23)")).click();
        logger.info("Открыт раздел Перфораторы");
        driver.findElement(By.cssSelector("#producer_16")).click();
        driver.findElement(By.cssSelector("#producer_473")).click();
        logger.info("Выбраы марки Makita и Зубр");
        driver.findElement(By.cssSelector("#filterSubm")).click();
        logger.info("Применен фильтра с  марками Makita и Зубр");
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        saveFile(file);
        driver.findElement(By.cssSelector("body > div.box-relative.phbspace-10.box-960.box-center.container.content.content-main.listing > div.bg-white.radius-5.box.pspace-20 > div.mhtspace-20 > div.columns.columns-page.mhtspace-20 > div.column.column-page.right > div.filter-content.mhbspace-10 > ul > li.filter-content-sort.col-20p > noindex > span > span > span.selection > span")).click();
        Thread.sleep(10000);
        driver.findElement(By.cssSelector(".select2-results__option.select2-results__option--highlighted")).click();
        Thread.sleep(10000);
        logger.info("Сортировка по Возрастанию цены");
        driver.findElement(By.cssSelector("#product-list > li:nth-child(1) > div > div.new-item-list-compare.custom-ui-compare.compare")).click();
        driver.findElement(By.cssSelector("#modal-content-placeholder > div > table > tbody > tr:nth-child(3) > td.buttons > div > a")).click();
        logger.info("Добавлен к сравнению первый Зубр");
        driver.findElement(By.cssSelector("#product-list > li:nth-child(9) > div > div.new-item-list-compare.custom-ui-compare.compare")).click();
        logger.info("Добавлен к сравнению первый Makita");
        Thread.sleep(10000);
        driver.findElement(By.cssSelector("#modal-content-placeholder > div > table > tbody > tr:nth-child(5) > td:nth-child(2) > div > a")).click();
        logger.info("Переход к сравнению");
        Thread.sleep(10000);

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
