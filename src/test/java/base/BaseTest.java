package base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.*;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {

	public static AndroidDriver driver;
	public static IOSDriver driver1;
	public static AppiumDriverLocalService service;
	public static AppiumServiceBuilder builder;

	String node_js_path = "C:\\Program Files\\nodejs\\node.exe";
	String node_js_main_path = "C:\\Users\\PrinciyShrivastava\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

	@BeforeTest
	public void setUp() throws MalformedURLException, InterruptedException {

		// start Appium server
		builder = new AppiumServiceBuilder();
		builder.withAppiumJS(new File(node_js_main_path)).usingDriverExecutable(new File(node_js_path)).usingPort(4723)
				.withArgument(GeneralServerFlag.LOCAL_TIMEZONE).withIPAddress("127.0.0.1");

		service = AppiumDriverLocalService.buildService(builder);
		service.start();

		// create capabilities for Android device
		UiAutomator2Options optionsAndroid = new UiAutomator2Options();
		optionsAndroid.setDeviceName("moto g23");
		optionsAndroid.setApp(System.getProperty("user.dir") + "\\src\\test\\java\\Resources\\apkfile\\v0.1.6.apk");
		

		// create object for Android device
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), optionsAndroid);
		
		// create capabilities for IOS device
		XCUITestOptions optionsIOS = new XCUITestOptions();
		optionsIOS.setDeviceName("iPhone SE");
		optionsIOS.setApp(System.getProperty("user.dir") + "\\src\\test\\java\\Resources\\ipafile\\v0.1.6.ipa");
		
		// create object for IOS device
		driver1 = new IOSDriver(new URL("http://127.0.0.1:4723/"), optionsIOS);

	}

	@AfterTest
	public void tearDown() {

		driver.quit();
		service.stop();

	}
}
