package kstream.processors.converter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-30
 * Time: 8:28 PM
 */
public interface IConverterArgs {

    List<String> toArgsList();

    Long getMediaDuration();

    String getBaseFolder();
}
