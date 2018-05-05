package allegro.restapp.controllers;

import allegro.restapp.models.Film;
import allegro.restapp.models.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private FilmRepository filmRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        filmRepository.save(new Film("Gone with the Wind", "Victor Fleming", 1939));
        filmRepository.save(new Film("Star Wars: Episode IV A new hope", "George Lucas", 1997));
        filmRepository.save(new Film("The Sound of Music", "Robert Wise", 1965));
        filmRepository.save(new Film("Quo Vadis", "Jerzy Kawalerowicz", 2001));
    }
}
