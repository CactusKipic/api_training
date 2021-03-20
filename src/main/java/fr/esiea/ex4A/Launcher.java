package fr.esiea.ex4A;

import fr.esiea.ex4A.mockmeet.http.AgifyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
    
    @Bean
    AgifyService agifyService(){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.agify.io/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
        
        return retrofit.create(AgifyService.class);
    }
}
