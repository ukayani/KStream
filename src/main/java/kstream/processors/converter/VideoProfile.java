package kstream.processors.converter;

import kstream.domain.Codec;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-01
 * Time: 7:32 PM
 */
public class VideoProfile {

    private Codec videoCodec;
    private Integer videoBitrate;

    private Codec audioCodec;
    private Integer audioBitrate;

    private Integer width;
    private Integer height;

    private Long size;
    private Long duration;

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
}
