package kstream.processors.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-30
 * Time: 8:29 PM
 */
@Component
public class FFMpeg implements IVideoConverter {

    @Autowired
    private Environment env;

    private static final String KEY_FFMPEG_PATH = "ffmpeg.path";

    @Override
    public Boolean convert(IConverterArgs args) {

        String ffmpegPath = env.getProperty(KEY_FFMPEG_PATH);

        if (ffmpegPath == null){
            return false;
        }

        List<String> commands = args.toArgsList();
        commands.add(0, ffmpegPath);

        ProcessBuilder builder = new ProcessBuilder(commands);

        String baseFolder = args.getBaseFolder();
        builder.directory(new File(baseFolder));
        builder.redirectErrorStream(true);

        try {


            Process process = builder.start();

            //Read out dir output
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            System.out.printf("Output of running %s is:\n",
                    Arrays.toString(commands.toArray()));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            //Wait to get exit value

            // don't wait forever,
            // lets figure out a maximum time the conversion should take
            // max, should be 1.5 x the duration
            Float maxDuration = args.getMediaDuration().floatValue() * 1.5f;


            Boolean exit = process.waitFor(maxDuration.longValue(), TimeUnit.SECONDS);

            int exitValue = process.exitValue();

            System.out.println("\n\nExit Value is " + exitValue);

            if (!exit || exitValue != 0){
                // failed conversion
                return false;
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
