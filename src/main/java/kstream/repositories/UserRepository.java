package kstream.repositories;

import kstream.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:21 PM
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}

