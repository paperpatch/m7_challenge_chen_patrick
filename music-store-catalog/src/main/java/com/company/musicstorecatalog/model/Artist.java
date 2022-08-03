package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Integer artistId;
    @NotNull(message = "name is null")
    private String name;
    @Nullable
    private String instagram;
    @Nullable
    private String twitter;

    public Artist() {
    }

    public Artist(String name, String instagram, String twitter) {
        this.name = name;
        this.instagram = instagram;
        this.twitter = twitter;
    }

    public Artist(Integer artistId, String name, String instagram, String twitter) {
        this.artistId = artistId;
        this.name = name;
        this.instagram = instagram;
        this.twitter = twitter;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(artistId, artist.artistId) && Objects.equals(name, artist.name) && Objects.equals(instagram, artist.instagram) && Objects.equals(twitter, artist.twitter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, name, instagram, twitter);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", name='" + name + '\'' +
                ", instagram='" + instagram + '\'' +
                ", twitter='" + twitter + '\'' +
                '}';
    }
}
