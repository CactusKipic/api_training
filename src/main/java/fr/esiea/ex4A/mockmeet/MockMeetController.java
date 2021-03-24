package fr.esiea.ex4A.mockmeet;

import fr.esiea.ex4A.mockmeet.json.Match;
import fr.esiea.ex4A.mockmeet.json.User;
import fr.esiea.ex4A.mockmeet.tools.ApiService;
import fr.esiea.ex4A.mockmeet.tools.MatchesResponse;
import fr.esiea.ex4A.mockmeet.tools.Sexes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MockMeetController {
    
    @Autowired
    ApiService apiService;
    
    @PostMapping(path = "api/inscription")
    void registerUser(@RequestBody User user){
        apiService.addNewUser(user);
    }
    
    @GetMapping(path = "api/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Match> getMatches(@RequestParam("userName") String name, @RequestParam("userCountry") String country,
                           @RequestParam("sex") String sexe,@RequestParam("sexPref") String sexePref){
        return apiService.getMatchsFor(name, country, Sexes.valueOf(sexe), Sexes.valueOf(sexePref));
    }
    
}
