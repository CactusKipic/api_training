package fr.esiea.ex4A.mockmeet.tools;

import fr.esiea.ex4A.mockmeet.json.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchesResponse extends ArrayList<Match> {
    
    public MatchesResponse(List<Match> matches){
        super();
        this.addAll(matches);
    }
    
    public MatchesResponse(Match... matches){
        super();
        this.addAll(Arrays.asList(matches));
    }
}
