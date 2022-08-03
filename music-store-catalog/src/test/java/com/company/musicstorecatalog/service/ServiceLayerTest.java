package com.company.musicstorecatalog.service;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.AlbumRepository;
import com.company.musicstorecatalog.repository.ArtistRepository;
import com.company.musicstorecatalog.repository.LabelRepository;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {
    ServiceLayer serviceLayer;
    AlbumRepository albumRepository;
    ArtistRepository artistRepository;
    TrackRepository trackRepository;
    LabelRepository labelRepository;

    private Album expectedAlbum;
    private Album actualAlbum;
    private List<Album> actualAlbumList;
    private List<Album> expectedAlbumList;

    private Artist expectedArtist;
    private Artist actualArtist;
    private List<Artist> actualArtistList;
    private List<Artist> expectedArtistList;

    private Track expectedTrack;
    private Track actualTrack;
    private List<Track> actualTrackList;
    private List<Track> expectedTrackList;

    private Label expectedLabel;
    private Label actualLabel;
    private List<Label> actualLabelList;
    private List<Label> expectedLabelList;

    @Before
    public void setUp() {
        setUpAlbumRepositoryMock();
        setUpArtistRepositoryMock();
        setUpTrackRepositoryMock();
        setUpLabelRepositoryMock();
        serviceLayer = new ServiceLayer(albumRepository, artistRepository, labelRepository, trackRepository);
    }

    public void setUpAlbumRepositoryMock() {
        albumRepository = mock(AlbumRepository.class);

        Album album1 = new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99));
        Album album2 = new Album(2, "title2", 2, LocalDate.of(2025,3,5), 2, new BigDecimal(15.99));

        List<Album> albumList = Arrays.asList(album1, album2);

        doReturn(albumList).when(albumRepository).findAll();
        doReturn(Optional.of(album1)).when(albumRepository).findById(1);
        doReturn(album1).when(albumRepository).save(album1);
    }

    public void setUpArtistRepositoryMock() {
        artistRepository = mock(ArtistRepository.class);

        Artist artist1 = new Artist(1, "someone", "someone", "someone");
        Artist artist2 = new Artist(2, "somebody", "somebody", "somebody");

        List<Artist> artistList = Arrays.asList(artist1, artist2);

        doReturn(artistList).when(artistRepository).findAll();
        doReturn(Optional.of(artist1)).when(artistRepository).findById(1);
        doReturn(artist1).when(artistRepository).save(artist1);
    }

    public void setUpTrackRepositoryMock() {
        trackRepository = mock(TrackRepository.class);

        Track track1 = new Track(1, 1, "title1", 120);
        Track track2 = new Track(2, 2, "title2", 90);

        List<Track> trackList = Arrays.asList(track1, track2);

        doReturn(trackList).when(trackRepository).findAll();
        doReturn(Optional.of(track1)).when(trackRepository).findById(1);
        doReturn(track1).when(trackRepository).save(track1);
    }

    public void setUpLabelRepositoryMock() {
        labelRepository = mock(LabelRepository.class);

        Label label1 = new Label(1, "Deep Records", "fake-website.com");
        Label label2 = new Label(2, "Core Music", "another-fake-website.com");

        List<Label> labelList = Arrays.asList(label1, label2);

        doReturn(labelList).when(labelRepository).findAll();
        doReturn(Optional.of(label1)).when(labelRepository).findById(1);
        doReturn(label1).when(labelRepository).save(label1);
    }

    @Test
    public void shouldReturnAllAlbums() {
        expectedAlbumList = Arrays.asList(
                new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99)),
                new Album(2, "title2", 2, LocalDate.of(2025,3,5), 2, new BigDecimal(15.99))
        );
        actualAlbumList = serviceLayer.getAllAlbums();

        assertEquals(expectedAlbumList, actualAlbumList);
    }

    @Test
    public void shouldReturnAllArtists() {
        expectedArtistList = Arrays.asList(
                new Artist(1, "someone", "someone", "someone"),
                new Artist(2, "somebody", "somebody", "somebody")
        );
        actualArtistList = serviceLayer.getAllArtists();

        assertEquals(expectedArtistList, actualArtistList);
    }

    @Test
    public void shouldReturnAllTracks() {
        expectedTrackList = Arrays.asList(
                new Track(1, 1, "title1", 120),
                new Track(2, 2, "title2", 90)
        );
        actualTrackList = serviceLayer.getAllTracks();

        assertEquals(expectedAlbumList, actualAlbumList);
    }

    @Test
    public void shouldReturnAllLabels() {
        expectedLabelList = Arrays.asList(
                new Label(1, "Deep Records", "fake-website.com"),
                new Label(2, "Core Music", "another-fake-website.com")
        );
        actualLabelList = serviceLayer.getAllLabels();

        assertEquals(expectedLabelList, actualLabelList);
    }

    @Test
    public void shouldReturnAlbumById() {
        expectedAlbum = new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99));
        actualAlbum = serviceLayer.getAlbumById(1).get();

        assertEquals(expectedAlbum, actualAlbum);
    }

    @Test
    public void shouldReturnArtistById() {
        expectedArtist =  new Artist(1, "someone", "someone", "someone");
        actualArtist = serviceLayer.getArtistById(1).get();

        assertEquals(expectedArtist, actualArtist);
    }

    @Test
    public void shouldReturnTrackById() {
        expectedTrack = new Track(1, 1, "title1", 120);
        actualTrack = serviceLayer.getTrackById(1).get();

        assertEquals(expectedTrack, actualTrack);
    }

    @Test
    public void shouldReturnLabelById() {
        expectedLabel = new Label(1, "Deep Records", "fake-website.com");
        actualLabel = serviceLayer.getLabelById(1).get();

        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    public void shouldCreateAndReturnAlbum() {
        expectedAlbum = new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99));
        actualAlbum = serviceLayer.createAlbum(new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99)));

        assertEquals(expectedAlbum, actualAlbum);
    }

    @Test
    public void shouldCreateAndReturnArtist() {
        expectedArtist =  new Artist(1, "someone", "someone", "someone");
        actualArtist = serviceLayer.createArtist(new Artist(1, "someone", "someone", "someone"));

        assertEquals(expectedAlbum, actualAlbum);
    }

    @Test
    public void shouldCreateAndReturnTrack() {
        expectedTrack = new Track(1, 1, "title1", 120);
        actualTrack = serviceLayer.createTrack(new Track(1, 1, "title1", 120));

        assertEquals(expectedAlbum, actualAlbum);
    }

    @Test
    public void shouldCreateAndReturnLabel() {
        expectedLabel = new Label(1, "Deep Records", "fake-website.com");
        actualLabel = serviceLayer.createLabel(new Label(1, "Deep Records", "fake-website.com"));

        assertEquals(expectedAlbum, actualAlbum);
    }
}
