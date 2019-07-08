package com.spotmusic.spotmusic.repository;

import com.spotmusic.spotmusic.models.Playlist;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, String> {

    Playlist findByCodigo(long codigo);

}
