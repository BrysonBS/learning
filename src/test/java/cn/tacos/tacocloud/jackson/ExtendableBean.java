package cn.tacos.tacocloud.jackson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class ExtendableBean {
    public ExtendableBean(String name) {
        this.name = name;
        this.properties = new HashMap<>();
    }
    public String name;
    private Map<String, String> properties;
    public void add(String key, String value){
        properties.put(key,value);
    }
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
}
