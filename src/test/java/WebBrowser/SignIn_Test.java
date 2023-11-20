package WebBrowser;

import Core.Support.General.Hooks;
import LizAI.Pages.SignInPage;
import org.testng.annotations.*;

public class SignIn_Test extends Hooks {

    @BeforeMethod
    public void setup(){
        before();
        beforeWeb();
    }

    @AfterMethod

    public void tearDown(){
        after();
    }

    String userEmail = "tyler.nguyen@lizai.co";
    String userPassword = "123456";

    @Test(priority = 1)

    public void Signin_Email() throws  InterruptedException{
        SignInPage signInPage = new SignInPage(true);
        signInPage.signIn(userEmail,userPassword);
        signInPage.waitForPageLoadComplete();
        signInPage.label_Dashboard(userEmail);
    }

}
