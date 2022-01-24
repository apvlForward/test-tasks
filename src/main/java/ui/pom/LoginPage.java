package ui.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="user-name")
    public WebElement loginInput;

    @FindBy(name="password")
    public WebElement passInput;

    @FindBy(name="login-button")
    public WebElement loginButton;

    public void performLogin(String login, String pass) {
        loginInput.sendKeys(login);
        passInput.sendKeys(pass);
        loginButton.click();
    }
}
