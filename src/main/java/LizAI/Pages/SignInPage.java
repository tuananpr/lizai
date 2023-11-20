package LizAI.Pages;

import Core.Selenium.Button;
import Core.Selenium.Label;
import Core.Selenium.Textbox;
import org.openqa.selenium.By;
import org.testng.Assert;

public class SignInPage extends BaseLizAIPages{
    private static final By by = By.xpath("//app-sign-in");
    private static final String name = "SignIn Page";

    //locators
    private final String id_Username = "username";
    private final String id_Password = "password";
    private final String xpath_SignIn = "//button[@translate='signIn']";
    private final String xpath_Dashboard = "//app-dashboard//h1[@translate='dashboard']";

    //elements
    private final Textbox textboxUsername = new Textbox(By.id(id_Username),"id_Username");
    private final Textbox textboxPassword = new Textbox(By.id(id_Password),"id_Password");
    private final Button buttonSignIn = new Button(By.xpath(xpath_SignIn),"xpath_SignIn");
    private final Label labelDashboard = new Label(By.xpath(xpath_Dashboard),"xpath_Dashboard");

    //constructor
    public SignInPage(boolean assertOpen) {super(by,name,assertOpen);}

    public void signIn(String userName, String passWord) throws InterruptedException{
        textboxUsername.waitForElementToBeDisplay();
        textboxUsername.sendClearText(userName);
        textboxPassword.waitForElementToBeDisplay();
        textboxPassword.sendClearText(passWord);
        waitForJSToComplete();
        this.clickon_SignIn();
    }

    public void clickon_SignIn(){
        buttonSignIn.waitForClickable();
        buttonSignIn.click();
    }

    public void label_Dashboard(String by) throws  InterruptedException{
        labelDashboard.waitForElementToBeDisplay();
        Assert.assertTrue(labelDashboard.getText().contains("Dashboard"),"Dashboard");
        System.out.println("SignIn with Email " +by+ " successfully");
    }
}
