package kstream.processors;


import kstream.domain.Episode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-02
 * Time: 10:24 PM
 */
@Component
public class SeriesFolderProcessor {

    private static final Pattern p1 = Pattern.compile("(\\d{1,2})[xe](\\d{1,2})");


    public Map<String, Episode> mapPathsToEpisodes(List<List<Episode>> seasons, List<String> paths){

        Map<String, Episode> pathMap = new HashMap<String, Episode>();

        for (String path: paths){

            Pair<Integer, Integer> pair = getSeasonAndEpisode(path);

            Integer seasonNum = pair.getLeft();
            Integer episodeNum = pair.getRight();

            if (seasonNum == 0 || episodeNum == 0) continue;
            // check bounds
            if (seasonNum - 1 >= seasons.size() || episodeNum - 1 >= seasons.get(seasonNum - 1).size()) continue;

            Episode episode = seasons.get(seasonNum - 1).get(episodeNum - 1);
            pathMap.put(path, episode);
        }

        return pathMap;
    }

    private Pair<Integer, Integer> getSeasonAndEpisode(String path){

        Integer episodeNum = 0;
        Integer seasonNum = 0;

        // define some patterns for episode  S01E02  s1e2 1x2, 01x02

        Matcher m = p1.matcher(path.toLowerCase());

        if (m.find()){

            String epStr = path.substring(m.start(2), m.end(2));
            episodeNum = Integer.parseInt(epStr);
            String sStr = path.substring(m.start(1), m.end(1));
            seasonNum = Integer.parseInt(sStr);

            return new ImmutablePair<Integer, Integer>(seasonNum, episodeNum);
        }

        return null;
    }
}
