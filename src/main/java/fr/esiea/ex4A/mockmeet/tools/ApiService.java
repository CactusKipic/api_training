package fr.esiea.ex4A.mockmeet.tools;

import fr.esiea.ex4A.mockmeet.http.AgifyService;
import fr.esiea.ex4A.mockmeet.json.Match;
import fr.esiea.ex4A.mockmeet.json.User;
import fr.esiea.ex4A.mockmeet.json.UserAgify;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.esiea.ex4A.mockmeet.tools.UserStocks.*;

@Service
public class ApiService {
    
    private final int AGE_SEARCH_LIMIT = 10; // Limite de la recherche par âge, dans le positif et négatif
    private final int MATCHES_SEARCH_LIMIT = 10; // Limite de la recherche par âge, dans le positif et négatif
    
    AgifyService agifyService;
    
    public ApiService(AgifyService agifyService){
        this.agifyService = agifyService;
    }
    
    public boolean addNewUser(User user){
        UserAgify userAgify;
        if (userAlreadyExist(user)) {
            userAgify = getUserAgify(user);
        } else {
            try {
                userAgify = agifyService.getAgeOf(user.userName, user.userCountry).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        addUser(userAgify, user);
        return true;
    }
    
    public MatchesResponse getMatchsFor(String name, String country_id, Sexes sexe, Sexes sexePref){
        int age = getUserAgify(name, country_id).age;
        MatchesResponse res = new MatchesResponse(getMatches(age, sexe, sexePref));
        for(int i = 1; i < AGE_SEARCH_LIMIT; i++){
            if(res.size() >= MATCHES_SEARCH_LIMIT)
                break;
            res.addAll(getMatches(age + i, sexe, sexePref));
            if(res.size() >= MATCHES_SEARCH_LIMIT)
                break;
            res.addAll(getMatches(age - i, sexe, sexePref));
        }
        return res;
    }
    
    private List<Match> getMatches(int age, Sexes sexe, Sexes sexePref){
        List<List<User>> usersOfAge = getUsersOfAge(age);
        return usersOfAge == null ? new ArrayList<>() : usersOfAge.stream().reduce((users, users2) -> {
            users.addAll(users2);
            return users;
        }).get().stream().filter(u -> (u.userSex.equals(sexePref) && u.userSexPref.equals(sexe))).map(User::toMatch).collect(Collectors.toList());
    }
    
}
