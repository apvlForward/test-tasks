package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ui.pom.*;

public class UITest {

    WebDriver driver = null;

    @DataProvider(name="UserData")
    public Object[][] getUserData() {
        return new String[][] {
                new String[] {"standard_user", "secret_sauce", "Sauce Labs Fleece Jacket", "Jane", "Doe", "12345"},
                new String[] {"problem_user", "secret_sauce", "Sauce Labs Backpack", "John", "Doe", "1357"}
        };
    }

    @BeforeTest
    private void initiateDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }

    @AfterTest
    public void cleanUp() {
        driver.quit();
    }

    @Test(dataProvider="UserData")
    public void buyProductFlow(String login, String pass, String productName,
                               String firstName, String lastName, String zipCode) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.performLogin(login, pass);

        ProductsPage productsPage = new ProductsPage(driver);
        WebElement selectedProductInProducts = productsPage.getProductByName(productName);
        String expectedPrice = productsPage.getProductPrice(selectedProductInProducts);
        productsPage.addProductToCart(selectedProductInProducts);

        Menu menu = new Menu(driver);
        menu.shoppingCartLink.click();

        CartPage cartPage = new CartPage(driver);
        WebElement selectedProductInCart = cartPage.getProductByName(productName);
        String actualPrice = cartPage.getProductPrice(selectedProductInCart);
        Assert.assertEquals(actualPrice, expectedPrice, "Product price in Cart equals product price in Shop");
        cartPage.checkoutButton.click();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillUserInfo(firstName, lastName, zipCode);
        checkoutPage.continueButton.click();
        checkoutPage.assertErrorMessageIsNotDisplayed();
        checkoutPage.finishButton.click();

        SuccessPage successPage = new SuccessPage(driver);
        Assert.assertEquals(successPage.successText.getText(),
                "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        new WebDriverWait(driver,5).until(ExpectedConditions.visibilityOf(successPage.successImage));
        Assert.assertEquals(successPage.successImage.isDisplayed(), true);
        menu.sideMenuButton.click();
        new WebDriverWait(driver,15).until(ExpectedConditions.visibilityOf(menu.logoutButton));
        menu.logoutButton.click();

        new WebDriverWait(driver,5).until(ExpectedConditions.visibilityOf(loginPage.loginInput));
        Assert.assertEquals(loginPage.loginInput.isDisplayed(), true);
        Assert.assertEquals(loginPage.passInput.isDisplayed(), true);
        Assert.assertEquals(loginPage.loginButton.isDisplayed(), true);
    }
}