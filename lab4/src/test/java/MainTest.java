import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;

public class MainTest {

    public static User user;
    ChromeDriver driver;

    @BeforeClass
    public static void createUser() {
        ChromeDriver driver2 = createChromeDriver();

        // 1. Atsidaryti tinklalapį https://demowebshop.tricentis.com/
        driver2.get("https://demowebshop.tricentis.com/");
        driver2.manage().window().maximize();

        // 2. Spausti 'Log in'
        driver2.findElement(By.xpath("//a[@href=\"/login\"]")).click();

        // 3. Spausti 'Register' skiltyje 'New Customer'
        driver2.findElement(By.xpath("//input[@value=\"Register\"]")).click();

        // 4. Užpildyti registracijos formos laukus
        /// 4.0 Create new user info and save login credentials to csv
        File usersFile = new File("./src/test/java/users.csv");
        String newId = getNewId(usersFile);
        user = new User(newId);
        saveUserToFile(user, usersFile);

        /// 4.1 Gender
        if (user.getGender().equals("Male")) {
            driver2.findElement(By.xpath("//input[@id=\"gender-male\"]")).click();
        } else if (user.getGender().equals("Female")) {
            driver2.findElement(By.xpath("//input[@id=\"gender-female\"]")).click();
        } else {
            driver2.findElement(By.xpath("//div[@class=\"gender\"][1]")).click();
        }
        /// 4.2 First name
        driver2.findElement(By.id("FirstName")).sendKeys(user.getName());
        /// 4.3 Last name
        driver2.findElement(By.id("LastName")).sendKeys(user.getSurname());
        /// 4.4 Email
        driver2.findElement(By.id("Email")).sendKeys(user.getEmail());
        /// 4.5 Password
        driver2.findElement(By.id("Password")).sendKeys(user.getPassword());
        /// 4.6 Confirm password
        driver2.findElement(By.id("ConfirmPassword")).sendKeys(user.getPassword());

        // 5. Spausti 'Register'
        driver2.findElement(By.id("register-button")).click();
        // 6. Spausti 'Continue'
        driver2.findElement(By.xpath("//input[@value=\"Continue\"]"));

        // Driver quit
        driver2.quit();
    }

    @Before
    public void createDriver() {
        driver = createChromeDriver();
        System.out.println("Before");
    }

    @After
    public void closeChromeDriver() {
        driver.quit();
        System.out.println("After");
    }

    @Test
    public void testHelloWorld() {
        System.out.println("Hello world!");
    }

    @Test
    public void data1ToCartTest() {
        try {
            // 1. Atsidaryti tinklalapį https://demowebshop.tricentis.com/
            driver.get("https://demowebshop.tricentis.com/");
            driver.manage().window().maximize();

            // 2. Spausti 'Log in'
            driver.findElement(By.xpath("//a[@href=\"/login\"]")).click();

            // 3. Užpildyti 'Email:', 'Password:' ir spausti 'Log in'
            driver.findElement(By.id("Email")).sendKeys(user.getEmail());
            driver.findElement(By.id("Password")).sendKeys(user.getPassword());
            driver.findElement(By.xpath("//input[@value=\"Log in\"]")).click();

            // 4. Šoniniame meniu pasirinkti 'Digital downloads'
            driver.findElement(By.xpath("//div[@class=\"leftside-3\"]//*[@href=\"/digital-downloads\"]")).click();

            // 5. Pridėti į krepšelį prekes nuskaitant tekstinį failą
            /// data1.txt
            List<String> products = getDataFromFile("data1.txt");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By cartQtyLocator = By.xpath("//span[@class='cart-qty']");

            for (String product : products) {
                String initialCartQty = driver.findElement(cartQtyLocator).getText();
                driver.findElement(By.xpath("//div[@class='details' and .//*[text()='"
                        + product + "']]//input[@value='Add to cart']")).click();

                wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(cartQtyLocator, initialCartQty)));
            }

            // 6. Atsidaryti 'Shopping cart'
            driver.findElement(By.xpath("//span[text()=\"Shopping cart\"]")).click();

            // 7. Paspausti 'I agree' varnelę ir mygtuką 'Checkout'
            driver.findElement(By.id("termsofservice")).click();
            driver.findElement(By.id("checkout")).click();

            // 8. 'Billing Address' pasirinkti jau esantį adresą arba supildyti naujo
            // adreso, spausti 'Continue'
            /// 8.1 'Country' choose 'Lithuania'
            WebElement dropdown = driver.findElement(By.id("BillingNewAddress_CountryId"));
            Select select = new Select(dropdown);
            select.selectByVisibleText("Lithuania");
            /// 8.2 'City' įvesti 'Vilnius'
            driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Vilnius");
            /// 8.3 'Address 1' įvesti 'Gedimino pr. 1'
            driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Gedimino pr. 1");
            /// 8.4 'Zip / postal code' įvesti 'LT-01103'
            driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("LT-01103");
            /// 8.5 'Phone number' įvesti '+37060000000'
            driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("+37060000000");
            /// 8.6 'Continue' click
            driver.findElement(By.xpath("//li[@id=\"opc-billing\"]//input[@type=\"button\" and @value=\"Continue\"]")).click();

            // 9. 'Payment Method' spausti 'Continue'
            driver.findElement(By.xpath("//li[@id=\"opc-payment_method\"]//input[@type=\"button\" and @value=\"Continue\"]")).click();

            // 10. 'Payment Information' spausti 'Continue'
            driver.findElement(By.xpath("//li[@id=\"opc-payment_info\"]//input[@type=\"button\" and @value=\"Continue\"]")).click();

            // 11. 'Confirm Order' spausti 'Confirm'
            driver.findElement(By.xpath("//li[@id=\"opc-confirm_order\"]//input[@type=\"button\" and @value=\"Confirm\"]")).click();

            // 12. Įsitikinti, kad užsakymas sėkmingai užskaitytas.
            //// TODO dynamically track, what's in data files, and assert that numbers are correct
            //// right now, it's hardcoded 3 of 3rd Album
            String cartQty = driver.findElement(By.xpath("//tr[@class='cart-item-row' and  .//a[@class='product-name' and text()='3rd Album']]//td[@class='qty nobr']/span[2]")).getText();
            assert cartQty.equals("3");

        } catch (Exception e) {
            System.out.println(":(");
        }
    }

    @Test
    public void data2ToCartTest() {

    }

    private static String getNewId(File usersFile) {
        int nextId = 0;
        try (Scanner scanner = new Scanner(usersFile)) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                nextId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextId + "";
    }

    private static void saveUserToFile(User user, File usersFile) {
        try {
            FileWriter outputfile = new FileWriter(usersFile, true);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] data = { user.getId(), user.getEmail(), user.getPassword() };
            writer.writeNext(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getDataFromFile(String filename) {
        List<String> products = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                products.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static ChromeDriver createChromeDriver() {
        ChromeDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        return driver;
    }
}