package me.efraimgentil.mymusic.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.efraimgentil.mymusic.CatalogApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;


/**
 * Created by efraimgentil on 21/02/17.
 */
@RunWith(SpringRunner.class )
@SpringBootTest(classes = CatalogApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
//@AutoConfigureTestDatabase
public class AlbumControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;

    @Test
    @Sql( scripts = "/sampledata/album.sql" , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql( scripts = "/sampledata/remove_album.sql" , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnTheAlbumWithTheSpecifiedId() throws IOException {
        String forObject = restTemplate.getForObject("/album/1", String.class);

        JsonNode node = mapper.readTree(forObject);
        assertThat(node.has("id")).isTrue();
        assertThat(node.has("name")).isTrue();
        assertThat( forObject ).isEqualTo("{\"id\":1,\"name\":\"Random album name\"}");
    }

    @Test
    @Sql( scripts = "/sampledata/album.sql" , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql( scripts = "/sampledata/remove_album.sql" , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnTheAlbumListOfAlbumsWithTheNameLikeTheFilter() throws IOException {
        String forObject = restTemplate.getForObject("/album/search?filter=Dark side", String.class);

        System.out.println("forObject = " + forObject);
        JsonNode node = mapper.readTree(forObject);
        assertThat( node.isArray() ).isTrue();

        /*assertThat(node.has("id")).isTrue();
        assertThat(node.has("name")).isTrue();
        assertThat( forObject ).isEqualTo("{\"id\":1,\"name\":\"Random album name\"}");*/
    }

}
