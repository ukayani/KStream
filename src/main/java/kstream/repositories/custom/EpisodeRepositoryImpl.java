package kstream.repositories.custom;

import kstream.domain.*;
import kstream.repositories.GenericRepository;
import kstream.repositories.GenreRepository;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-25
 * Time: 8:32 PM
 */

@Component
public class EpisodeRepositoryImpl implements EpisodeRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    protected EntityManager manager(){
        return this.entityManager;
    }

    protected Session openSession(){
        return manager().unwrap(Session.class);
    }


    public Episode getEpisodeWithVideo(Long episodeId){

        CriteriaBuilder builder = manager().getCriteriaBuilder();
        CriteriaQuery<Episode> criteria = builder.createQuery(Episode.class);
        Root<Episode> episodeRoot = criteria.from(Episode.class);
        criteria.select(episodeRoot);
        criteria.where(builder.equal(episodeRoot.get(Episode_.id), episodeId));


        Fetch<Episode, Video> episodeVideos = episodeRoot.fetch(Episode_.videos, JoinType.LEFT);
        Fetch<Episode, Series> episodeSeriesFetch = episodeRoot.fetch(Episode_.series, JoinType.LEFT);

        Episode ep = manager().createQuery(criteria).getSingleResult();

        return ep;
    }
}
