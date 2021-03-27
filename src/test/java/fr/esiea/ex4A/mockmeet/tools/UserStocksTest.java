package fr.esiea.ex4A.mockmeet.tools;

import fr.esiea.ex4A.mockmeet.json.User;
import fr.esiea.ex4A.mockmeet.json.UserAgify;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UserStocksTest {
    
    UserStocks userStocks = new UserStocks();
    
    @ParameterizedTest
    @CsvSource({
        "Brigitte,68,916,FR,brig@esiea.fr,BriTwtr,F,M",
        "Renaud,73,14890,FR,renaud@virus.debout,RenaudColere,M,O"
    })
    void userStocks_put_and_retrieve_test(String name, int age, int count, String country, String mail, String tweeter, String sex, String sexPref) {
        UserAgify userAgify = new UserAgify(name, age, count, country);
        User user = new User(mail, name, tweeter, country, Sexes.valueOf(sex), Sexes.valueOf(sexPref));
        userStocks.addUser(userAgify, user);
        
        assertEquals(userStocks.getUserAgify(name, country), userAgify);
        assertTrue(userStocks.getAgifyUsersOfAge(age).contains(userAgify));
        assertTrue(userStocks.getUsersOfAgifyUser(userAgify).contains(user));
        assertEquals(userStocks.getUserAgify(user), userAgify);
        
        assertTrue(userStocks.getUsersOfAge(age).stream().reduce((u1, u2) -> {
            u1.addAll(u2);
            return u1;
        }).get().contains(user));
        
        assertTrue(userStocks.userAlreadyExist(user));
        assertFalse(userStocks.userAlreadyExist(new User(mail, name+"Cpalui", tweeter, country, Sexes.valueOf(sex), Sexes.valueOf(sexPref))));
    }
}
