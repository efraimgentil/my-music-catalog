package me.efraimgentil.mymusic.controller;

import com.fasterxml.jackson.annotation.JsonView;
import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.View;
import me.efraimgentil.mymusic.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by efraimgentil on 20/02/17.
 */
@RestController
@RequestMapping(value = "/album")
public class AlbumController {

    @Autowired
    AlbumRepository repository;

    @JsonView(View.Basic.class)
    @RequestMapping(value = {  "/{artistId}" , "/{artistId}/" } , method = RequestMethod.GET)
    public List<Album> albumsByArtist( @PathVariable("artistId") Long artistId ){
        return repository.findAllByArtistId( artistId );
    }

    /*@JsonView(ArtistView.All.class)
    @RequestMapping(value = { "/{id}/" , "/{id}" } , method = RequestMethod.GET)
    public Artist artists(@PathVariable("id") Long id  ){
        return repository.findOne(id);
    }*/

}
