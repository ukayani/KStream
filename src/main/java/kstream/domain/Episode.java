package kstream.domain;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-25
 * Time: 3:56 PM
 */

import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "episode_number")
    private Integer episodeNumber;

    @Column(name = "season_number")
    private Integer seasonNumber;

    @Column(name = "first_aired")
    @Temporal(TemporalType.DATE)
    private Date firstAired;

    @Column(name = "last_updated")
    @Temporal(TemporalType.DATE)
    private Date lastUpdated;

    private String overview;

    private Double rating;

    private Integer status;

    @Column(name = "total_video_count")
    private Integer totalVideoCount;

    @Column(name = "visible_video_count")
    private Integer visibleVideoCount;

    @Column(name = "series_tvdb_id")
    private String tvDbId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="episode_videos",
            joinColumns = {@JoinColumn(name="episode_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="video_id", referencedColumnName="id")}
    )
    private Set<Video> videos;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series){
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(Date firstAired) {
        this.firstAired = firstAired;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(String tvDbId) {
        this.tvDbId = tvDbId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {

        this.videos = videos;
        updateVideoCount();
    }

    public void addVideo(Video video){
        if (this.videos == null){
            this.videos = new HashSet<Video>();
        }

        this.videos.add(video);
        updateVideoCount();
    }

    public void removeVideo(Video video){
        if (this.videos == null) return;

        this.videos.remove(video);

        updateVideoCount();
    }

    public Integer getTotalVideoCount() {
        return totalVideoCount;
    }

    public void setTotalVideoCount(Integer totalVideoCount) {
        this.totalVideoCount = totalVideoCount;
    }

    public Integer getVisibleVideoCount() {
        return visibleVideoCount;
    }

    public void setVisibleVideoCount(Integer visibleVideoCount) {
        this.visibleVideoCount = visibleVideoCount;
    }

    public void updateVideoCount(){

        this.totalVideoCount = 0;
        this.visibleVideoCount = 0;

        if (this.videos == null || this.videos.isEmpty()) return;

        for (Video v: this.videos){
            this.totalVideoCount += 1;

            if (v.getStatus() == VideoStatus.Visible){
                this.visibleVideoCount += 1;
            }
        }
    }
}
