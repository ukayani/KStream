package kstream.dtos;

import kstream.domain.Codec;
import kstream.domain.MediaContainer;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-29
 * Time: 7:24 PM
 */
public class ProfileForm {

    @NotEmpty
    private String label;

    @NotEmpty
    private String fileSuffix;

    private MediaContainer container;

    private Codec audioCodec;

    private Integer audioBitrate;

    private String audioOptions;

    private Codec videoCodec;

    private Integer videoBitrate;

    private String videoOptions;

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

    public MediaContainer getContainer() {
        return container;
    }

    public void setContainer(MediaContainer container) {
        this.container = container;
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
