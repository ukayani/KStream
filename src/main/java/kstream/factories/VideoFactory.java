package kstream.factories;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import kstream.domain.Codec;
import kstream.domain.Video;
import kstream.domain.VideoStatus;
import kstream.domain.VideoType;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-24
 * Time: 8:35 PM
 */

@Component
public class VideoFactory {

    public static Video getUpdatedVideo(Video video){

        // first we create a Xuggler container object
        IContainer container = IContainer.make();

        // we attempt to open up the container
        int result = container.open(video.getUrl(), IContainer.Type.READ, null);

        // check if the operation was successful
        if (result<0)
            throw new RuntimeException("Failed to open media file");

        // query how many streams the call to open found
        int numStreams = container.getNumStreams();

        // query for the total duration
        long duration = container.getDuration();

        // query for the file size
        long fileSize = container.getFileSize();

        // query for the bit rate
        int bitRate = container.getBitRate();

        ICodec.ID videoCodec = null;

        ICodec.ID audioCodec = null;

        int width = 0;
        int height = 0;

        int videoBitrate = 0;
        int audioBitrate = 0;

        // iterate through the streams to print their meta data
        for (int i=0; i<numStreams; i++) {

            // find the stream object
            IStream stream = container.getStream(i);

            // get the pre-configured decoder that can decode this stream;
            IStreamCoder coder = stream.getStreamCoder();


            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO){
                audioCodec = coder.getCodecID();
                audioBitrate = coder.getBitRate();
            }

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO){
                videoCodec = coder.getCodecID();
                width = coder.getWidth();
                height = coder.getHeight();
                videoBitrate = coder.getBitRate();
            }

        }

        video.setUrl(video.getUrl());
        video.setSize(fileSize);
        video.setType(VideoType.LOCAL);
        video.setWidth(width);
        video.setHeight(height);
        video.setAudioCodec(toCodec(audioCodec));
        video.setVideoCodec(toCodec(videoCodec));
        video.setVideoBitrate((bitRate - audioBitrate)/1000);
        video.setAudioBitrate(audioBitrate/1000);
        video.setStatus(video.isWebEnabled() ? VideoStatus.Visible : VideoStatus.Hidden);
        video.setDuration(duration/1000000);

        return video;
    }
      
    public static Video getVideo(String source){
        Video video = new Video();
        video.setUrl(source);
        return getUpdatedVideo(video);
    }

    private static Codec toCodec(ICodec.ID codec){

        if (codec == ICodec.ID.CODEC_ID_AAC){
            return Codec.AAC;
        }
        else if (codec == ICodec.ID.CODEC_ID_H264){
            return Codec.H264;
        }
        else if (codec == ICodec.ID.CODEC_ID_MP3){
            return Codec.MP3;
        }
        else if (codec == ICodec.ID.CODEC_ID_MPEG4){
            return Codec.MPEG4;
        }
        else {
            return Codec.Unknown;
        }
    }
}
