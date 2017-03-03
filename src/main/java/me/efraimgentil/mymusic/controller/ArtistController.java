package me.efraimgentil.mymusic.controller;

import com.fasterxml.jackson.annotation.JsonView;
import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.model.View;
import me.efraimgentil.mymusic.repository.ArtistRepository;
import me.efraimgentil.mymusic.util.NormalizerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by efraimgentil on 20/02/17.
 */
@RestController
@RequestMapping(value = "/artist")
@Transactional
public class ArtistController {

    @Autowired
    ArtistRepository repository;
    @Autowired
    NormalizerUtil normalizer;

    @JsonView(View.Basic.class)
    @RequestMapping(value = { "" , "/" } , method = RequestMethod.GET)
    @ResponseBody
    public Page<Artist> artists( @RequestParam(value = "page" , defaultValue = "0") Integer page ){
        return repository.findAll( new PageRequest( page , 10) );
    }

    @Transactional
    @JsonView(View.All.class)
    @RequestMapping(value = { "/{id}/" , "/{id}" } , method = RequestMethod.GET)
    public Artist artist(@PathVariable("id") Long id){
        return repository.findOne(id);
    }

    @JsonView(View.Basic.class)
    @RequestMapping(value = {  "/{id}/albums" , "/{id}/albums/" } , method = RequestMethod.GET)
    public List<Album> artistAlbums( @PathVariable("id") Long artistId ){
        return new ArrayList<>( artist(artistId).getAlbums() );
    }

    @JsonView(View.Basic.class)
    @RequestMapping(value = { "/search/" , "/search" } , method = RequestMethod.GET)
    public List<Artist> searchArtist(@RequestParam(value="filter" , required =  true ) String filter ){
        filter = normalizer.normalize(filter);
        return repository.findByNormalizedNameLike(filter);
    }




}
