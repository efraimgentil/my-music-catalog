package me.efraimgentil.mymusic.repository;


import me.efraimgentil.mymusic.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByArtistId(Long artistId);
}
