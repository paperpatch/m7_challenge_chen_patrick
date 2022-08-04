package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
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
public class TrackRecommendationRepositoryTest {
    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;

    TrackRecommendation trackRecommendation1;
    TrackRecommendation trackRecommendation2;
    private List<TrackRecommendation> expectedTrackRecommendationlist = new ArrayList<>();

    @Before
    public void setUp() {
        trackRecommendationRepository.deleteAll();

        trackRecommendation1 = trackRecommendationRepository.save(new TrackRecommendation(1, 1, 1, true));
        trackRecommendation2 = trackRecommendationRepository.save(new TrackRecommendation(2, 2, 2, true));
    }

    @Test
    public void shouldGetAllTrackRecommendations() {
        expectedTrackRecommendationlist.add(trackRecommendation1);
        expectedTrackRecommendationlist.add(trackRecommendation2);

        List<TrackRecommendation> actual = trackRecommendationRepository.findAll();

        assertEquals(actual.size(), 2);
        assertEquals(expectedTrackRecommendationlist, actual);
    }

    @Test
    public void shouldAddAndDeleteTrackRecommendation() {
        Optional<TrackRecommendation> track = trackRecommendationRepository.findById(trackRecommendation1.getTrackRecommendationId());
        assertEquals(trackRecommendation1, track.get());

        trackRecommendationRepository.deleteById(trackRecommendation1.getTrackRecommendationId());
        track = trackRecommendationRepository.findById(trackRecommendation1.getTrackRecommendationId());
        assertFalse(track.isPresent());
    }

    @Test
    public void shouldUpdateTrackRecommendation() {
        trackRecommendation1.setLiked(false);
        trackRecommendationRepository.save(trackRecommendation1);
        Optional<TrackRecommendation> trackRecommendation = trackRecommendationRepository.findById(trackRecommendation1.getTrackRecommendationId());

        assertEquals(trackRecommendation.get(), trackRecommendation1);
    }
}
