package fr.esiea.ex4A.mockmeet.tools;

import fr.esiea.ex4A.mockmeet.http.AgifyService;
import fr.esiea.ex4A.mockmeet.json.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiServiceTest {
    
    ApiService api = new ApiService(new Retrofit.Builder()
        .baseUrl("https://api.agify.io/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build().create(AgifyService.class));
    
    @ParameterizedTest
    @CsvSource({
        "Hugo,25,FR,brig@esiea.fr,BriTwtr,F,M,0",
        "Manon,25,FR,renaud@virus.debout,RenaudColere,M,F,1"
    })
    void apiService_test(String name, int age, String country, String mail, String tweeter, String sex, String sexPref, int nbMatchs) {
        
        
        User user = new User(mail, name, tweeter, country, Sexes.valueOf(sex), Sexes.valueOf(sexPref));
        assertTrue(api.addNewUser(user));
        
        assertEquals(api.getMatchsFor(name, country, Sexes.valueOf(sex), Sexes.valueOf(sexPref)).size(), nbMatchs);
        
    }
    
}
