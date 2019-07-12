package com.spotmusic.spotmusic.repository;

import com.spotmusic.spotmusic.models.Music;
import com.spotmusic.spotmusic.models.Playlist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MusicRepository extends CrudRepository<Music, String> {

    Iterable<Music> findByPlaylist(Playlist playlist);

    @Transactional
    Integer deleteByPlaylist(Playlist playlist);

    Music findByCodigo(long codigo);

    @Transactional
    Integer deleteByCodigo(long codigo);

}
