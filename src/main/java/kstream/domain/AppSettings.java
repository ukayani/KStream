package kstream.domain;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 11:58 AM
 */
@Entity
@Table(name="settings")
public class AppSettings {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ConversionProfile defaultProfile;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "conversion_mode")
    private ConversionMode conversionMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConversionProfile getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(ConversionProfile defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public ConversionMode getConversionMode() {
        return conversionMode;
    }

    public void setConversionMode(ConversionMode conversionMode) {
        this.conversionMode = conversionMode;
    }
}
