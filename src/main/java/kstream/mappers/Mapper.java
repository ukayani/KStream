package kstream.mappers;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-06
 * Time: 7:53 PM
 */
public interface Mapper<A, B> {

    A get(B source);
    B map(A source);
}
