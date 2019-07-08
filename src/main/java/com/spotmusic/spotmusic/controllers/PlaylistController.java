package com.spotmusic.spotmusic.controllers;

import com.spotmusic.spotmusic.models.Music;
import com.spotmusic.spotmusic.models.Playlist;
import com.spotmusic.spotmusic.repository.MusicRepository;
import com.spotmusic.spotmusic.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlaylistController {

    @Autowired
    private PlaylistRepository pr;

    @Autowired
    private MusicRepository mr;

    @RequestMapping(value="/playlist/new", method = RequestMethod.GET)
    public String form(){
        return "playlist/new";
    }

    @RequestMapping(value="/playlist/new", method = RequestMethod.POST)
    public String form(Playlist playlist){

        pr.save(playlist);

        return "redirect:/playlist/list";
    }

    @RequestMapping(value="/playlist/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("playlist/list");
        Iterable<Playlist> playlists = pr.findAll();
        mav.addObject("playlists", playlists);
        return mav;
    }

    @RequestMapping(value="/playlist/view/{codigo}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("codigo") long codigo){
        Playlist playlist = pr.findByCodigo(codigo);
        ModelAndView mav = new ModelAndView("playlist/view");
        mav.addObject("playlist", playlist);

        Iterable<Music> musics = mr.findByPlaylist(playlist);
        mav.addObject("musics", musics);

        return mav;
    }

    @RequestMapping(value="/playlist/view/{codigo}", method = RequestMethod.POST)
    public String save(@PathVariable("codigo") long codigo, Music music){
        Playlist playlist = pr.findByCodigo(codigo);
        music.setPlaylist(playlist);
        mr.save(music);
        return "redirect:/playlist/view/{codigo}";
    }

    @RequestMapping(value="/playlist/edit/{codigo}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("codigo") long codigo){
        Playlist playlist = pr.findByCodigo(codigo);
        ModelAndView mav = new ModelAndView("playlist/edit");
        mav.addObject("playlist", playlist);

        return mav;
    }

    @RequestMapping(value="/playlist/edit/{codigo}", method = RequestMethod.POST)
    public String editPost(@PathVariable("codigo") long codigo, Playlist playlist){
        pr.save(playlist);

        return "redirect:/playlist/view/{codigo}";
    }

    @RequestMapping("/playlist/delete")
    public String delete(long codigo) {

        Playlist playlist = pr.findByCodigo(codigo);
        mr.deleteByPlaylist(playlist);
        pr.delete(playlist);

        return "redirect:/playlist/list";
    }

    @RequestMapping(value="/music/edit/{codigo}", method = RequestMethod.GET)
    public ModelAndView editMusic(@PathVariable("codigo") long codigo){
        Music music = mr.findByCodigo(codigo);
        ModelAndView mav = new ModelAndView("music/edit");
        Long playlist = music.getPlaylist().getCodigo();
        mav.addObject("music", music);
        mav.addObject("playlist", playlist);

        return mav;
    }

    @RequestMapping(value="/music/edit/{codigo}", method = RequestMethod.POST)
    public String editMusicPost(@PathVariable("codigo") long codigo, Music music){
        Music m = mr.findByCodigo(codigo);
        music.setPlaylist(m.getPlaylist());
        mr.save(music);
        Long playlist = m.getPlaylist().getCodigo();
        return "redirect:/playlist/view/" + playlist;
    }

    @RequestMapping("/music/delete/{codigo}/{playlist}")
    public String deleteMusic(@PathVariable("codigo") long codigo, @PathVariable("playlist") long playlist) {
        mr.deleteByCodigo(codigo);
        return "redirect:/playlist/view/{playlist}";
    }

}