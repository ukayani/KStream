package kstream.services;

import com.google.common.collect.Lists;
import kstream.domain.AppSettings;
import kstream.domain.ConversionProfile;
import kstream.dtos.SettingsForm;
import kstream.repositories.AppSettingsRepository;
import kstream.repositories.ConversionProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 2:43 PM
 */
@Component
public class SettingsService {

    @Autowired
    private AppSettingsRepository _settingsRepository;

    @Autowired
    private ConversionProfileRepository _conversionProfileRepository;

    @Transactional
    public Boolean updateSettings(SettingsForm form){

        List<AppSettings> settingsList = Lists.newArrayList(_settingsRepository.findAll());

        AppSettings settings = null;

        if (settingsList.size() > 0){
            settings = settingsList.get(0);
        }
        else {
            settings = new AppSettings();
        }

        ConversionProfile profile = _conversionProfileRepository.findOne(form.getProfileId());
        if (profile == null) return false;

        settings.setDefaultProfile(profile);
        settings.setConversionMode(form.getConversionMode());

        _settingsRepository.save(settings);

        return true;
    }

    @Transactional(readOnly = true)
    public AppSettings getSettings(){

        List<AppSettings> settingsList = Lists.newArrayList(_settingsRepository.findAll());

        AppSettings settings = null;

        if (settingsList.size() > 0){
            settings = settingsList.get(0);
        }

        return settings;
    }

}
