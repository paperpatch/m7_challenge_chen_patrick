package com.company.musicstorerecommendations.service;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {
    ServiceLayer serviceLayer;
    AlbumRecommendationRepository albumRecommendationRepository;
    ArtistRecommendationRepository artistRecommendationRepository;
    TrackRecommendationRepository trackRecommendationRepository;
    LabelRecommendationRepository labelRecommendationRepository;

    private AlbumRecommendation expectedAlbumRecommendation;
    private AlbumRecommendation actualAlbumRecommendation;
    private List<AlbumRecommendation> actualAlbumRecommendationList;
    private List<AlbumRecommendation> expectedAlbumRecommendationList;

    private ArtistRecommendation expectedArtistRecommendation;
    private ArtistRecommendation actualArtistRecommendation;
    private List<ArtistRecommendation> actualArtistRecommendationList;
    private List<ArtistRecommendation> expectedArtistRecommendationList;

    private LabelRecommendation expectedLabelRecommendation;
    private LabelRecommendation actualLabelRecommendation;
    private List<LabelRecommendation> actualLabelRecommendationList;
    private List<LabelRecommendation> expectedLabelRecommendationList;

    private TrackRecommendation expectedTrackRecommendation;
    private TrackRecommendation actualTrackRecommendation;
    private List<TrackRecommendation> actualTrackRecommendationList;
    private List<TrackRecommendation> expectedTrackRecommendationList;

    @Before
    public void setUp() {
        setUpAlbumRecommendationRepositoryMock();
        setUpArtistRecommendationRepositoryMock();
        setUpTrackRecommendationRepositoryMock();
        setUpLabelRecommendationRepositoryMock();
        serviceLayer = new ServiceLayer(albumRecommendationRepository, artistRecommendationRepository, labelRecommendationRepository, trackRecommendationRepository);
    }

    public void setUpAlbumRecommendationRepositoryMock() {
        albumRecommendationRepository = mock(AlbumRecommendationRepository.class);

        AlbumRecommendation albumRecommendation1 = new AlbumRecommendation(1,1,1,true);
        AlbumRecommendation albumRecommendation2 = new AlbumRecommendation(2,2,2,true);

        List<AlbumRecommendation> albumRecommendationList = Arrays.asList(albumRecommendation1, albumRecommendation2);

        doReturn(albumRecommendationList).when(albumRecommendationRepository).findAll();
        doReturn(Optional.of(albumRecommendation1)).when(albumRecommendationRepository).findById(1);
        doReturn(albumRecommendation1).when(albumRecommendationRepository).save(albumRecommendation1);
    }

    public void setUpArtistRecommendationRepositoryMock() {
        artistRecommendationRepository = mock(ArtistRecommendationRepository.class);

        ArtistRecommendation artistRecommendation1 = new ArtistRecommendation(1,1,1,true);
        ArtistRecommendation artistRecommendation2 = new ArtistRecommendation(2,2,2,true);

        List<ArtistRecommendation> artistRecommendationList = Arrays.asList(artistRecommendation1, artistRecommendation2);

        doReturn(artistRecommendationList).when(artistRecommendationRepository).findAll();
        doReturn(Optional.of(artistRecommendation1)).when(artistRecommendationRepository).findById(1);
        doReturn(artistRecommendation1).when(artistRecommendationRepository).save(artistRecommendation1);
    }

    public void setUpLabelRecommendationRepositoryMock() {
        labelRecommendationRepository = mock(LabelRecommendationRepository.class);

        LabelRecommendation labelRecommendation1 = new LabelRecommendation(1,1,1,true);
        LabelRecommendation labelRecommendation2 = new LabelRecommendation(2,2,2,true);

        List <LabelRecommendation> labelRecommendationList = Arrays.asList(labelRecommendation1, labelRecommendation2);

        doReturn(labelRecommendationList).when(labelRecommendationRepository).findAll();
        doReturn(Optional.of(labelRecommendation1)).when(labelRecommendationRepository).findById(1);
        doReturn(labelRecommendation1).when(labelRecommendationRepository).save(labelRecommendation1);
    }

    public void setUpTrackRecommendationRepositoryMock() {
        trackRecommendationRepository = mock(TrackRecommendationRepository.class);

        TrackRecommendation trackRecommendation1 = new TrackRecommendation(1,1,1,true);
        TrackRecommendation trackRecommendation2 = new TrackRecommendation(2,2,2,true);

        List<TrackRecommendation> trackRecommendationList = Arrays.asList(trackRecommendation1, trackRecommendation2);

        doReturn(trackRecommendationList).when(trackRecommendationRepository).findAll();
        doReturn(Optional.of(trackRecommendation1)).when(trackRecommendationRepository).findById(1);
        doReturn(trackRecommendation1).when(trackRecommendationRepository).save(trackRecommendation1);
    }

    //  <============= ALBUM TESTS =============>

    @Test
    public void shouldGetAndReturnAlbumRecommendationsList() {
        expectedAlbumRecommendationList = Arrays.asList(
                new AlbumRecommendation(1,1,1,true),
                new AlbumRecommendation(2,2,2,true)
        );
        actualAlbumRecommendationList = serviceLayer.getAllAlbumRecommendations();

        assertEquals(expectedAlbumRecommendationList, actualAlbumRecommendationList);
    }

    @Test
    public void shouldGetAndReturnAlbumRecommendationById() {
        expectedAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        actualAlbumRecommendation = serviceLayer.getAlbumRecommendationById(1).get();

        assertEquals(expectedAlbumRecommendation, actualAlbumRecommendation);
    }

    @Test
    public void shouldCreateAndReturnAlbumRecommendation() {
        expectedAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        actualAlbumRecommendation = serviceLayer.createAlbumRecommendation(new AlbumRecommendation(1,1,1,true));

        assertEquals(expectedAlbumRecommendation, actualAlbumRecommendation);
    }

    //  <============= ARTIST TESTS =============>

    @Test
    public void shouldGetAndReturnArtistRecommendationsList() {
        expectedArtistRecommendationList = Arrays.asList(
                new ArtistRecommendation(1,1,1,true),
                new ArtistRecommendation(2,2,2,true)
        );
        actualArtistRecommendationList = serviceLayer.getAllArtistRecommendations();

        assertEquals(expectedArtistRecommendationList, actualArtistRecommendationList);
    }

    @Test
    public void shouldGetAndReturnArtistRecommendationById() {
        expectedArtistRecommendation = new ArtistRecommendation(1,1,1,true);
        actualArtistRecommendation = serviceLayer.getArtistRecommendationById(1).get();

        assertEquals(expectedArtistRecommendation, actualArtistRecommendation);
    }

    @Test
    public void shouldCreateAndReturnArtistRecommendation() {
        expectedArtistRecommendation = new ArtistRecommendation(1,1,1,true);
        actualArtistRecommendation = serviceLayer.createArtistRecommendation(new ArtistRecommendation(1,1,1,true));

        assertEquals(expectedArtistRecommendation, actualArtistRecommendation);
    }

    //  <============= LABEL TESTS =============>

    @Test
    public void shouldGetAndReturnLabelRecommendationsList() {
        expectedLabelRecommendationList = Arrays.asList(
                new LabelRecommendation(1,1,1,true),
                new LabelRecommendation(2,2,2,true)
        );
        actualLabelRecommendationList = serviceLayer.getAllLabelRecommendations();

        assertEquals(expectedLabelRecommendationList, actualLabelRecommendationList);
    }

    @Test
    public void shouldGetAndReturnLabelRecommendationById() {
        expectedLabelRecommendation = new LabelRecommendation(1,1,1,true);
        actualLabelRecommendation = serviceLayer.getLabelRecommendationById(1).get();

        assertEquals(expectedLabelRecommendation, actualLabelRecommendation);
    }

    @Test
    public void shouldCreateAndReturnLabelRecommendation() {
        expectedLabelRecommendation = new LabelRecommendation(1,1,1,true);
        actualLabelRecommendation = serviceLayer.createLabelRecommendation(new LabelRecommendation(1,1,1,true));

        assertEquals(expectedLabelRecommendation, actualLabelRecommendation);
    }

    //  <============= TRACK TESTS =============>

    @Test
    public void shouldGetAndReturnTrackRecommendationsList() {
        expectedTrackRecommendationList = Arrays.asList(
                new TrackRecommendation(1,1,1,true),
                new TrackRecommendation(2,2,2,true)
        );
        actualTrackRecommendationList = serviceLayer.getAllTrackRecommendations();

        assertEquals(expectedTrackRecommendationList, actualTrackRecommendationList);
    }

    @Test
    public void shouldGetAndReturnTrackRecommendationById() {
        expectedTrackRecommendation = new TrackRecommendation(1,1,1,true);
        actualTrackRecommendation = serviceLayer.getTrackRecommendationById(1).get();

        assertEquals(expectedTrackRecommendation, actualTrackRecommendation);
    }

    @Test
    public void shouldCreateAndReturnTrackRecommendation() {
        expectedTrackRecommendation = new TrackRecommendation(1,1,1,true);
        actualTrackRecommendation = serviceLayer.createTrackRecommendation(new TrackRecommendation(1,1,1,true));

        assertEquals(expectedTrackRecommendation, actualTrackRecommendation);
    }

}
