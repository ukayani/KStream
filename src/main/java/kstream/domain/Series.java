package kstream.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-25
 * Time: 3:53 PM
 */
@Entity
@Table(name="series")
public class Series  {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "tvdb_id")
    private String tvDbId;

    private String banner;

    private String overview;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "content_rating")
    private String contentRating;

    private String network;

    @Column(name = "imdb_rating", precision = 1, scale = 1)
    private Double imdbRating;

    @Column(precision = 1, scale = 1)
    private Double rating;

    private String status;

    @Enumerated(EnumType.ORDINAL)
    private ConversionPriority priority;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ConversionProfile defaultProfile;

    @Column(name = "first_aired")
    @Temporal(TemporalType.DATE)
    private Date firstAired;

    @Column(name = "air_day")
    private String airDay;

    @Column(name = "air_time")
    private String airTime;

    private String runtime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="series_genres",
            joinColumns = {@JoinColumn(name="series_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="genre_id", referencedColumnName="id")}
    )
    private Set<Genre> genres;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
    private List<Episode> episodes;


    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre){
        if (this.genres == null){
            this.genres = new HashSet<Genre>(0);
        }

        this.genres.add(genre);

    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {

        for(Episode ep: episodes){
            ep.setSeries(this);
        }

        this.episodes = episodes;
    }

    public void addEpisode(Episode episode){
        if (this.episodes == null){
            this.episodes = new ArrayList<Episode>();
        }
        episode.setSeries(this);
        this.episodes.add(episode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(String tvDbId) {
        this.tvDbId = tvDbId;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public ConversionProfile getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(ConversionProfile defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ConversionPriority getPriority() {
        return priority;
    }

    public void setPriority(ConversionPriority priority) {
        this.priority = priority;
    }

    public Date getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(Date firstAired) {
        this.firstAired = firstAired;
    }

    public String getAirDay() {
        return airDay;
    }

    public void setAirDay(String airDay) {
        this.airDay = airDay;
    }

    public String getAirTime() {
        return airTime;
    }

    public void setAirTime(String airTime) {
        this.airTime = airTime;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
