package kstream.services;

import com.google.common.io.Files;
import kstream.domain.*;
import kstream.dtos.SeriesSettingsForm;
import kstream.factories.VideoFactory;
import kstream.managers.EpisodeVideoManager;
import kstream.mappers.Mapper;
import kstream.notifiers.VideoConversionHandler;
import kstream.processors.SeriesFolderProcessor;
import kstream.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-10
 * Time: 4:52 PM
 */
@Service
public class TVService implements VideoConversionHandler{

    @Autowired
    private TVDBService _tvDbService;

    @Autowired
    private EpisodeVideoManager _evm;

    @Autowired
    private VideoFactory _vf;

    @Autowired
    private Mapper<com.omertron.thetvdbapi.model.Series, Series> _seriesMapper;

    @Autowired
    private Mapper<com.omertron.thetvdbapi.model.Episode, Episode> _episodeMapper;

    @Autowired
    private SeriesRepository _seriesRepository;

    @Autowired
    private EpisodeRepository _episodeRepository;

    @Autowired
    private VideoRepository _videoRepository;

    @Autowired
    private ConversionItemRepository _conversionItemRepository;

    @Autowired
    private ConversionProfileRepository _conversionProfileRepository;

    @Autowired
    private SeriesFolderProcessor _seriesFolderProcessor;

    @Transactional
    public Boolean addSeriesByTvDbId(String id, String language){

        com.omertron.thetvdbapi.model.Series series = _tvDbService.getSeriesById(id, language);

        if (series == null) return false;

        List<com.omertron.thetvdbapi.model.Episode> episodes = _tvDbService.getAllEpisodesBySeriesId(series.getId(), language);

        kstream.domain.Series s = _seriesMapper.map(series);
        s.setEpisodes(episodes.stream().map(e -> _episodeMapper.map(e)).collect(Collectors.toList()));

        _seriesRepository.save(s);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Series> searchSeriesByName(String name){
        return _seriesRepository.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    public Iterable<Series> getAllSeries(){
        return _seriesRepository.getAllSeries();
    }

    @Transactional(readOnly = true)
    public List<List<Episode>> getSeriesEpisodes(Long seriesId){
        List<Episode> episodes = _episodeRepository.findBySeriesId(seriesId);


        List<List<Episode>> episodesList = new ArrayList<List<Episode>>();

        if (episodes == null || episodes.isEmpty()) return episodesList;

        for (Episode ep: episodes){
            Integer seasonNumber = ep.getSeasonNumber() - 1;
            if (seasonNumber>= episodesList.size()){
                episodesList.add(new ArrayList<Episode>());
            }

            episodesList.get(seasonNumber).add(ep);
        }

        return episodesList;
    }

    @Transactional(readOnly = true)
    public Series getSeriesById(Long id){
        return _seriesRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public Episode getEpisodeById(Long id){
        return _episodeRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Episode> getEpisodesForSeriesBySeasonNumber(Long seriesId, Integer seasonNumber){
        Series s = _seriesRepository.findOne(seriesId);
        if (s == null) return null;
        return _episodeRepository.findBySeriesAndSeasonNumber(s, seasonNumber);
    }

    @Transactional
    public Boolean deleteSeriesById(Long id){
        _seriesRepository.delete(id);

        return true;
    }

    @Transactional
    public Boolean updateSeriesSettings(Long seriesId, SeriesSettingsForm settings){
        Series series = _seriesRepository.findOne(seriesId);

        if (series == null) return false;

        series.setPriority(settings.getPriority());
        ConversionProfile profile = _conversionProfileRepository.findOne(settings.getProfileId());

        if (profile == null) return false;

        series.setDefaultProfile(profile);

        _seriesRepository.save(series);

        return true;
    }

    @Transactional
    public Boolean saveEpisode(Episode episode){
        _episodeRepository.save(episode);
        return true;
    }

    @Transactional
    public Boolean addVideoToEpisode(Long episodeId, String sourcePath){

        Episode episode = getEpisodeById(episodeId);
        File sourceFile = new File(sourcePath);
        String extension = Files.getFileExtension(sourcePath);

        String path = _evm.getEpisodePath(episode, extension);

        File destinationFile = new File(path);
        File parentFile = destinationFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()){
            throw new IllegalStateException("Couldn't create dir: " + parentFile);
        }


        try {
            Files.move(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Video video = _vf.getVideo(path);

        episode.addVideo(video);

        _episodeRepository.save(episode);

        return true;
    }

    @Transactional
    public Boolean addVideosToSeries(Long seriesId, List<String> videoPaths){

        List<List<Episode>> seasons = getSeriesEpisodes(seriesId);

        Map<String, Episode> pathMap = _seriesFolderProcessor.mapPathsToEpisodes(seasons, videoPaths);


        for (Map.Entry<String, Episode> entry: pathMap.entrySet()){
            Boolean success = addVideoToEpisode(entry.getValue().getId(), entry.getKey());

        }

        return true;
    }

    @Transactional
    public Video addConversionVideoToEpisode(Long videoId, Long episodeId, String suffix, String extension){

        Episode ep = _episodeRepository.findOne(episodeId);
        // existing video
        Video source = _videoRepository.findOne(videoId);

        String path = _evm.getNonExistingEpisodePath(ep, suffix, extension);

        // copy existing video attributes into this one, for use during conversion
        Video video = new Video();
        video.setUrl(path);
        video.setStatus(VideoStatus.Hidden);
        video.setSize(source.getSize());
        video.setType(VideoType.LOCAL);
        video.setAudioBitrate(source.getAudioBitrate());
        video.setVideoBitrate(source.getVideoBitrate());
        video.setHeight(source.getHeight());
        video.setWidth(source.getWidth());
        video.setVideoCodec(source.getVideoCodec());
        video.setAudioCodec(source.getAudioCodec());
        video.setDuration(source.getDuration());

        ep.addVideo(video);
        video = _videoRepository.save(video);
        _episodeRepository.save(ep);


        return video;
    }


    @Transactional(readOnly = true)
    public Episode getEpisodeWithVideos(Long episodeId){

        Episode ep = _episodeRepository.getEpisodeWithVideo(episodeId);

        if (ep == null) return ep;

        return ep;
    }

    @Transactional
    public Boolean removeVideo(Long episodeId, Long videoId){

        Episode ep = _episodeRepository.findOne(episodeId);
        Video v = _videoRepository.findOne(videoId);


        if (ep == null || v == null) return false;


        ep.removeVideo(v);
        _episodeRepository.save(ep);

        List<ConversionItem> items = _conversionItemRepository.findByVideo(v);

        for (ConversionItem item: items){
            _conversionItemRepository.delete(item);
        }

        _videoRepository.delete(v);

        return true;
    }



    @Override
    @Transactional
    public void onVideoConversionComplete(Long videoId) {
        System.out.println(String.format("Converted Video %d", videoId));
        Video video = _videoRepository.findOne(videoId);
        Episode episode = _episodeRepository.findByVideo(video);

        if (episode == null) return;
        // update the video count on episode
        System.out.println(String.format("Converted video for episode: %s", episode.getName()));
        episode.updateVideoCount();
        _episodeRepository.save(episode);
    }
}
