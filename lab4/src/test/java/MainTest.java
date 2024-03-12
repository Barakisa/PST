import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainTest {
    
    public static User user;
    ChromeDriver driver;

    @BeforeClass
    public static void createUser() {
        ChromeDriver driver2 = create_driver();
        user = new User("1");
        driver2.get("https://demowebshop.tricentis.com/");
        driver2.manage().window().maximize();
        driver2.findElement(By.xpath("//a[@href=\"/login\"]")).click();

        driver2.quit();
    }

    @Before
    public void beforeEach() {
        driver = create_driver();
        System.out.println("Before");
    }
    @After
    public void after() {
        driver.quit();
        System.out.println("After");
    }


    @Test
    public void testHelloWorld() {
        System.out.println("Hello world!");
    }

    private static ChromeDriver create_driver(){
        ChromeDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000), Duration.ofMillis(1000));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        return driver;
    }
}