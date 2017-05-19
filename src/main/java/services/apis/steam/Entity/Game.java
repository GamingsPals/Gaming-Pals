package services.apis.steam.Entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game  implements Serializable {

    private Integer appid;
    private String name;
    private Integer playtime_forever;


    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlaytime_forever() {
        return playtime_forever;
    }

    public void setPlaytime_forever(Integer playtime_forever) {
        this.playtime_forever = playtime_forever;
    }
}
