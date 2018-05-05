package allegro.restapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FilmIdIsTakenException extends RuntimeException {
    public FilmIdIsTakenException(Long id) {
        super("Cannot create object with id=" + id+".Ids are generated automatically");
    }
}
