package me.efraimgentil.mymusic.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.efraimgentil.mymusic.CatalogApplication;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.repository.ArtistRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by efraimgentil on 21/02/17.
 */
@RunWith(SpringRunner.class )
@SpringBootTest(classes = CatalogApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class ArtistControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    ArtistRepository re;

    @Test
    @Sql( scripts = "/sampledata/artist.sql" , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql( scripts = "/sampledata/remove_artist.sql" , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnAPageWith10RowsOfAllAvailableArtists() throws IOException {
        String forObject = restTemplate.getForObject("/artist", String.class);

        JsonNode node = mapper.readTree(forObject);
        assertThat(node.has("size")).isTrue();
        assertThat(node.has("totalElements")).isTrue();
        assertThat(node.has("last")).isTrue();
        assertThat(node.has("totalPages")).isTrue();
        assertThat(node.has("numberOfElements")).isTrue();
        assertThat(node.has("content")).isTrue();
        assertThat(node.get("content").isArray()).isTrue();
        JsonNode content = node.get("content");
        for (JsonNode n : content) {
            assertThat(n.has("id")).isTrue();
            assertThat(n.has("name")).isTrue();
            assertThat(n.has("normalizedName")).isFalse();
            assertThat(n.has("albums")).isFalse();
        }
    }

    @Test
    @Sql( scripts = "/sampledata/artist.sql" , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql( scripts = "/sampledata/remove_artist.sql" , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnTheArtisAndItsAlbuns() throws IOException {

        String forObject = restTemplate.getForObject("/artist/1", String.class);

        System.out.println( forObject );
        JsonNode node = mapper.readTree(forObject);
        assertThat(node.has("id")).isTrue();
        assertThat(node.has("name")).isTrue();
        assertThat(node.has("normalizedName")).isFalse();
        assertThat(node.has("albums")).isTrue();
    }


}
