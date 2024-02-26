import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
	public static void main(String[] args) throws InterruptedException {
		
		ChromeDriver driver = new ChromeDriver();

		// Launching website

		//1
		driver.get("https://demowebshop.tricentis.com/");
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.pollingEvery(Duration.ofMillis(200));
		
		
		//2
		driver.findElement(By.xpath("(//*/li[@class='inactive'])[7]/*[1]")).click();
		
		//3
		driver.findElement(By.xpath("//*/div[@class='item-box'][.//span[@class='price actual-price' and number(.) > 99]]//div[@class='picture']/*[1]")).click();
		
		//4
		WebElement recipientNameField = driver.findElement(By.xpath("//*/input[@class='recipient-name']"));
		recipientNameField.sendKeys("Krabas");
		
		WebElement senderNameField = driver.findElement(By.xpath("//*/input[@class='sender-name']"));
		senderNameField.sendKeys("Reivas");
		
		//5
		WebElement qtyNameField = driver.findElement(By.name("addtocart_4.EnteredQuantity"));
		qtyNameField.clear();
		qtyNameField.sendKeys("5000");
		
		//6
		driver.findElement(By.id("add-to-cart-button-4")).click();
		
		//7 !!!!!
		Thread.sleep(500);
		driver.findElement(By.id("add-to-wishlist-button-4")).click();
		
		//8
		driver.findElement(By.xpath("(//*/li[@class='inactive'])[6]/*[1]")).click();
		
		//9
		driver.findElement(By.xpath("//*/h2[@class='product-title' and .//*[@href='/create-it-yourself-jewelry']]")).click();
		
		//10
		Select dropdown = new Select(driver.findElement(By.name("product_attribute_71_9_15")));
		dropdown.selectByVisibleText("Silver (1 mm)");
		WebElement lengthField = driver.findElement(By.id("product_attribute_71_10_16"));
		lengthField.sendKeys("80");

		driver.findElement(By.id("product_attribute_71_11_17_50")).click();

		//11
		
		WebElement qtyJewleryField = driver.findElement(By.name("addtocart_71.EnteredQuantity"));
		qtyJewleryField.clear();
		qtyJewleryField.sendKeys("26");
		
		//12
		driver.findElement(By.id("add-to-cart-button-71")).click();
		
		//13 !!!!!
		Thread.sleep(500);
		driver.findElement(By.id("add-to-wishlist-button-71")).click();
			
		//14
		Thread.sleep(500);
		driver.findElement(By.xpath("//*/a[@href='/wishlist']/*[1]")).click();

		//15
		List<WebElement> items = driver.findElements(By.xpath("//*[@class=\"add-to-cart\"]"));
		for (WebElement item : items) {
			item.click();
		}

		//16
		driver.findElement(By.name("addtocartbutton")).click();
		
		//17
		driver.findElement(By.xpath("//*[@class=\"cart-label\"]")).click();
		
		String subtotalText = driver.findElement(By.xpath("//*[@class=\"cart-total-left\" and .//*[@class=\"nobr\" and text()=\"Sub-Total:\"]]/following-sibling::td//*[@class=\"product-price\"]")).getText();
		Float subtotal = Float.parseFloat(subtotalText.substring(0));
		if (subtotal == 1002600.00) {
			System.out.println("Subtotal is correct. Subtotal should be 1002600.00 and is " + subtotal + ".");
		} else {
			System.out.println("Subtotal is incorrect. Should be 1002600.00, but is " + subtotal);
		}
		
		//end
		driver.close();
		return;
	}
}
