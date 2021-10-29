package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AvicSmokeTests {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkThatUrlContainsSearchWorld() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("jbl");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        assertTrue(driver.getCurrentUrl().contains("query=jbl"));
    }

    @Test(priority = 2)
    public void checkElementsAmountOnSearchPage() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("iPhone 11");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='prod-cart__descr']"));
        assertEquals(elementList.size(), 12);
    }



    @Test(priority = 3)
    public void check3() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("jbl");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        assertTrue(driver.getCurrentUrl().contains("query=jbl"));
    }

    @Test(priority = 4)
    public void checkAddToCart() {
        driver.findElement(xpath("//span[@class='sidebar-item']")).click();//каталог товаров
        driver.findElement(xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href, '#')]")).click();//scooters
        driver.findElement(xpath("//div[@class='brand-box__title']/a[contains(@href,'iphone')]")).click();//iphone
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));//wait for page loading
        driver.findElement(xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart,'Slim Box White (MHDC3)')]")).click();//add to cart iphone
        WebDriverWait wait = new WebDriverWait(driver, 30);//ждем пока не отобразится попап с товаром добавленным в корзину
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[@class='btns-cart-holder']//a[contains(@class,'btn--orange')]")).click();//продолжить покупки
        String actualProductsCountInCart =
                driver.findElement(xpath("//div[contains(@class,'header-bottom__cart')]//div[contains(@class,'cart_count')]"))
                        .getText();//получили 1 которая в корзине (один продукт)
        assertEquals(actualProductsCountInCart, "1");






    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}

