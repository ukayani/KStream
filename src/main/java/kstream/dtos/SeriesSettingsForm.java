package kstream.dtos;

import kstream.domain.ConversionPriority;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 11:02 AM
 */
public class SeriesSettingsForm {

    @NotNull
    private ConversionPriority priority;

    @NotNull
    private Long profileId;

    public ConversionPriority getPriority() {
        return priority;
    }

    public void setPriority(ConversionPriority priority) {
        this.priority = priority;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
