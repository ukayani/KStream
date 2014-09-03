package kstream.repositories;

import kstream.domain.ConversionItem;
import kstream.domain.ConversionPriority;
import kstream.domain.ConversionStatus;
import kstream.domain.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-26
 * Time: 11:45 AM
 */
public interface ConversionItemRepository extends CrudRepository<ConversionItem, Long> {

    @Query("select c from ConversionItem c left join fetch c.profile p left join fetch c.video v")
    public List<ConversionItem> getAllItems();

    @Query("select c from ConversionItem c left join fetch c.profile p left join fetch c.video v where c.priority = ?1 and c.status = ?2")
    public List<ConversionItem> getAllItemsWithPriorityAndStatus(ConversionPriority priority, ConversionStatus status);

    public List<ConversionItem> findByVideo(Video video);
}
