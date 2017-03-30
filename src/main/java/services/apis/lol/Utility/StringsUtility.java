package services.apis.lol.Utility;

import domain.Summoner;
import org.apache.commons.lang.StringUtils;
import services.apis.lol.Builder.SummonerBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StringsUtility {


    public static String ListToStringComma(List<Integer> list) throws IOException {
        Integer[] arr = new Integer[list.size()];
        list.toArray(arr);
        return  StringUtils.join(arr,",");

    }
}
