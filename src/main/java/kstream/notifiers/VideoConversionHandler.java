package kstream.notifiers;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-20
 * Time: 2:39 PM
 */
public interface VideoConversionHandler {

    void onVideoConversionComplete(Long videoId);
}
