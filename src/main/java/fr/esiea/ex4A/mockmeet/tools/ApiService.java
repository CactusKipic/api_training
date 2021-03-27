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

@Service
public class ApiService {
    
    private final int AGE_SEARCH_LIMIT = 10; // Limite de la recherche par âge, dans le positif et négatif
    private final int MATCHES_SEARCH_LIMIT = 10; // Limite de la recherche par âge, dans le positif et négatif
    private final UserStocks userStocks = new UserStocks();
    
    AgifyService agifyService;
    
    public ApiService(AgifyService agifyService){
        this.agifyService = agifyService;
    }
    
    public boolean addNewUser(User user) throws IOException {
        UserAgify userAgify;
        if (userStocks.userAlreadyExist(user)) {
            userAgify = userStocks.getUserAgify(user);
        } else {
            userAgify = agifyService.getAgeOf(user.userName, user.userCountry).execute().body();
        }
        userStocks.addUser(userAgify, user);
        return true;
    }
    
    public MatchesResponse getMatchsFor(String name, String country_id, Sexes sexe, Sexes sexePref){
        int age = userStocks.getUserAgify(name, country_id).age;
        MatchesResponse res = new MatchesResponse(getMatches(age, sexe, sexePref));
        for(int i = 1; i < AGE_SEARCH_LIMIT; i++){
            res.addAll(getMatches(age + i, sexe, sexePref));
            res.addAll(getMatches(age - i, sexe, sexePref));
            if(res.size() >= MATCHES_SEARCH_LIMIT)
                break;
        }
        return res;
    }
    
    public MatchesResponse getMatchsFor(String name, String country_id){
        int age = userStocks.getUserAgify(name, country_id).age;
        MatchesResponse res = new MatchesResponse(getMatches(age));
        for(int i = 1; i < AGE_SEARCH_LIMIT; i++){
            res.addAll(getMatches(age + i));
            res.addAll(getMatches(age - i));
            if(res.size() >= MATCHES_SEARCH_LIMIT)
                break;
        }
        return res;
    }
    
    private List<Match> getMatches(int age, Sexes sexe, Sexes sexePref){
        List<List<User>> usersOfAge = userStocks.getUsersOfAge(age);
        return usersOfAge == null ? new ArrayList<>() : usersOfAge.stream().reduce((users, users2) -> {
            users.addAll(users2);
            return users;
        }).get().stream().filter(u -> (u.userSex.equals(sexePref) && u.userSexPref.equals(sexe))).map(User::toMatch).collect(Collectors.toList());
    }
    
    private List<Match> getMatches(int age){
        List<List<User>> usersOfAge = userStocks.getUsersOfAge(age);
        return usersOfAge == null ? new ArrayList<>() : usersOfAge.stream().reduce((users, users2) -> {
            users.addAll(users2);
            return users;
        }).get().stream().map(User::toMatch).collect(Collectors.toList());
    }
    
}
