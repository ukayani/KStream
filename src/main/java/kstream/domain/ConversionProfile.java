package kstream.domain;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-29
 * Time: 8:39 PM
 */

@Entity
@Table(name = "conversion_profiles")
public class ConversionProfile {

    @Id
    @GeneratedValue
    private Long id;

    private String label;

    @Column(name = "file_suffix")
    private String fileSuffix;

    @Enumerated(EnumType.ORDINAL)
    private MediaContainer format;

    @Column(name = "audio_codec")
    @Enumerated(EnumType.ORDINAL)
    private Codec audioCodec;

    @Column(name = "audio_bitrate")
    private Integer audioBitrate;

    @Column(name = "audio_options")
    private String audioOptions;

    @Column(name = "video_codec")
    @Enumerated(EnumType.ORDINAL)
    private Codec videoCodec;

    @Column(name = "video_bitrate")
    private Integer videoBitrate;

    @Column(name = "video_options")
    private String videoOptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public MediaContainer getFormat() {
        return format;
    }

    public void setFormat(MediaContainer format) {
        this.format = format;
    }

    public Codec getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(Codec audioCodec) {
        this.audioCodec = audioCodec;
    }

    public Integer getAudioBitrate() {
        return audioBitrate;
    }

    public void setAudioBitrate(Integer audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public String getAudioOptions() {
        return audioOptions;
    }

    public void setAudioOptions(String audioOptions) {
        this.audioOptions = audioOptions;
    }

    public Codec getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(Codec videoCodec) {
        this.videoCodec = videoCodec;
    }

    public Integer getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(Integer videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public String getVideoOptions() {
        return videoOptions;
    }

    public void setVideoOptions(String videoOptions) {
        this.videoOptions = videoOptions;
    }
}
