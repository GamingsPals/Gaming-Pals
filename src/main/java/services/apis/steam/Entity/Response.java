package services.apis.steam.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    public Games response;

    public Games getResponse() {
        return response;
    }

    public void setResponse(Games response) {
        this.response = response;
    }
}
