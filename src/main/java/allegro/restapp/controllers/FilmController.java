package allegro.restapp.controllers;

import allegro.restapp.models.Film;
import allegro.restapp.models.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @RequestMapping("/filmObjects")
    public Collection<Film> getAllFilms(){
        return filmRepository.findAll();
    }

    @RequestMapping("/films")
    public Collection<FilmTitleWithId> getAllTitles(){
        return filmRepository.findAll().stream().map(a->new FilmTitleWithId(a.getId(), a.getTitle())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/film/{id}")
    public Optional<Film> getFilm(@PathVariable("id") Long id){
        this.isFilmExists(id);
        return filmRepository.findById(id);
    }

    @RequestMapping(value = "/film", params = "id")
    public Optional<Film> getFilmByParam(@RequestParam(value="id") Long id){
        this.isFilmExists(id);
        return filmRepository.findById(id);
    }

    @PostMapping(value = "/addFilm")
    public ResponseEntity addFilm(@RequestBody Film newFilm){
        if(newFilm.getId()!=null)
            if(filmRepository.findById(newFilm.getId())!=null)
                throw new FilmIdIsTakenException(newFilm.getId());
        filmRepository.save(newFilm);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void isFilmExists(Long id){
        filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
    }

    //Only for ID + Title View
    class FilmTitleWithId {
        private Long id;
        private String title;


        public FilmTitleWithId(Long id, String title) {
            this.id = id;
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}

