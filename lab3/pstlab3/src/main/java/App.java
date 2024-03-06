import java.time.Duration;
import java.util.List;

import org.checkerframework.checker.units.qual.m;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App 
{
    public static void main( String[] args )
    {
        ChromeDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000), Duration.ofMillis(1000));

        // wait.IgnoreExceptionTypes(typeof(NoSuchElementException));
        // 1 Atsidaryti https://demoqa.com/
        driver.get("https://demoqa.com/");
        // cookies consent
        driver.findElement(By.xpath("//*[@class=\"fc-button fc-cta-consent fc-primary-button\"]")).click();
                
        // 2 Pasirinkti "Widgets" kortelę
        driver.findElement(By.xpath("//*[@class=\"card mt-4 top-card\" and .//h5/text()=\"Widgets\"]")).click();

        // 3 Pasirinkti meniu punkta "Progress Bar"
        driver.findElement(By.xpath("//*[@id=\"item-4\" and .//span/text()=\"Progress Bar\"]")).click();

        // 4 Spausti mygtuka "Start"
        driver.findElement(By.xpath("//button[text()=\"Start\"]")).click();

        // 5. Sulaukti, kol bus 100% in paspausti "Reset"
        wait.until(wait_driver -> wait_driver.findElement(By.xpath("//div[@role=\"progressbar\" and @aria-valuenow=\"100\"]")));
        driver.findElement(By.xpath("//button[text()=\"Reset\"]")).click();
        // 6. Isitikinti, kad progreso eilutė tuscia (0%).
        WebElement bar_empty = wait.until(wait_driver -> wait_driver.findElement(By.xpath("//div[@role=\"progressbar\" and @aria-valuenow=\"0\"]")));
        if (bar_empty == null) {
            System.out.println("Bar reset failed");
        } else {
            System.out.println("Bar reset successfull");
        }

        driver.close();
        return;
    }
}
