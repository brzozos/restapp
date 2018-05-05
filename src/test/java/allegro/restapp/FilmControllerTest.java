package allegro.restapp;

import allegro.restapp.models.Film;
import allegro.restapp.models.FilmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Josh Long
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestappApplication.class)
@WebAppConfiguration
public class FilmControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;



    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findFilmWithId2() throws Exception {
        Film film = filmRepository.getOne(2L);

        mockMvc.perform(get("/film/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(film.getId())))
                .andExpect(jsonPath("$.title", is(film.getTitle())))
                .andExpect(jsonPath("$.director", is(film.getDirector())))
                .andExpect(jsonPath("$.premiereYear", is(film.getPremiereYear())));
        mockMvc.perform(get("/film?id=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(film.getId())))
                .andExpect(jsonPath("$.title", is(film.getTitle())))
                .andExpect(jsonPath("$.director", is(film.getDirector())))
                .andExpect(jsonPath("$.premiereYear", is(film.getPremiereYear())));
    }

    @Test
    public void findAllFilms() throws Exception {
        int count = filmRepository.findAll().size();
        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(count)));
        mockMvc.perform(get("/filmObjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(count)));

    }

    @Test
    public void filmNotFound() throws Exception {
        mockMvc.perform(get("/film/22222"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addNewFilm() throws Exception {
        mockMvc.perform(post("/addFilm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Film("Nowy", "Pan",1999)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void userIdIsTaken() throws Exception{
        Film film = new Film("Nowy", "Pan",1999);
        film.setId(1L);
        mockMvc.perform(post("/addFilm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(film))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }




}