package me.efraimgentil.mymusic.controller;

import com.fasterxml.jackson.annotation.JsonView;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.model.View;
import me.efraimgentil.mymusic.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by efraimgentil on 20/02/17.
 */
@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    ArtistRepository repository;

    @JsonView(View.Basic.class)
    @RequestMapping(value = { "" , "/" } , method = RequestMethod.GET)
    @ResponseBody
    public Page<Artist> artists( @RequestParam(value = "page" , defaultValue = "0") Integer page ){
        return repository.findAll( new PageRequest( page , 10) );
    }

    @JsonView(View.All.class)
    @RequestMapping(value = { "/{id}/" , "/{id}" } , method = RequestMethod.GET)
    public Artist artists(@PathVariable("id") Long id  ){
        return repository.findOne(id);
    }

}