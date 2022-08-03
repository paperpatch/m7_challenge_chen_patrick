package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    TrackRepository trackRepository;

    Artist artist1;
    Artist artist2;
    private List<Artist> expectedArtistList = new ArrayList<>();

    @Before
    public void setUp() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();

        artist1 = artistRepository.save(new Artist(1, "someone", "someone", "someone"));
        artist2 = artistRepository.save(new Artist(2, "somebody", "somebody", "somebody"));

        expectedArtistList.add(artist1);
        expectedArtistList.add(artist2);
    }

    @Test
    public void shouldAddGetAndDeleteArtist() {
        Optional<Artist> artist = artistRepository.findById(artist1.getArtistId());

        assertEquals(artist1, artist.get());

        artistRepository.deleteById(artist1.getArtistId());
        artist = artistRepository.findById(artist1.getArtistId());

        assertFalse(artist.isPresent());
    }

    @Test
    public void shouldUpdateArtist() {
        artist1.setName("someone2");
        artistRepository.save(artist1);
        Optional<Artist> actual = artistRepository.findById(artist1.getArtistId());

        assertEquals(actual.get(), artist1);
    }

    @Test
    public void shouldGetAllArtists() {
        List<Artist> actualList = artistRepository.findAll();

        assertEquals(actualList.size(), 2);
        assertEquals(expectedArtistList, actualList);
    }
}
