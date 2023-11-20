package Core.RestAPI;

import io.restassured.http.Cookies;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public abstract class Post extends HasBody {
    private final static Logger logger = Logger.getLogger(Post.class.getCanonicalName());

    public Post(String host) {
        super(host);
    }

    protected void send(String uri) {
        logger.log(Level.INFO, "--> Sending POST Request ...");
        if (isRestAssuredDebugModeOn()) {
            Cookies cookies = stepsData.getLastCookies();
            stepsData.setLastResponse(given().when().spec(getRequestSpecBuilder().build())
                    .cookies(cookies).log().uri().log().headers().log().params().log().body().log().cookies()
                    .post(uri).then().log().everything().extract().response());
            stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
        } else {
            Cookies cookies = stepsData.getLastCookies();
            stepsData.setLastResponse(given().when().spec(getRequestSpecBuilder().build())
                    .cookies(cookies).log().uri()
                    .post(uri).then().log().status().extract().response());
            stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
        }
        logger.log(Level.INFO, "----- Completed -----");
    }
}
