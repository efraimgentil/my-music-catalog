package me.efraimgentil.mymusic.controller;

import com.fasterxml.jackson.annotation.JsonView;
import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.View;
import me.efraimgentil.mymusic.repository.AlbumRepository;
import me.efraimgentil.mymusic.util.NormalizerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by efraimgentil on 20/02/17.
 */
@RestController
@RequestMapping(value = "/album")
public class AlbumController {

    @Autowired
    AlbumRepository repository;

    @Autowired
    NormalizerUtil normalizer;

    @JsonView(View.Basic.class)
    @RequestMapping(value = {  "/{albumId}" , "/{albumId}/" } , method = RequestMethod.GET)
    public Album album( @PathVariable("albumId") Long artistId ){
        return repository.findOne( artistId );
    }

    @JsonView(View.WithArtist.class)
    @RequestMapping(value = {  "/search" , "/search/"  } , method = RequestMethod.GET)
    public List<Album> searchAlbum( @RequestParam(value = "filter" , required =  true ) String filter ){
        filter = normalizer.normalize(filter);
        return repository.findByNormalizedNameLike(filter);
    }

    /*@JsonView(ArtistView.All.class)
    @RequestMapping(value = { "/{id}/" , "/{id}" } , method = RequestMethod.GET)
    public Artist artist(@PathVariable("id") Long id  ){
        return repository.findOne(id);
    }*/

}
