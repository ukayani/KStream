package kstream.repositories.specifications;

import kstream.domain.Series;
import kstream.domain.Series_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-14
 * Time: 8:00 PM
 */
public class SeriesSpecifications {

    public static Specification<Series> hasPostalCode(final String name, final Fetches<?> fetches) {

        return new Specification<Series>() {


            @Override
            public Predicate toPredicate(Root<Series> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                bindFetchAttributes(root, fetches);
                return builder.equal(root.get(Series_.name), name);
            }
        };
    }


    private static void bindFetchAttributes(final Root<?> root, final Fetches<?> fetchMetamodel ) {

        if(fetchMetamodel != null) {

            // SingularAtrributes
            for(SingularAttribute attr : fetchMetamodel.singularAttributes() ){
                root.fetch(attr, JoinType.LEFT);
            }

            // PluraAttributes
            for(PluralAttribute attr : fetchMetamodel.pluralAttributes()) {
                root.fetch(attr);
            }
        }
    }

}
