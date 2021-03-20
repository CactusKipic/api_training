package fr.esiea.ex4A;

import fr.esiea.ex4A.mockmeet.http.AgifyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {
    
    @Test
    void agifyServiceBean_test(){
        Launcher mock = new Launcher();
    
        AgifyService agifyService = mock.agifyService();
        
        assertNotNull(agifyService);
    }
    
}
