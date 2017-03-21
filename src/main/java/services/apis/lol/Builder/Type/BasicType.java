package services.apis.lol.Builder.Type;


public class BasicType implements Type {

    private static final String uri =
            "https://{region}.api.pvp.net/api/lol/{region}/v{version}/{resource}?api_key={apikey}{parameters}";

    public String getUri() {
        return uri;
    }
}
