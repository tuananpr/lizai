package Core.Support.General;

import Core.RestAPI.Get;

import java.util.HashMap;
import java.util.Map;

public class PSIRequest extends Get {
    public PSIRequest() {
        super("https://www.googleapis.com");
    }

    public void send(String url, String category, String strategy) {
        Map<String,Object> params = new HashMap<>();
        params.put("url", url);
        params.put("key", PropertyBuilder.getEnvProperty().getProperty("psi.api.key"));
        params.put("category", category);
        params.put("strategy", strategy);
        addQueryParams(params);
        super.send("/pagespeedonline/v5/runPagespeed");
    }
}