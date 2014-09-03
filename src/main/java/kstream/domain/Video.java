package kstream.domain;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-28
 * Time: 5:29 PM
 */

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_type")
    @Enumerated(EnumType.ORDINAL)
    private VideoType type;

    private String url;

    private Integer width;

    private Integer height;

    @Enumerated(EnumType.ORDINAL)
    private VideoStatus status;

    @Column(name = "video_size")
    private Long size;

    private Long duration;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ConversionProfile profile;

    @Column(name = "video_codec")
    @Enumerated(EnumType.ORDINAL)
    private Codec videoCodec;

    @Column(name = "vbitrate")
    private Integer videoBitrate;

    @Column(name = "abitrate")
    private Integer audioBitrate;

    @Column(name = "audio_codec")
    @Enumerated(EnumType.ORDINAL)
    private Codec audioCodec;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VideoType getType() {
        return type;
    }

    public void setType(VideoType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }

    public Boolean isWebEnabled() {
        return videoCodec == Codec.H264 && audioCodec == Codec.AAC;
    }

    public ConversionProfile getProfile() {
        return profile;
    }

    public void setProfile(ConversionProfile profile) {
        this.profile = profile;
    }

    public Codec getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(Codec videoCodec) {
        this.videoCodec = videoCodec;
    }

    public Codec getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(Codec audioCodec) {
        this.audioCodec = audioCodec;
    }

    public Integer getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(Integer videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public Integer getAudioBitrate() {
        return audioBitrate;
    }

    public void setAudioBitrate(Integer audioBitrate) {
        this.audioBitrate = audioBitrate;
    }
}