package kstream.repositories;

import kstream.domain.AppSettings;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 2:43 PM
 */
public interface AppSettingsRepository extends CrudRepository<AppSettings, Long> {
}
