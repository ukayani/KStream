package kstream.processors;

import kstream.services.VideoService;

import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-20
 * Time: 3:01 PM
 */
public interface IVideoProcessor {

    Future<Boolean> process(Long conversionItemId, VideoService service);
}
