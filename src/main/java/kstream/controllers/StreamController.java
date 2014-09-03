package kstream.controllers;

import kstream.domain.Video;
import kstream.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.regex.Pattern;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-29
 * Time: 7:59 PM
 */

@Controller
public class StreamController {

    @Autowired
    private VideoService _videoService;

    private static final int BUFFER_LENGTH = 1024 * 16;
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
    private static final Pattern RANGE_PATTERN = Pattern.compile("bytes=(?<start>\\d*)-(?<end>\\d*)");

    private static final Logger logger = LoggerFactory.getLogger(StreamController.class);

    @RequestMapping(value = "/streamx/{videoId}", method = RequestMethod.GET)
    public void getStream(@PathVariable("videoId") Long videoId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Video video = _videoService.getVideoById(videoId);
        logger.info("");
        logger.info("-------------REQUEST---------");
        logger.info(video.getUrl());


        Enumeration<String> namesEnumeration = request.getHeaderNames();

        while (namesEnumeration.hasMoreElements()) {
            String name = (String) namesEnumeration.nextElement();
            logger.info(String.format("%s : %s", name, request.getHeader(name)));
        }

        Path videoFile = Paths.get(video.getUrl());


        int length = 0;

        length = (int) Files.size(videoFile);

        int start = 0;
        int end = length - 1;

        logger.info(String.format("File Size: %d", length));

        String range = request.getHeader("Range");
        Matcher matcher = RANGE_PATTERN.matcher(range);

        if (matcher.matches()) {
            String startGroup = matcher.group("start");
            start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
            start = start < 0 ? 0 : start;

            String endGroup = matcher.group("end");
            end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
            end = end > length - 1 ? length - 1 : end;
        }

        int contentLength = end - start + 1;

        response.reset();
        response.setBufferSize(BUFFER_LENGTH);
        //response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", videoFile.getFileName().toString()));
        response.setHeader("Accept-Ranges", "bytes");
        response.setDateHeader("Last-Modified", Files.getLastModifiedTime(videoFile).toMillis());
        //response.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
        response.setContentType(Files.probeContentType(videoFile));
        response.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
        response.setHeader("Content-Length", String.format("%s", contentLength));
        response.setHeader("Keep-Alive", "timeout=5, max=100");
        response.setHeader("Connection", "Keep-Alive");
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        int bytesRead;
        int bytesLeft = contentLength;
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);

        try (SeekableByteChannel input = Files.newByteChannel(videoFile, READ);
             OutputStream output = response.getOutputStream()) {

            input.position(start);

            while ((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
                buffer.clear();
                output.write(buffer.array(), 0, bytesLeft < bytesRead ? bytesLeft : bytesRead);
                bytesLeft -= bytesRead;
            }
        }
    }
}
