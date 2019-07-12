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
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @PostMapping("/add/{codigo}")
    public String save(@PathVariable("codigo") long codigo, Music music){
        Playlist playlist = playlistRepository.findByCodigo(codigo);
        music.setPlaylist(playlist);
        musicRepository.save(music);
        return "redirect:/playlist/view/{codigo}";
    }

    @GetMapping("/edit/{codigo}")
    public ModelAndView editMusic(@PathVariable("codigo") long codigo){
        Music music = musicRepository.findByCodigo(codigo);
        ModelAndView modelAndView = new ModelAndView("music/edit");
        modelAndView.addObject("music", music);
        modelAndView.addObject("playlist", music.getPlaylist().getCodigo());
        return modelAndView;
    }

    @PostMapping("/edit/{codigo}")
    public String editMusicPost(Music music, @PathVariable("codigo") long codigo){
        Music musicTemp = musicRepository.findByCodigo(codigo);
        music.setPlaylist(musicTemp.getPlaylist());
        musicRepository.save(music);
        return "redirect:/playlist/view/" + music.getPlaylist().getCodigo();
    }

    @GetMapping("/delete/{codigo}/{playlist}")
    public String deleteMusic(@PathVariable("codigo") long codigo, @PathVariable("playlist") long playlist) {
        musicRepository.deleteByCodigo(codigo);
        return "redirect:/playlist/view/{playlist}";
    }

}
