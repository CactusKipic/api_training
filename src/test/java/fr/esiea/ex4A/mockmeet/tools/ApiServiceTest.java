package fr.esiea.ex4A.mockmeet.tools;

import fr.esiea.ex4A.mockmeet.http.AgifyService;
import fr.esiea.ex4A.mockmeet.json.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiServiceTest {
    
    static ApiService api = new ApiService(new Retrofit.Builder()
        .baseUrl("https://api.agify.io/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build().create(AgifyService.class));
    
    @ParameterizedTest
    @Order(1)
    @CsvSource({
        "Hugo,FR,brig@esiea.fr,BriTwtr,F,M,0",
        "Manon,FR,renaud@virus.debout,RenaudColere,M,F,1"
    })
    void apiService_ajout_test(String name, String country, String mail, String tweeter, String sex, String sexPref, int nbMatchs) {
        
        User user = new User(mail, name, tweeter, country, Sexes.valueOf(sex), Sexes.valueOf(sexPref));
        try {
            assertTrue(api.addNewUser(user));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
    
    @ParameterizedTest
    @Order(2)
    @CsvSource({
        "Hugo,FR,brig@esiea.fr,BriTwtr,F,M,1",
        "Manon,FR,renaud@virus.debout,RenaudColere,M,F,1"
    })
    void apiService_match_test(String name, String country, String mail, String tweeter, String sex, String sexPref, int nbMatchs) {
        
        assertEquals(api.getMatchsFor(name, country, Sexes.valueOf(sex), Sexes.valueOf(sexPref)).size(), nbMatchs);
        
    }
    
    @ParameterizedTest
    @Order(3)
    @CsvSource({
        "Hugo,FR,brig@esiea.fr,BriTwtr,F,M,2",
        "Manon,FR,renaud@virus.debout,RenaudColere,M,F,2"
    })
    void apiService_match_noPref_test(String name, String country, String mail, String tweeter, String sex, String sexPref, int nbMatchs) {
        
        assertEquals(api.getMatchsFor(name, country).size(), nbMatchs);
        
    }
    
}
