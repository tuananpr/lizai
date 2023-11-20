package Core.RestAPI;

import Core.BaseEntities;
import Core.Support.RestAPI.StepsData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public abstract class RestAPI extends BaseEntities {
    private RequestSpecBuilder requestSpecBuilder;
    protected StepsData stepsData = StepsData.getInstance();

    public RestAPI(String host) {
        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(host);
    }

    public void updateCookies() {
        stepsData.setLastCookies(stepsData.getLastResponse().getDetailedCookies());
    }

    protected RequestSpecBuilder getRequestSpecBuilder() {
        return requestSpecBuilder;
    }

    protected void setRequestSpecBuilder(RequestSpecBuilder requestSpecBuilder) {
        this.requestSpecBuilder = requestSpecBuilder;
    }

    protected void setHeader(String key, String value) {
        this.requestSpecBuilder.addHeader(key, value);
    }

    protected void setHeaders(Map<String, String> headers) {
        this.requestSpecBuilder.addHeaders(headers);
    }

    protected void setAccept(ContentType contentType) {
        this.requestSpecBuilder.setAccept(contentType);
    }

    protected void setContentType(ContentType contentType) {
        this.requestSpecBuilder.setContentType(contentType);
    }

    protected void setReferer(String referer) {
        Map<String,String> header = new HashMap<>();
        header.put("Referer", referer);
        this.requestSpecBuilder.addHeaders(header);
    }

    protected void setFormParam(Map<String, Object> body) {
        getRequestSpecBuilder().addFormParams(body);
    }

    protected void setFormParam(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, value);
        getRequestSpecBuilder().addFormParams(params);
    }

    protected void setPathParam(Map<String, Object> pathParamList) {
        getRequestSpecBuilder().addPathParams(pathParamList);
    }

    protected void setPathParam(String key, String value) {
        getRequestSpecBuilder().addPathParam(key, value);
    }

    protected void addQueryParams(String key, Object value) {
        Map<String, Object> queries = new HashMap<>();
        queries.put(key, value);
        getRequestSpecBuilder().addQueryParams(queries);
    }

    protected void setAuthorization(AUTHORIZATION_TYPE authorization) {
        String type = authorization == AUTHORIZATION_TYPE.BEARER ? "Bearer " : "Insights ";
        if (stepsData.getAccessToken() == null || stepsData.getAccessToken().equalsIgnoreCase("")) {
            Assert.fail("--> Please login before calling to this API ...");
        } else {
            setHeader("Authorization", type + stepsData.getAccessToken());
        }
    }

    protected void setAdminAuthorization() {
        setHeader("Authorization", "Bearer " + stepsData.getAdminAccessToken());
    }

    protected void addQueryParams(Map<String, Object> params) {
        getRequestSpecBuilder().addQueryParams(params);
    }

    protected enum AUTHORIZATION_TYPE {
        BEARER,
        INSIGHTS
    }
}
