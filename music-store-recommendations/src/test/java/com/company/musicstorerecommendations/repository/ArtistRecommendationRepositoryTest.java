package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
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
public class ArtistRecommendationRepositoryTest {
    @Autowired
    ArtistRecommendationRepository artistRecommendationRepository;

    ArtistRecommendation artistRecommendation1;
    ArtistRecommendation artistRecommendation2;
    private List<ArtistRecommendation> expectedArtistRecommendationlist = new ArrayList<>();

    @Before
    public void setUp() {
        artistRecommendationRepository.deleteAll();

        artistRecommendation1 = artistRecommendationRepository.save(new ArtistRecommendation(1, 1, 1, true));
        artistRecommendation2 = artistRecommendationRepository.save(new ArtistRecommendation(2, 2, 2, true));
    }

    @Test
    public void shouldGetAllArtistRecommendations() {
        expectedArtistRecommendationlist.add(artistRecommendation1);
        expectedArtistRecommendationlist.add(artistRecommendation2);

        List<ArtistRecommendation> actual = artistRecommendationRepository.findAll();

        assertEquals(actual.size(), 2);
        assertEquals(expectedArtistRecommendationlist, actual);
    }

    @Test
    public void shouldAddAndDeleteArtistRecommendation() {
        Optional<ArtistRecommendation> artist = artistRecommendationRepository.findById(artistRecommendation1.getArtistRecommendationId());
        assertEquals(artistRecommendation1, artist.get());

        artistRecommendationRepository.deleteById(artistRecommendation1.getArtistRecommendationId());
        artist = artistRecommendationRepository.findById(artistRecommendation1.getArtistRecommendationId());
        assertFalse(artist.isPresent());
    }

    @Test
    public void shouldUpdateArtistRecommendation() {
        artistRecommendation1.setLiked(false);
        artistRecommendationRepository.save(artistRecommendation1);
        Optional<ArtistRecommendation> artistRecommendation = artistRecommendationRepository.findById(artistRecommendation1.getArtistRecommendationId());

        assertEquals(artistRecommendation.get(), artistRecommendation1);
    }
}
