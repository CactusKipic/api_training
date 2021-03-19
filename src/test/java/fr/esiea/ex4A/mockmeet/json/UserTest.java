package fr.esiea.ex4A.mockmeet.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    
    @ParameterizedTest
    @ValueSource(strings = {"{\"userEmail\":\"blabla@facteur.mat\",\"userName\":\"Blablu\",\"userTweeter\":\"Balabulu\",\"userCountry\":\"FR\",\"userSex\":\"F\",\"userSexPref\":\"F\"}"
    })
    void user_from_Json_test(String json){
        try {
            User user = new ObjectMapper().readValue(json, User.class);
            System.out.println(user.toString());
            Assertions.assertTrue(json.contains(user.userCountry));
            Assertions.assertTrue(json.contains(user.userEmail));
            Assertions.assertTrue(json.contains(user.userName));
            Assertions.assertTrue(json.contains(user.userTweeter));
            Assertions.assertTrue(json.contains(user.userSex.name()));
            Assertions.assertTrue(json.contains(user.userSexPref.name()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }
    
}
