package Core.Utils;

import Core.Support.RestAPI.StepsData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsonUtil {
    public static JSONObject generateJsonFromHashMap(Map<String, Object> data) {
        /*  --- legacy code ---
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<String, Object> field : data.entrySet()) {
                jsonObject.addProperty(field.getKey(), field.getValue().toString());
            }
            return jsonObject;

            --- new code ---
            --- example of using ---
            List<Map<String, ?>> innerJsonArr = new ArrayList<>();
            Map<String, Object> innerJsonObject = new HashMap<>();
            innerJsonObject.put("name", "Tung");
            innerJsonObject.put("age", 30);
            innerJsonArr.add(innerJsonObject);

            Map<String, Object> innerJsonObject2 = new HashMap<>();
            innerJsonObject2.put("name", "An");
            innerJsonObject2.put("age", 40);
            innerJsonArr.add(innerJsonObject2);

            Map<String, Object> finalBody = new HashMap<>();
            finalBody.put("users", innerJsonArr);
            finalBody.put("city", "TPHCM");
            System.out.println(JsonUtil.generateJsonFromHashMap(finalBody));
         */

        JSONObject json = new JSONObject();
        json.putAll(data);
        return json;
    }

    public static ArrayList<Object> parse(String haystack, String jsonPath) {
        return JsonPath.read(haystack, jsonPath);
    }

    public static Object parseSingleObject(String haystack, String jsonPath) {
        return JsonPath.read(haystack, jsonPath);
    }

    public static JsonObject generateJsonFromString(String jsonString) {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public static JsonObject editJson(JsonObject input, String key, Object value) {
        if (value instanceof String) {
            input.addProperty(key, value.toString());
        } else if (value instanceof Number) {
            input.addProperty(key, IntegerUtil.convertToInt(value.toString()));
        } else {
            input.addProperty(key, Boolean.parseBoolean(value.toString()));
        }
        return input;
    }

    public static JsonObject addProperty(JsonObject input, String key, String value) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(input);
        jsonElement.getAsJsonObject().addProperty(key, value);
        return jsonElement.getAsJsonObject();
    }

    public static String objectToJsonString(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static boolean isKeyExist(String haystack, String key) {
        JsonObject jsonObject = JsonUtil.generateJsonFromString(haystack);
        return jsonObject.has(key);
    }

    public static JsonObject generateJsonFromJsonFile(String filePath) {
        // Read from File to String
        JsonObject jsonObject = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(filePath));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Object jsonStringToObject(String jsonString, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, object.getClass());
    }

    public static String parseResponseToString(String path) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(JsonPath.read(StepsData.getInstance().getLastResponseBody(), path));
    }
}
