package me.efraimgentil.mymusic.controller;

import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.repository.ArtistRepository;
import me.efraimgentil.mymusic.util.NormalizerUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by efraimgentil on 21/02/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArtistControllerTest {

    ArtistController controller;
    @Mock NormalizerUtil normalizer;
    @Mock ArtistRepository repository;

    @Before
    public void setUp(){
        controller = new ArtistController();
        controller.normalizer = normalizer;
        controller.repository = repository;
    }

    @Test
    public void callFindAllPageableWithDefaultOf10RowsPerPage(){

        controller.artists( 0 );

        verify( repository , times(1) ).findAll( new PageRequest( 0 , 10 ) );
    }

    @Test
    public void callFindAllPageableWithDefaultOf10RowsPerPageInTheSpecifiedPage(){
        Integer page = 10;

        controller.artists( page  );

        verify( repository , times(1) ).findAll( new PageRequest( page  , 10 ) );
    }

/*    @Test
    public void callFindByArtistFromTheRepository(){
        Long artistId = 1L;
        List<Album> albums = new ArrayList<>();
        when( repository.findAllByArtistId( anyLong() ) ).thenReturn( albums );

        List<Album> result = controller.albumsByArtist(artistId);

        verify( repository , times(1) ).findAllByArtistId( 1L );
        assertThat( result ).isSameAs( albums );
    }*/

    @Test
    public void getArtistById(){
        Long artistId = 1L;
        Artist a = new Artist();
        when( repository.findOne( artistId ) ).thenReturn( a  );

        Artist result = controller.artist(artistId);

        verify( repository , times(1) ).findOne( artistId );
        assertThat(result ).isSameAs( a );
    }

    @Test
    public void normalizeFilterBeforeSearching(){
        String filter = "HA HA";
        String normalizedFilter = "haha";
        when(normalizer.normalize(filter)).thenReturn( normalizedFilter );

        List<Artist> artists = controller.searchArtist(filter);

        verify( repository , times(1) ).findByNormalizedNameLike(normalizedFilter);
    }



}
