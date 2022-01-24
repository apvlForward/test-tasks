package ui.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SuccessPage {

    public SuccessPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//div[@class='complete-text']")
    public WebElement successText;

    @FindBy(xpath="//img[@class='pony_express']")
    public WebElement successImage;
}
