package kstream.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-29
 * Time: 8:40 PM
 */

@Entity
@Table(name = "conversion_items")
public class ConversionItem {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ConversionProfile profile;

    private String source;

    private String destination;


    @Column(name = "date_created")
    private Date dateCreated;

    @Enumerated(EnumType.ORDINAL)
    private ConversionStatus status;

    @OneToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "media_type")
    private MediaType mediaType;

    @Enumerated(EnumType.ORDINAL)
    private ConversionPriority priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConversionProfile getProfile() {
        return profile;
    }

    public void setProfile(ConversionProfile profile) {
        this.profile = profile;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public ConversionStatus getStatus() {
        return status;
    }

    public void setStatus(ConversionStatus status) {
        this.status = status;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public ConversionPriority getPriority() {
        return priority;
    }

    public void setPriority(ConversionPriority priority) {
        this.priority = priority;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
