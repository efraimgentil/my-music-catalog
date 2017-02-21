package me.efraimgentil.mymusic.repository;

import me.efraimgentil.mymusic.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by efraimgentil on 09/02/17.
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Artist findByNormalizedName(String normalizedName);

    @Query("SELECT a FROM Artist a WHERE a.normalizedName LIKE CONCAT( :filter , '%' )")
    List<Artist> findByNormalizedNameLike(@Param("filter") String filter);
}
