package Core.Support.RestAPI;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StepsData {
    private static StepsData instance;
    private Response lastResponse;
    private Cookies lastCookies = new Cookies();
    private String accessToken;
    private String adminAccessToken;
    private String veryfiSession;
    private String loyaltyPhoneValidationId;
    private String appVersion = "N/A";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private StepsData() {
    }

    public static StepsData getInstance() {
        if (instance == null) {
            instance = new StepsData();
        }
        return instance;
    }

    public String getLastResponseBody() {
        return lastResponse.getBody().asString();
    }

    public int getLastResponseCode() {
        return this.lastResponse.getStatusCode();
    }

    public void clearCookie() {
        this.lastCookies = new Cookies();
    }
}
