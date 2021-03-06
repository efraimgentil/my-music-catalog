package me.efraimgentil.mymusic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by efraimgentil on 07/02/17.
 */
@Entity
@Table(name = "artist" )
public class Artist implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({ View.Basic.class  } )
    private Long id;

    @JsonView(View.Basic.class)
    private String name;

    private String normalizedName;

    @JsonView(View.Basic.class)
    private String genero;

    @JsonView(View.All.class)
    @OneToMany(mappedBy = "artist" , cascade = CascadeType.ALL)
    private Set<Album> albums = new LinkedHashSet<>();

    @JsonIgnore
    public Album getNoAlbum(){
        for( Album a : albums ){
            if( Album.NO_ALBUM.equals(a.getNormalizedName()) ){
                return a;
            }
        }
        return null;
    }

    @JsonIgnore
    public boolean hasNoAlbum(){
        for( Album a : albums ){
            if( Album.NO_ALBUM.equals(a.getNormalizedName()) ){
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public void addAlbum(Album album ){
        album.setArtist( this );
        albums.add( album );
    }

    public Artist() {
    }

    public Artist(String name, String normalizedName) {
        this.name = name;
        this.normalizedName = normalizedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (!normalizedName.equals(artist.normalizedName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return normalizedName.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
