package kstream.repositories.custom;

import kstream.domain.Genre;
import kstream.domain.Series;
import kstream.domain.Series_;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-27
 * Time: 11:35 PM
 */
@Component
public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    protected EntityManager manager(){
        return this.entityManager;
    }

    protected Session openSession(){
        return manager().unwrap(Session.class);
    }



    @Override
    public List<Series> getAllSeries() {

        /*return openSession().createCriteria(Series.class)
                .setFetchMode("genres", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();*/
        CriteriaBuilder builder = manager().getCriteriaBuilder();
        CriteriaQuery<Series> criteria = builder.createQuery(Series.class);
        Root<Series> root = criteria.from(Series.class);
        criteria.select(root);

        Fetch<Series, Genre> genreFetch = root.fetch(Series_.genres, JoinType.LEFT);
        criteria.distinct(true);
        return manager().createQuery(criteria).getResultList();
    }
}
