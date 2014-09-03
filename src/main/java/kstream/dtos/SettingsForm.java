package kstream.dtos;

import kstream.domain.ConversionMode;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 2:47 PM
 */
public class SettingsForm {

    @NotNull
    private ConversionMode conversionMode;

    @NotNull
    private Long profileId;

    public ConversionMode getConversionMode() {
        return conversionMode;
    }

    public void setConversionMode(ConversionMode conversionMode) {
        this.conversionMode = conversionMode;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
