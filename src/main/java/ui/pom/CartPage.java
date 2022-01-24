package ui.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.NoSuchElementException;

public class CartPage {

    public CartPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//div[@class='inventory_item_name']")
    public List<WebElement> productNames;

    @FindBy(id="checkout")
    public WebElement checkoutButton;

    public WebElement getProductByName (String productName) {
        WebElement selectedProduct = null;
        for (WebElement currentProduct : productNames) {
            if (currentProduct.getText().equals(productName)) {
                selectedProduct = currentProduct;
                break;
            }
        }

        if (selectedProduct == null) {
            throw new NoSuchElementException("Product with name " + productName + " was not found");
        }

        return selectedProduct;
    }

    public String getProductPrice(WebElement selectedProduct) {
        return selectedProduct.findElement(By.xpath("./../../..//div[@class='inventory_item_price']")).getText();
    }
}
