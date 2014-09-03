package kstream.processors.converter;

import kstream.domain.Codec;
import kstream.domain.ConversionItem;
import kstream.domain.ConversionProfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-30
 * Time: 8:30 PM
 */
public class FFMpegArgs implements IConverterArgs {

    private static final String CODER_COPY = "copy";
    private static final String CODER_H264 = "libx264";
    private static final String CODER_AAC = "libfaac";
    private static final String CODER_MP3 = "libmp3lame";
    private static final String CODER_MPEG4 = "mpeg4";

    private static final Integer HD_720_WIDTH = 1280;
    private static final Integer HD_720_HEIGHT = 720;

    public FFMpegArgs(){

    }

    public FFMpegArgs(VideoProfile sourceProfile, ConversionProfile destinationProfile, String source, String destination){

        this.source = source;
        this.destination = destination;
        this.duration = sourceProfile.getDuration();


        String vCodecPart = "-c:v ";
        String aCodecPart = "-c:a ";
        String vCodec = "";
        String aCodec = "";

        if (sourceProfile.getVideoCodec() == destinationProfile.getVideoCodec()){
            vCodec = CODER_COPY;
            vCodecPart += vCodec;
        }
        else {
            vCodec = getEncoder(destinationProfile.getVideoCodec());
            vCodecPart += vCodec;
        }

        if (sourceProfile.getAudioCodec() == destinationProfile.getAudioCodec()){
            aCodec = CODER_COPY;
            aCodecPart += aCodec;
        }
        else {
            aCodec = getEncoder(destinationProfile.getAudioCodec());
            aCodecPart += aCodec;
        }

        videoCodecPart = vCodecPart;
        audioCodecPart = aCodecPart;

        videoBitratePart = "";
        videoOptionsPart = "";

        if (vCodec != CODER_COPY){
            String vBitratePart = "";
            // try achieving same quality
            if (destinationProfile.getVideoBitrate() == null || destinationProfile.getVideoBitrate() == -1){
                vBitratePart = "-crf 24";
            }
            else {
                Integer vbitrate = Integer.min(destinationProfile.getVideoBitrate(), sourceProfile.getVideoBitrate());
                vBitratePart = String.format("-b:v %dk", vbitrate);
            }

            videoBitratePart = vBitratePart;
            videoOptionsPart = destinationProfile.getVideoOptions();
        }

        audioBitratePart = "";
        audioOptionsPart = "";
        if (aCodec != CODER_COPY){
            String aBitratePart = "";
            Integer aBitrate = (destinationProfile.getAudioBitrate() == -1)? sourceProfile.getAudioBitrate():
                    Integer.min(sourceProfile.getAudioBitrate(), destinationProfile.getAudioBitrate());


            aBitratePart = String.format("-b:a %dk", aBitrate);


            audioBitratePart = aBitratePart;
            audioOptionsPart = destinationProfile.getAudioOptions();
        }

        // add faststart if h264
        /*
        if (destinationProfile.getVideoCodec() == Codec.H264){
            videoOptionsPart = "-movflags +faststart " + videoOptionsPart;
        } */

        // check dimensions, must be 720 or lower
        if (sourceProfile.getHeight() > HD_720_HEIGHT){
            Double reductionFactor = HD_720_HEIGHT.doubleValue()/sourceProfile.getHeight().doubleValue();
            // make it multiple of 8
            Double reducedWidth = Math.floor((sourceProfile.getWidth().doubleValue() * reductionFactor)/8) * 8;
            String resizePart = String.format("-s:v %dx%d ", reducedWidth.intValue(), HD_720_HEIGHT);
            videoOptionsPart = resizePart + videoOptionsPart;

        }

        videoPresetPart = "";
        if (!videoOptionsPart.toLowerCase().contains("-preset")){
            //videoPresetPart = "-preset fast";
        }
        videoProfilePart = "";
        if (!videoOptionsPart.toLowerCase().contains("-profile:v")){
            videoProfilePart = "-bf 0 -profile:v baseline -level 3.0";
        }

    }

    private String source;
    private String destination;
    private Long duration;
    private String videoCodecPart;

    private String videoBitratePart;
    private String videoPresetPart;
    private String videoProfilePart;

    private String videoOptionsPart;

    private String audioCodecPart;

    private String audioBitratePart;

    private String audioOptionsPart;

    private String getEncoder(Codec codec){

        if (codec == Codec.H264){
            return CODER_H264;
        }
        if (codec == Codec.AAC){
            return CODER_AAC;
        }
        if (codec == Codec.MP3){
            return CODER_MP3;
        }
        if (codec == Codec.MPEG4){
            return CODER_MPEG4;
        }

        return CODER_COPY;
    }

    @Override
    public List<String> toArgsList() {

        List<String> commands = new ArrayList<String>();

        // input commands
        commands.add("-i");
        File sourceFile = new File(source);
        commands.add(String.format("%s", sourceFile.getName()));
        commands.add("-y");

        StringBuilder builder = new StringBuilder();

        builder.append(audioCodecPart);
        builder.append(" ");
        builder.append(audioBitratePart);
        builder.append(" ");
        builder.append(audioOptionsPart);
        builder.append(" ");
        builder.append(videoCodecPart);
        builder.append(" ");
        builder.append(videoPresetPart);
        builder.append(" ");
        builder.append(videoBitratePart);
        builder.append(" ");
        builder.append(videoProfilePart);
        builder.append(" ");
        builder.append(videoOptionsPart);


        String args = builder.toString().replaceAll("\\s+", " ");
        List<String> commandsMid = Arrays.asList(args.split(" "));

        commands.addAll(commandsMid);

        // add output commands
        File outputFile = new File(destination);
        commands.add(String.format("%s", outputFile.getName()));

        return commands;
    }

    @Override
    public Long getMediaDuration() {
        return duration;
    }

    @Override
    public String getBaseFolder() {
        File sourceFile = new File(source);
        return sourceFile.getParent();
    }
}
