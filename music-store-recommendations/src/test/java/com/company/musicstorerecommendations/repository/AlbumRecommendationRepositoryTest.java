package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
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
public class AlbumRecommendationRepositoryTest {

    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;

    AlbumRecommendation albumRecommendation1;
    AlbumRecommendation albumRecommendation2;
    private List<AlbumRecommendation> expectedAlbumRecommendationlist = new ArrayList<>();

    @Before
    public void setUp() {
        albumRecommendationRepository.deleteAll();

        albumRecommendation1 = albumRecommendationRepository.save(new AlbumRecommendation(1, 1, 1, true));
        albumRecommendation2 = albumRecommendationRepository.save(new AlbumRecommendation(2, 2, 2, true));
    }

    @Test
    public void shouldGetAllAlbumRecommendations() {
        expectedAlbumRecommendationlist.add(albumRecommendation1);
        expectedAlbumRecommendationlist.add(albumRecommendation2);

        List<AlbumRecommendation> actual = albumRecommendationRepository.findAll();

        assertEquals(actual.size(), 2);
        assertEquals(expectedAlbumRecommendationlist, actual);
    }

    @Test
    public void shouldAddAndDeleteAlbumRecommendation() {
        Optional<AlbumRecommendation> album = albumRecommendationRepository.findById(albumRecommendation1.getAlbumRecommendationId());
        assertEquals(albumRecommendation1, album.get());

        albumRecommendationRepository.deleteById(albumRecommendation1.getAlbumRecommendationId());
        album = albumRecommendationRepository.findById(albumRecommendation1.getAlbumRecommendationId());
        assertFalse(album.isPresent());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        albumRecommendation1.setLiked(false);
        albumRecommendationRepository.save(albumRecommendation1);
        Optional<AlbumRecommendation> albumRecommendation = albumRecommendationRepository.findById(albumRecommendation1.getAlbumRecommendationId());

        assertEquals(albumRecommendation.get(), albumRecommendation1);
    }

}
