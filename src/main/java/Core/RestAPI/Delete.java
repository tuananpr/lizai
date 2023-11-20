package Core.RestAPI;

import io.restassured.http.Cookies;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public abstract class Delete extends HasBody {
    public Delete(String host) {
        super(host);
    }

    private final static Logger logger = Logger.getLogger(Delete.class.getCanonicalName());

    protected void send(String uri) {
        logger.log(Level.INFO, "--> Sending [DELETE] Request...");
        if (isRestAssuredDebugModeOn()) {
            Cookies cookies = stepsData.getLastCookies();
            stepsData.setLastResponse(given().when().spec(getRequestSpecBuilder().build())
                    .cookies(cookies).log().uri().log().headers().log().params().log().body()
                    .delete(uri).then().log().body().log().status().extract().response());
            stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
        } else {
            Cookies cookies = stepsData.getLastCookies();
            stepsData.setLastResponse(given().when().spec(getRequestSpecBuilder().build())
                    .cookies(cookies).log().uri()
                    .delete(uri).then().log().status().extract().response());
            stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
        }
        logger.log(Level.INFO, "----- Completed -----");
    }
}
