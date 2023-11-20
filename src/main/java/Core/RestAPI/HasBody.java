package Core.RestAPI;

import com.google.gson.JsonObject;
import io.restassured.mapper.ObjectMapperType;

import java.util.Map;

public abstract class HasBody extends RestAPI {
    public HasBody(String host) {
        super(host);
    }

    protected void setBody(Map<String, Object> body) {
        getRequestSpecBuilder().setBody(body);
    }

    protected void setBody(JsonObject jsonObject) {
        getRequestSpecBuilder().setBody(jsonObject, ObjectMapperType.GSON);
    }

    protected void setBody(String jsonString) {
        getRequestSpecBuilder().setBody(jsonString);
    }
}
