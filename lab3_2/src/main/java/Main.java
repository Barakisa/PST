import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.failsafe.internal.util.Assert;



public class Main {
    public static void main(String[] args) {
        try{
            System.out.println("Hello world!");

            ChromeDriver driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000), Duration.ofMillis(1000));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

            // 1. Atsidaryti https://demoqa.com/
            driver.get("https://demoqa.com/");
            driver.manage().window().maximize();
            // cookies consent
            driver.findElement(By.xpath("//*[@class=\"fc-button fc-cta-consent fc-primary-button\"]")).click();

            // 2. Pasirinkti "Elements" kortele,
            driver.findElement(By.xpath("//div[@class=\"card mt-4 top-card\" and .//h5[text()=\"Elements\"]]")).click();

            // 3. Pasirinkti meniu punkta "Web Tables"
            driver.findElement(By.xpath("//span[text()=\"Web Tables\"]")).click();
            js.executeScript("window.scrollBy(0, 500)");

            // 4. Prideti pakankamai elementų, kad atsirastų antras puslapis puslapiavime
            System.out.println(driver.findElement(By.xpath("//span[@class=\"-totalPages\"]")).getText());
            do {
                addRecord(driver);
            } while (driver.findElement(By.xpath("//span[@class=\"-totalPages\"]")).getText().equals("1"));

            // 5. Pasirinkti antra puslapi paspaudus "Next"
            driver.findElement(By.xpath("//button[text()=\"Next\"]")).click();

            // 6. Ištrinti elementa antrajame puslapyje
            //save pagination for test 7
            String currentPageBeforeDelete = driver.findElement(By.xpath("//input[@aria-label=\"jump to page\"]")).getAttribute("value");
            String maxPageBeofreDelete = driver.findElement(By.xpath("//span[@class=\"-totalPages\"]")).getText();
            
            // scroll to side
            WebElement scrollTable = driver.findElement(By.xpath("//div[@class=\"rt-table\"]"));
            js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollTable);
            
            driver.findElement(By.xpath("//span[@title=\"Delete\"][1]")).click();

            // 7. Įsitikinti, kad automatiškai puslapiavimas perkeliamas į pirmąjį puslapį ir kad puslapiu skaičius
            
            String currentPageNew = driver.findElement(By.xpath("//input[@aria-label=\"jump to page\"]")).getAttribute("value");
            String maxPageNew = driver.findElement(By.xpath("//span[@class=\"-totalPages\"]")).getText();
            
            Assert.isTrue(!maxPageNew.equals(maxPageBeofreDelete), "Max page nesumazeja");
            Assert.isTrue(!currentPageNew.equals(currentPageBeforeDelete), "Current page nesumazeja");

            
            // driver close
            driver.close();

            return;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
    }

    public static void addRecord(ChromeDriver driver) {
        // ad record click
        driver.findElement(By.id("addNewRecordButton")).click();
        // fill up fields
        driver.findElement(By.xpath("//input[@id=\"firstName\"]")).sendKeys("FirstName");
        driver.findElement(By.xpath("//input[@id=\"lastName\"]")).sendKeys("LastName");
        driver.findElement(By.xpath("//input[@id=\"userEmail\"]")).sendKeys("email@email.email");
        driver.findElement(By.xpath("//input[@id=\"age\"]")).sendKeys("18");
        driver.findElement(By.xpath("//input[@id=\"salary\"]")).sendKeys("15000");
        driver.findElement(By.xpath("//input[@id=\"department\"]")).sendKeys("vroom vroom");
        // submit
        driver.findElement(By.id("submit")).click();
        

        
    }
}