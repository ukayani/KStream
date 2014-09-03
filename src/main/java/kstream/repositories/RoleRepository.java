package kstream.repositories;

import kstream.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:31 PM
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

}