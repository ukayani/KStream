package kstream.repositories.contracts;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:28 PM
 */
public interface IRepository<T extends Identifier<I>, I extends Serializable> {
    public T find(I id);
    public void delete(T obj);
    public void saveOrUpdate(T obj);
}
