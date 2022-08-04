package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.LabelRecommendation;
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
public class LabelRecommendationRepositoryTest {
    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;

    LabelRecommendation labelRecommendation1;
    LabelRecommendation labelRecommendation2;
    private List<LabelRecommendation> expectedLabelRecommendationlist = new ArrayList<>();

    @Before
    public void setUp() {
        labelRecommendationRepository.deleteAll();

        labelRecommendation1 = labelRecommendationRepository.save(new LabelRecommendation(1, 1, 1, true));
        labelRecommendation2 = labelRecommendationRepository.save(new LabelRecommendation(2, 2, 2, true));
    }

    @Test
    public void shouldGetAllLabelRecommendations() {
        expectedLabelRecommendationlist.add(labelRecommendation1);
        expectedLabelRecommendationlist.add(labelRecommendation2);

        List<LabelRecommendation> actual = labelRecommendationRepository.findAll();

        assertEquals(actual.size(), 2);
        assertEquals(expectedLabelRecommendationlist, actual);
    }

    @Test
    public void shouldAddAndDeleteLabelRecommendation() {
        Optional<LabelRecommendation> label = labelRecommendationRepository.findById(labelRecommendation1.getLabelRecommendationId());
        assertEquals(labelRecommendation1, label.get());

        labelRecommendationRepository.deleteById(labelRecommendation1.getLabelRecommendationId());
        label = labelRecommendationRepository.findById(labelRecommendation1.getLabelRecommendationId());
        assertFalse(label.isPresent());
    }

    @Test
    public void shouldUpdateLabelRecommendation() {
        labelRecommendation1.setLiked(false);
        labelRecommendationRepository.save(labelRecommendation1);
        Optional<LabelRecommendation> labelRecommendation = labelRecommendationRepository.findById(labelRecommendation1.getLabelRecommendationId());

        assertEquals(labelRecommendation.get(), labelRecommendation1);
    }
}
