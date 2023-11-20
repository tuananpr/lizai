package Core.RestAPI;

import io.restassured.http.Cookies;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public abstract class Get extends RestAPI {
    private final static Logger logger = Logger.getLogger(Get.class.getCanonicalName());

    public Get(String host) {
        super(host);
    }

    protected void send(String uri) {
        logger.log(Level.INFO, "--> Sending GET Request ...");
        if (isRestAssuredDebugModeOn()) {
            Cookies cookies = stepsData.getLastCookies();
            stepsData.setLastResponse(given().when().spec(getRequestSpecBuilder().build())
                    .cookies(cookies).log().uri().log().headers().log().params().log().cookies()
                    .get(uri).then().log().body().log().status().log().headers().extract().response());
            stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
        } else {
            Cookies cookies = stepsData.getLastCookies();
            stepsData.setLastResponse(given().when().spec(getRequestSpecBuilder().build())
                    .cookies(cookies).log().uri()
                    .get(uri).then().log().status().extract().response());
            stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
        }
        logger.log(Level.INFO, "----- Completed -----");
    }
}
