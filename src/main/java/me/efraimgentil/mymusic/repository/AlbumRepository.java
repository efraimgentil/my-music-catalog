package me.efraimgentil.mymusic.repository;


import me.efraimgentil.mymusic.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByArtistId(Long artistId);

    @Query(value = "SELECT a FROM Album a WHERE a.normalizedName LIKE CONCAT( :filter , '%' ) ")
    List<Album> findByNormalizedNameLike( @Param("filter") String filter);

}
