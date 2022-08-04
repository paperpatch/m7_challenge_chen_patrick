package com.company.musicstorerecommendations.service;


import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer {
    private AlbumRecommendationRepository albumRecommendationRepository;
    private ArtistRecommendationRepository artistRecommendationRepository;
    private LabelRecommendationRepository labelRecommendationRepository;
    private TrackRecommendationRepository trackRecommendationRepository;

    @Autowired
    public ServiceLayer(AlbumRecommendationRepository albumRecommendationRepository, ArtistRecommendationRepository artistRecommendationRepository, LabelRecommendationRepository labelRecommendationRepository, TrackRecommendationRepository trackRecommendationRepository) {
        this.albumRecommendationRepository = albumRecommendationRepository;
        this.artistRecommendationRepository = artistRecommendationRepository;
        this.labelRecommendationRepository = labelRecommendationRepository;
        this.trackRecommendationRepository = trackRecommendationRepository;
    }

    public List<AlbumRecommendation> getAllAlbumRecommendations() {
        return albumRecommendationRepository.findAll();
    }
    public Optional<AlbumRecommendation> getAlbumRecommendationById(int id) {
        return albumRecommendationRepository.findById(id);
    }
    public AlbumRecommendation createAlbumRecommendation(AlbumRecommendation albumRecommendation) {
        return albumRecommendationRepository.save(albumRecommendation);
    }
    public void updateAlbumRecommendation(AlbumRecommendation album) {
        albumRecommendationRepository.save(album);
    }
    public void deleteAlbumRecommendation(int id) {
        albumRecommendationRepository.deleteById(id);
    }

    public List<ArtistRecommendation> getAllArtistRecommendations() {
        return artistRecommendationRepository.findAll();
    }
    public Optional<ArtistRecommendation> getArtistRecommendationById(int id) {
        return artistRecommendationRepository.findById(id);
    }
    public ArtistRecommendation createArtistRecommendation(ArtistRecommendation artistRecommendation) {
        return artistRecommendationRepository.save(artistRecommendation);
    }
    public void updateArtistRecommendation(ArtistRecommendation artist) {
        artistRecommendationRepository.save(artist);
    }
    public void deleteArtistRecommendation(int id) {
        artistRecommendationRepository.deleteById(id);
    }

    public List<LabelRecommendation> getAllLabelRecommendations() {
        return labelRecommendationRepository.findAll();
    }
    public Optional<LabelRecommendation> getLabelRecommendationById(int id) {
        return labelRecommendationRepository.findById(id);
    }
    public LabelRecommendation createLabelRecommendation(LabelRecommendation labelRecommendation) {
        return labelRecommendationRepository.save(labelRecommendation);
    }
    public void updateLabelRecommendation(LabelRecommendation label) {
        labelRecommendationRepository.save(label);
    }
    public void deleteLabelRecommendation(int id) {
        labelRecommendationRepository.deleteById(id);
    }

    public List<TrackRecommendation> getAllTrackRecommendations() {
        return trackRecommendationRepository.findAll();
    }
    public Optional<TrackRecommendation> getTrackRecommendationById(int id) {
        return trackRecommendationRepository.findById(id);
    }
    public TrackRecommendation createTrackRecommendation(TrackRecommendation trackRecommendation) {
        return trackRecommendationRepository.save(trackRecommendation);
    }
    public void updateTrackRecommendation(TrackRecommendation track) {
        trackRecommendationRepository.save(track);
    }
    public void deleteTrackRecommendation(int id) {
        trackRecommendationRepository.deleteById(id);
    }

}
