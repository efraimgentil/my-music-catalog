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
@Sql( scripts = "/sampledata/artist.sql" , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql( scripts = "/sampledata/remove_artist.sql" , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ArtistControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    ArtistRepository re;

    @Test
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
    public void returnTheArtisAndItsAlbuns() throws IOException {

        String forObject = restTemplate.getForObject("/artist/1", String.class);

        JsonNode node = mapper.readTree(forObject);
        assertThat(node.has("id")).isTrue();
        assertThat(node.has("name")).isTrue();
        assertThat(node.has("normalizedName")).isFalse();
        assertThat(node.has("albums")).isTrue();
        assertThat( forObject ).isEqualTo(
        "{\"id\":1,\"name\":\"Rangom GUY\",\"albums\":[{\"id\":2,\"name\":\"Random 2\"},{\"id\":1,\"name\":\"Random album name\"}]}"
        );
    }

    @Test
    public void returnTheAlbunsOfAnArtist() throws IOException {

        String json = restTemplate.getForObject("/artist/2/albums", String.class);

        JsonNode node = mapper.readTree(json);
        assertThat(node.isArray()).isTrue();
        assertThat(json).isEqualTo("[{\"id\":3,\"name\":\"Dark side of the moon\"}]");
    }

    @Test
    public void returnEmptyArrayIfTheArtistDoesntHaveAlbums() throws IOException {

        String json = restTemplate.getForObject("/artist/3/albums", String.class);

        JsonNode node = mapper.readTree(json);
        assertThat(node.isArray()).isTrue();
        assertThat(json).isEqualTo("[]");
    }

    @Test
    public void returnTheArtistWithTheNormalizedNameOfTheFilter() throws IOException {
        String filter = "mari";

        String json = restTemplate.getForObject("/artist/search?filter=" + filter , String.class);
        JsonNode node = mapper.readTree( json );
        assertThat(node.isArray()).isTrue();
        assertThat(json).isEqualTo("[{\"id\":2,\"name\":\"Mario\"},{\"id\":10,\"name\":\"Maria\"}]");
    }

}
