import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class NewTestOtus {

    private Logger logger = LogManager.getLogger(NewTestOtus.class);
    protected static WebDriver driver;

    @Before
    public void StartUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @After
    public void End(){
        if (driver!=null)
            driver.quit();
    }

    @Test
    public void checkTitle() {
        logger.info("Тест стaрт");
        driver.get("https://otus.ru");
        logger.info("Сайт открыт");
        logger.info("Проверяем title страницы");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        assertEquals(expectedTitle, actualTitle);
    }


}
