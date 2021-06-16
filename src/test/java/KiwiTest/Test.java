package KiwiTest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Iterator;
import java.util.Set;

public class Test {


    public static void main(String[] args) throws InterruptedException {

        String BASE_URL = 	"https://www.kiwi.com/en/";
        System.setProperty("webdriver.chrome.driver", "C:\\work\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        SoftAssert softAssert;
        softAssert = new SoftAssert();

// Stage. 1: Navigate to https://www.kiwi.com/en/
        driver.get(BASE_URL);

// Stage. 1: Agree to cookie consent
        WebElement AcceptButton = driver.findElement(By.xpath("//p[contains(text(),'Accept')]"));
        AcceptButton.click();

// Stage 4: Rewrite the test making the flight search for 3 Adult passengers
        WebElement PassengerButton = driver.findElement(By.xpath("//div[@data-test = 'PassengersField']/div/div/button"));
        PassengerButton.click();
        WebElement AddPassenger = driver.findElement(By.xpath("//button[@aria-label = 'increment']"));
        for(int i=1; i<3; i++) {
            AddPassenger.click();
        }

// Stage. 1: Search for a flight to London from your default departure (you don’t have to set flight dates)
        WebElement Departure = driver.findElement(By.xpath("//div[@data-test = 'PlacePickerInput-destination']/input"));
        Departure.sendKeys("London");
        Thread.sleep(2000l);
        WebElement AddPlace = driver.findElement(By.xpath("//button[@aria-label = 'Add place']"));
        AddPlace.click();

// Stage. 1: Set currency to USD
        WebElement Currency = driver.findElement(By.xpath("//div[@data-test = 'Currency-Open']"));
        Currency.click();
        Thread.sleep(2000l);
        WebElement USD = driver.findElement(By.xpath("//span[contains(text(), 'USD')]"));
        USD.click();

//	Stage. 1: Hit “Book” on the first result
        WebElement Search = driver.findElement(By.xpath("//a[@data-test = 'LandingSearchButton']"));
        Search.click();
        Thread.sleep(3000l);

//  Switch to booking page

        Set<String> handles = driver.getWindowHandles();
        Iterator<String> it = handles.iterator();
        String parentWindowId = it.next();
        String childWindowId = it.next();
        driver.switchTo().window(parentWindowId);

        System.out.println(parentWindowId + childWindowId );
        System.out.println(driver.getTitle());

//  Stage.4 Rewrite the test making the flight search for 3 Adult passengers, and assert same passenger count on booking page
        System.out.println(driver.findElement(By.xpath("//option[@selected = 'selected']")).getAttribute("value"));
        Assert.assertEquals(driver.findElement(By.xpath("//option[@selected = 'selected']")).getAttribute("value"), "3");

// Stage 1. Assert that the currency is “USD” on booking page
//Stage.3 Suggest multiple ways to Assert that the currency is “USD” on booking page
        System.out.println(driver.findElement(By.xpath("//span[@aria-hidden = 'true']")).getText());
        softAssert.assertTrue(driver.findElement(By.xpath("//span[@aria-hidden = 'true']")).getText().contains("USD"));
        softAssert.assertFalse(driver.findElement(By.xpath("//span[@aria-hidden = 'true']")).getText().contains("USD"));
        softAssert.assertEquals(driver.findElement(By.xpath("//span[@aria-hidden = 'true']")).getText(), "USD");
        softAssert.assertNotEquals(driver.findElement(By.xpath("//span[@aria-hidden = 'true']")).getText(), "USD");

        driver.quit();

    }

}
