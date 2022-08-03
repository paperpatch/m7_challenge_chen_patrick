package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.*;
import com.company.musicstorecatalog.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    TrackRepository trackRepository;

    Album album1;
    Album album2;
    Label label1;
    Label label2;
    Artist artist1;
    Artist artist2;
    Track track1;
    Track track2;
    private List<Track> expectedTrackList = new ArrayList<>();

    @Before
    public void setUp() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();

        artist1 = artistRepository.save(new Artist(1, "someone", "someone", "someone"));
        artist2 = artistRepository.save(new Artist(2, "somebody", "somebody", "somebody"));

        label1 = labelRepository.save(new Label(1, "Deep Records", "fake-website.com"));
        label2 = labelRepository.save(new Label(2, "Core Music", "another-fake-website.com"));

        album1 = albumRepository.save(new Album(1, "title", artist1.getArtistId(), LocalDate.of(2022,8,3), label1.getLabelId(), new BigDecimal(15.99)));
        album2 = albumRepository.save(new Album(2, "title2", artist2.getArtistId(), LocalDate.of(2025,3,5), label2.getLabelId(), new BigDecimal(15.99)));

        track1 = trackRepository.save(new Track(1, album1.getAlbumId(), "title1", 120));
        track2 = trackRepository.save(new Track(2, album2.getAlbumId(), "title2", 90));

        expectedTrackList.add(track1);
        expectedTrackList.add(track2);
    }

    @Test
    public void shouldAddGetAndDeleteTrack() {
        Optional<Track> track = trackRepository.findById(track1.getTrackId());

        assertEquals(track1, track.get());

        trackRepository.deleteById(track1.getTrackId());
        track = trackRepository.findById(track1.getTrackId());

        assertFalse(track.isPresent());
    }

    @Test
    public void shouldUpdateTrack() {
        track1.setTitle("title3");
        trackRepository.save(track1);
        Optional<Track> actual = trackRepository.findById(track1.getTrackId());

        assertEquals(actual.get(), track1);
    }

    @Test
    public void shouldGetAllTracks() {
        List<Track> actualList = trackRepository.findAll();

        assertEquals(actualList.size(), 2);
        assertEquals(expectedTrackList, actualList);
    }
}
