package services.apis.lol.Utility;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private final String regex;

    public Regex(String regex){
        this.regex = regex;
    }

    public List<String> findAll(String string){
        Matcher m = Pattern.compile(this.regex).matcher(string);
        List<String> result = new ArrayList<>();
        while(m.find()){
            result.add((m.group(0)));
        }
        return result;

    }
}
