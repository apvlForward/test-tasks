package ui.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Menu {

    public Menu(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="react-burger-menu-btn")
    public WebElement sideMenuButton;

    @FindBy(xpath="//a[@class='shopping_cart_link']")
    public WebElement shoppingCartLink;

    @FindBy(id="logout_sidebar_link")
    public WebElement logoutButton;
}
