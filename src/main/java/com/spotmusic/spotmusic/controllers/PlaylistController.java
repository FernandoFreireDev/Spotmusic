package com.spotmusic.spotmusic.controllers;

import com.spotmusic.spotmusic.models.Music;
import com.spotmusic.spotmusic.models.Playlist;
import com.spotmusic.spotmusic.repository.MusicRepository;
import com.spotmusic.spotmusic.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @GetMapping("/new")
    public String form(){
        return "playlist/new";
    }

    @PostMapping("/new")
    public String form(Playlist playlist){
        playlistRepository.save(playlist);
        return "redirect:/playlist/list";
    }

    @GetMapping("/list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView("playlist/list");
        Iterable<Playlist> playlists = playlistRepository.findAll();
        modelAndView.addObject("playlists", playlists);
        return modelAndView;
    }

    @GetMapping("/view/{codigo}")
    public ModelAndView view(@PathVariable("codigo") long codigo){
        Playlist playlist = playlistRepository.findByCodigo(codigo);
        ModelAndView modelAndView = new ModelAndView("playlist/view");
        modelAndView.addObject("playlist", playlist);

        Iterable<Music> musics = musicRepository.findByPlaylist(playlist);
        modelAndView.addObject("musics", musics);

        return modelAndView;
    }

    @GetMapping("/edit/{codigo}")
    public ModelAndView edit(@PathVariable("codigo") long codigo){
        Playlist playlist = playlistRepository.findByCodigo(codigo);
        ModelAndView modelAndView = new ModelAndView("playlist/edit");
        modelAndView.addObject("playlist", playlist);

        return modelAndView;
    }

    @PostMapping("/edit/{codigo}")
    public String editPost(@PathVariable("codigo") long codigo, Playlist playlist){
        playlistRepository.save(playlist);

        return "redirect:/playlist/view/{codigo}";
    }

    @GetMapping("/delete")
    public String delete(long codigo) {

        Playlist playlist = playlistRepository.findByCodigo(codigo);
        musicRepository.deleteByPlaylist(playlist);
        playlistRepository.delete(playlist);

        return "redirect:/playlist/list";
    }

}