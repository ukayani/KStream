package kstream.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-29
 * Time: 11:07 PM
 */
public enum Codec {
    Unknown,
    AAC,
    MP3,
    H264,
    MPEG4;

    public static final List<Codec> CODEC_LIST = new ArrayList<Codec>(Codec.values().length);
    static {
        for (int i = 0; i < Codec.values().length; i++){
            CODEC_LIST.add(Codec.values()[i]);
        }
    }
}


