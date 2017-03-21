package services.apis.lol.Builder.Type;


public class StaticDataType implements Type {

    private static final String uri =
            "https://global.api.pvp.net/api/lol/static-data/{region}/v{version}/{resource}?api_key={apikey}{parameters}";

    public String getUri() {
        return uri;
    }
}
