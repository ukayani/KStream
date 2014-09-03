package kstream.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-30
 * Time: 2:43 PM
 */
public enum MediaContainer {
    UNKNOWN,
    MP4,
    MKV,
    WEBM;

    public static final List<MediaContainer> CONTAINERS_LIST = new ArrayList<MediaContainer>(MediaContainer.values().length);
    static {
        for (int i = 0; i < MediaContainer.values().length; i++){
            CONTAINERS_LIST.add(MediaContainer.values()[i]);
        }
    }
}
