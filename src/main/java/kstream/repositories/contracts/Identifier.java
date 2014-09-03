package kstream.repositories.contracts;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:26 PM
 */
public interface Identifier<I> {

    I getId();
    void setId(I id);
}
