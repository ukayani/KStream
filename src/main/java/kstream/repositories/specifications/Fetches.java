package kstream.repositories.specifications;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-14
 * Time: 7:42 PM
 */

public class Fetches<T> {

    private final List<SingularAttribute<T, ? extends Serializable>> singularAttributes = new ArrayList<SingularAttribute<T, ? extends Serializable>>();


    private List<PluralAttribute<T,? extends Collection<?>, ?>> pluralAttributes
            = new ArrayList<PluralAttribute<T,? extends Collection<?>, ?>>();


    public Fetches<T> add(SingularAttribute<T, ? extends Serializable> singularAttribute){
        singularAttributes.add(singularAttribute);
        return this;
    }

    public Fetches<T> add(PluralAttribute<T,? extends Collection<?>, ?> pluralAttribute){
        pluralAttributes.add(pluralAttribute);
        return this;
    }

    public List<SingularAttribute<T, ? extends Serializable>> singularAttributes() {
        return Collections.unmodifiableList(singularAttributes);
    }

    public List<PluralAttribute<T,? extends Collection<?>, ?>> pluralAttributes() {
        return Collections.unmodifiableList(pluralAttributes);
    }
}