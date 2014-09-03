package kstream.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 12:05 PM
 */
public enum ConversionMode {
    Paused,
    Normal,
    HighOnly;

    public static final List<ConversionMode> MODE_LIST = new ArrayList<ConversionMode>(ConversionMode.values().length);
    static {
        for (int i = 0; i < ConversionMode.values().length; i++){
            MODE_LIST.add(ConversionMode.values()[i]);
        }
    }
}
