package kstream.dtos;

import kstream.domain.ConversionPriority;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-26
 * Time: 11:57 AM
 */
public class ConversionCreationForm {



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
