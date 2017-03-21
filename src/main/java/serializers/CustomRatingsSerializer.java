package serializers;


import domain.Rating;
import domain.User;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomRatingsSerializer extends SerializerBase<User> {
    protected CustomRatingsSerializer(Class<User> t) {
        super(t);
    }

    protected CustomRatingsSerializer() {
        super((Class<User>) null);
    }


    public void serialize(User u, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonGenerationException {
        List<Rating> ratingsDone = new ArrayList<>();
        List<Rating> ratingsReceived = new ArrayList<>();

        for(Rating e: u.getRatingsDone()){
            e.setRatedUser(null);
            e.setRatingUser(null);
            ratingsDone.add(e);
        }
        for(Rating e: u.getRatingsReceived()){
            e.setRatedUser(null);
            e.setRatingUser(null);
            ratingsReceived.add(e);
        }

        u.setRatingsReceived(ratingsReceived);
        u.setRatingsDone(ratingsDone);

        jsonGenerator.writeObject(u);
    }

}
