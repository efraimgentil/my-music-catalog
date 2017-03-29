package me.efraimgentil.mymusic.controller;

import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.repository.AlbumRepository;
import me.efraimgentil.mymusic.util.NormalizerUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by efraimgentil on 21/02/17.
 */
@RunWith(MockitoJUnitRunner.class )
public class AlbumControllerTest {

    AlbumController controller;
    @Mock AlbumRepository repository;
    @Mock  NormalizerUtil normalizer;

    @Before
    public void setUp(){
        controller = new AlbumController();
        controller.repository = repository;
        controller.normalizer = normalizer;
    }



    @Test
    public void normalizeTheFilterBeforeSearching(){
        String filter = "Random Filter";
        String normalizedFilter = "randomfilter";
        when( normalizer.normalize(filter) ).thenReturn( normalizedFilter );

        List<Album> result = controller.searchAlbum( filter );

        verify(repository, times(1)).findByNormalizedNameLike(normalizedFilter);
    }

}
