package fr.esiea.ex4A.mockmeet;

import fr.esiea.ex4A.mockmeet.json.Match;
import fr.esiea.ex4A.mockmeet.json.User;
import fr.esiea.ex4A.mockmeet.tools.MatchesResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MockMeetController {
    
    @PostMapping(path = "api/inscription")
    void registerUser(@RequestBody User user){
        System.out.println(user);
    }
    
    @GetMapping(path = "api/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Match> getMatches(String name, String country){
        
        return new MatchesResponse(new Match("Cobu", "DUDU"),
            new Match("ElBG", "Del78"),
            new Match("LeMatchDavant", "SeLaPeteUnPeuTrop"));
    }
    
}
