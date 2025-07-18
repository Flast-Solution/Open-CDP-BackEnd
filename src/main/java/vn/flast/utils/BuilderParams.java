package vn.flast.utils;

import java.util.HashMap;
import java.util.Map;

public class BuilderParams {

    private final Map<String, Object> maps;
    public BuilderParams() {
        maps = new HashMap<>();
    }

    public static BuilderParams create() {
        return new BuilderParams();
    }

    public BuilderParams addParam(String key, Object obj) {
        this.maps.put(key, obj);
        return this;
    }

    public Map<String, Object> getParams() {
        return maps;
    }

    public String toJson() {
        return JsonUtils.toJson(maps);
    }
}
