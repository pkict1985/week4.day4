package week4.day4;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// Amazon:
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
		Actions builder = new Actions(driver);

		// 1.Load the URL https://www.amazon.in/
		driver.navigate().to("https://www.amazon.in/");
		// 2.search as oneplus 9 pro
		WebElement searchElement = driver.findElement(By.xpath("//div[@class='nav-search-field ']//input"));
		// 2a Move cursor to the search element
		builder.moveToElement(searchElement).perform();
		// 2b Enter oneplus 9 pro
		searchElement.sendKeys("oneplus 9 pro", Keys.ENTER);
		// 3.Get the price of the first product
		List<WebElement> resultList = driver.findElements(By.xpath("//div[contains(@class,'s-result-item')]"));
		String text = resultList.get(2).getText();
		String[] split = text.split("\\n");
		int price=Integer.parseInt(split[4].replaceAll("[^0-9]", ""));
		// ?54,999
		System.out.println("Price of first product searched " + price);
		
		// 4. Print the number of customer ratings for the first displayed product
		WebElement ratingElement = driver.findElement(By.xpath("(//span[@class='a-icon-alt'])[1]"));
		System.out.println("Rating for the first product is: " + ratingElement.getAttribute("innerHTML"));

		// 5. Click the first text link of the first image
		System.out.println("Text Link displayed for the first image: " + split[0]);

		// 6. Take a screen shot of the product displayed
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Now you can do whatever you need to do with it, for example copy somewhere "./snap/product.png"
		FileUtils.copyFile(scrFile, new File("C:\\TestLeaf\\Selenium\\src\\main\\java\\week4\\day4\\Snap\\product.png"));
		System.out.println("Please find the snapshop");
		// 7. Click 'Add to Cart' button
		WebElement imgElement = driver.findElement(By.xpath("(//img[@class='s-image'])[1]"));
		builder.moveToElement(imgElement).click(imgElement).perform();
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windowsList=new ArrayList<String>(windowHandles);
		driver.switchTo().window(windowsList.get(1));
		WebElement addToCartElement = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
		builder.moveToElement(addToCartElement).click(addToCartElement).perform();
		// 8. Get the cart subtotal and verify if it is correct.
		WebElement subtotalElement = driver.findElement(By.xpath("//span[@class='a-size-base-plus a-color-price a-text-bold']//span"));
		Thread.sleep(3000);
		//System.out.println(subtotalElement.isDisplayed());
		String  strSub= subtotalElement.getAttribute("innerHTML");
		//String resultStrSub = strSub.replaceAll("[^0-9]","");
		int cartPrice=Integer.parseInt(strSub.replaceAll("[^0-9]","").substring(0,strSub.replaceAll("[^0-9]","").length()-2));
		System.out.println("Subtotal is " + cartPrice); 
		if(price==cartPrice)
			System.out.println("Cart subtotal is valid amount");
		// 9.close the browser
		driver.quit();
	}
}
