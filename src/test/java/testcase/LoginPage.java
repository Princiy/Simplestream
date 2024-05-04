package testcase;

import base.BaseTest;
import static org.testng.Assert.assertEquals;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;

public class LoginPage extends BaseTest {
	
	@Test(priority=1)
	public void loginPageLoadedCompletely() {
		
		//Waiting for login credential web elements to load and asserting login text on the page
		
		String expectedText = "Enter your sign in info below";
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@class='login']/div[1]/div[1]//input")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@class='login']/div[2]//input")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='field']//div[1]//button")));		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String actualText = driver.findElement(AppiumBy.xpath("//p[@class='mt-0']")).getText();
		
		assertEquals(actualText, expectedText);
		
	}
	
	//Validating login functionality with invalid credentials
	@Test(dataProvider = "InvalidLoginData", priority=2)
	public void loginTest(String username, String password, String scenario) throws Exception {
		
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	WebElement emailField = driver.findElement(AppiumBy.xpath("//form[@class='login']/div[1]/div[1]//input"));
	emailField.click();
	emailField.clear();
	emailField.sendKeys(username);
	
	WebElement passwordField = driver.findElement(AppiumBy.xpath("//form[@class='login']/div[2]//input"));
	passwordField.click();
	passwordField.clear();
	passwordField.sendKeys(password);
	
	WebElement signInButton = driver.findElement(AppiumBy.xpath("//div[@class='field']//div[1]//button"));
	signInButton.click();
	
	if(scenario.equals("Correctemail")){
		String errorMessage = driver.findElement(AppiumBy.xpath("//*[@id='main']/div/div/div/ul/li/text()[1]")).getText();
		Assert.assertEquals(errorMessage, "Error: The password you entered for the email address");
	}
	
	else if(scenario.equals("Correctpassword")) {
		String errorMessage = driver.findElement(AppiumBy.xpath("//*[@id='main']/div/div/div/ul/li")).getText();
		Assert.assertEquals(errorMessage, "Unknown email address. Check again or try your username");
	}
	
	
	else if (scenario.equals("Incorrectemailandpassword")) {
		String errorMessage = driver.findElement(AppiumBy.xpath("//*[@id='main']/div/div/div/ul/li")).getText();
		Assert.assertEquals(errorMessage, "Unknown email address. Check again or try your username");
	}
	
	else if (scenario.equals("Blankpassword")){
		String errorMessage = driver.findElement(AppiumBy.xpath("//*[@id='main']/div/div/div/ul/li")).getText();
		Assert.assertEquals(errorMessage, "The password field is required.");
		
	}
	
	else if (scenario.equals("Blankemail")){
		String errorMessage = driver.findElement(AppiumBy.xpath("//*[@id='main']/div/div/div/ul/li")).getText();
		Assert.assertEquals(errorMessage, "The username field is required.");
	
	}
	
	driver.hideKeyboard();
	
	}
	
	@Test(priority=3)
	public void validLoginTest() {
		
		String expectedHomePageText = "My Account";
		
		//Locating Email Address field and entering valid email id
		WebElement emailAddress = driver.findElement(AppiumBy.xpath("//form[@class='login']/div[1]/div[1]//input"));
		emailAddress.click();
		emailAddress.clear();
		emailAddress.sendKeys("tester1@simplestream.com");
		
		//Locating Password field and entering valid password
		WebElement passwordField = driver.findElement(AppiumBy.xpath("//form[@class='login']/div[2]//input"));
		passwordField.click();
		passwordField.clear();
		passwordField.sendKeys("TestLogin");
		WebElement loginButton = driver.findElement(AppiumBy.accessibilityId("LOGIN"));
		loginButton.click();

		//Locating sign in button and tapping it to log into the application
		WebElement signInButton = driver.findElement(AppiumBy.xpath("//div[@class='field']//div[1]//button"));
		signInButton.click();
		
		//Validate successful login
		WebElement actualHomePageText = driver.findElement(AppiumBy.xpath("//*[@id='container']/div[3]/nav/div[1]/div[2]/div[1]/span/a"));
		Assert.assertEquals(actualHomePageText, expectedHomePageText);
		
	}
	
	@DataProvider(name = "InvalidLoginData")
	public String[][] getData() {
		
	String loginData[][] = { 
	//Correct email and incorrect password
	{ "tester1@simplestream.com", "abc123", "Correctemail" },
	
	//Incorrect email and correct password
	{"1@simplestream.com", "TestLogin", "Correctpassword"},
	
	//Incorrect email and incorrect password
	{"1@simplestream.com", "abc123", "Incorrectemailandpassword"},
	
	//correct email and blank password
	{"tester1@simplestream.com", "", "Blankpassword"},
	
	//Blank email and correct password
		{"", "TestLogin", "Blankemail"},
	};

	return loginData;
	}
	
	

}
