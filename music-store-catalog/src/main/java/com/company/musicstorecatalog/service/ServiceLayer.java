package com.company.musicstorecatalog.service;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.AlbumRepository;
import com.company.musicstorecatalog.repository.ArtistRepository;
import com.company.musicstorecatalog.repository.LabelRepository;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer {
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private LabelRepository labelRepository;
    private TrackRepository trackRepository;

    @Autowired
    public ServiceLayer(AlbumRepository albumRepository, ArtistRepository artistRepository, LabelRepository labelRepository, TrackRepository trackRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.labelRepository = labelRepository;
        this.trackRepository = trackRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }
    public Optional<Album> getAlbumById(int id) {
        return albumRepository.findById(id);
    }
    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }
    public void updateAlbum(Album album) {
        albumRepository.save(album);
    }
    public void deleteAlbum(int id) {
        albumRepository.deleteById(id);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
    public Optional<Artist> getArtistById(int id) {
        return artistRepository.findById(id);
    }
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }
    public void updateArtist(Artist artist) {
        artistRepository.save(artist);
    }
    public void deleteArtist(int id) {
        artistRepository.deleteById(id);
    }

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }
    public Optional<Label> getLabelById(int id) {
        return labelRepository.findById(id);
    }
    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }
    public void updateLabel(Label label) {
        labelRepository.save(label);
    }
    public void deleteLabel(int id) {
        labelRepository.deleteById(id);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
    public Optional<Track> getTrackById(int id) {
        return trackRepository.findById(id);
    }
    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }
    public void updateTrack(Track track) {
        trackRepository.save(track);
    }
    public void deleteTrack(int id) {
        trackRepository.deleteById(id);
    }
}
