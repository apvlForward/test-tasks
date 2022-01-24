package ui.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.openqa.selenium.NoSuchElementException;

public class CheckoutPage {

    public CheckoutPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="first-name")
    public WebElement firstNameInput;

    @FindBy(id="last-name")
    public WebElement lastNameInput;

    @FindBy(id="postal-code")
    public WebElement zipCodeInput;

    @FindBy(xpath="//h3[@data-test='error']")
    public WebElement error;

    @FindBy(id="continue")
    public WebElement continueButton;

    @FindBy(id="finish")
    public WebElement finishButton;

    public void fillUserInfo(String firstName, String lastName, String zipCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        zipCodeInput.sendKeys(zipCode);
    }

    public void assertErrorMessageIsNotDisplayed() {
        try {
            Assert.assertEquals(error.isDisplayed(), false,
                    "Error message on Checkout User Info Page visibility");
        } catch (NoSuchElementException e) {
            System.out.println("Error was not found as expected");
        }
    }
}
