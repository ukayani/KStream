package kstream.repositories;

import kstream.repositories.contracts.IRepository;
import kstream.repositories.contracts.Identifier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:24 PM
 */
@NoRepositoryBean
public abstract class GenericRepository {


    @Autowired
    private EntityManager entityManager;

    protected EntityManager manager(){
        return this.entityManager;
    }

    protected Session openSession(){
        return manager().unwrap(Session.class);
    }


}


