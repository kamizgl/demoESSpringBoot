package cn.hadron.demo.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "geo", type = "city")
public class PositionBean {
    @Id
    private String id;
    private String name;
    private String location;

    public PositionBean(){}

    public PositionBean(String id, String name, String location){
        this.id=id;
        this.name=name;
        this.location=location;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
