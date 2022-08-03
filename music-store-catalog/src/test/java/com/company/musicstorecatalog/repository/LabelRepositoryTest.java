package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Label;
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
public class LabelRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    TrackRepository trackRepository;

    Label label1;
    Label label2;
    private List<Label> expectedLabelList = new ArrayList<>();

    @Before
    public void setUp() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();

        label1 = labelRepository.save(new Label(1, "Deep Records", "fake-website.com"));
        label2 = labelRepository.save(new Label(2, "Core Music", "another-fake-website.com"));

        expectedLabelList.add(label1);
        expectedLabelList.add(label2);
    }

    @Test
    public void shouldAddGetAndDeleteLabel() {
        Optional<Label> label = labelRepository.findById(label1.getLabelId());

        assertEquals(label1, label.get());

        labelRepository.deleteById(label1.getLabelId());
        label = labelRepository.findById(label1.getLabelId());

        assertFalse(label.isPresent());
    }

    @Test
    public void shouldUpdateLabel() {
        label1.setName("Deep Records 2");
        labelRepository.save(label1);
        Optional<Label> actual = labelRepository.findById(label1.getLabelId());

        assertEquals(actual.get(), label1);
    }

    @Test
    public void shouldGetAllLabels() {
        List<Label> actualList = labelRepository.findAll();

        assertEquals(actualList.size(), 2);
        assertEquals(expectedLabelList, actualList);
    }
}
