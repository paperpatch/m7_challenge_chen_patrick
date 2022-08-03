package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {

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

    private List<Album> expectedAlbumList = new ArrayList<>();

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

    }

    @Test
    public void shouldGetSaveFindAndDeleteAlbum() {
        Album actual = new Album(3, "title", artist1.getArtistId(), LocalDate.of(2022,8,3), label1.getLabelId(), new BigDecimal(15.99));
        Album expected = new Album(3, "title", artist1.getArtistId(), LocalDate.of(2022,8,3), label1.getLabelId(), new BigDecimal(15.99));

        albumRepository.save(actual);
        expected.setAlbumId(actual.getAlbumId());

        assertEquals(expected, actual);

        List<Album> allAlbums = albumRepository.findAll();

        assertEquals(3, allAlbums.size());

        albumRepository.deleteAll();

        allAlbums = albumRepository.findAll();
        assertEquals(0, allAlbums.size());
    }

}
