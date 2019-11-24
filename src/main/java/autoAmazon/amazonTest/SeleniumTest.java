package autoAmazon.amazonTest;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import autoAmazon.helper.Helper;
import autoAmazon.helper.ExcelAction;
import autoAmazon.pages.AddToCartPage;
import autoAmazon.pages.DeliveryAddressPage;
import autoAmazon.pages.HomePage;
import autoAmazon.pages.LoginPage;
import autoAmazon.pages.ProductPage;
import autoAmazon.pages.SearchResultPage;

public class SeleniumTest {	
		
	WebDriver driver;
	HomePage homePage;
	SearchResultPage srPage;
	AddToCartPage atcPage;
	LoginPage loginPage;
	ProductPage pPage=null;
	DeliveryAddressPage deliveryAddPage;	
	ExcelAction excelAction;
	
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws InterruptedException {
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\eclipse-workspace\\lib\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\Softwares\\eclipse-workspace\\lib\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("Edge")){
			System.setProperty("webdriver.edge.driver","D:\\Softwares\\Executable Files\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		else {
			throw new SkipException("Invalid Browser");
		}
		driver.get("https://www.amazon.in/");		
		driver.manage().window().maximize();
		Thread.sleep(3000);
	}
	
	
		@Test
		public void deliveryOfProduct() throws InterruptedException, IOException {
			
			/*System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\eclipse-workspace\\lib\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("https://www.amazon.in/");		
			driver.manage().window().maximize();
			Thread.sleep(3000);*/

			excelAction = new ExcelAction();
			String productName = excelAction.readSheet("TestData",1,0);
			homePage = new HomePage(driver);
			homePage.searchProduct(productName);
			
			//Search Result page is displayed
			srPage = new SearchResultPage(driver);
			srPage.clickResult(productName);			
						
			String parentWinHandle = driver.getWindowHandle();
			
			Helper helper = new Helper(driver);
			helper.switchToChildWindow(parentWinHandle);
	        
    		//Selected Product page is displayed
    		pPage = new ProductPage(driver);
    		pPage.clickAddToCart();	    		
    		Thread.sleep(3000);	

	        //Add to Cart page is displayed
	        atcPage = new AddToCartPage(driver);
	        atcPage.clickProceed();
	        
	        //Login page is displayed
	        loginPage = new LoginPage(driver);
	        loginPage.verifyLoginPage();
			String userName = excelAction.readSheet("TestData",1,1);
			String password = excelAction.readSheet("TestData",1,2);
	        loginPage.enterUserCredentials(userName, password);
	        
	        //Delivery page is displayed
	        deliveryAddPage = new DeliveryAddressPage(driver);
	        String custName = excelAction.readSheet("TestData",1,3);
	        deliveryAddPage.verifyDeliveryPage(custName);	        
	        
			driver.close();
			
			//Close the parent window
	        driver.switchTo().window(parentWinHandle);
	        driver.close();
			System.out.println("Final Verification is successful");
			
			//Write Excel
			//excelAction.writeSheet("Status", 1, 1, "Pass"); //Updating the file incorrectly
		}		

}