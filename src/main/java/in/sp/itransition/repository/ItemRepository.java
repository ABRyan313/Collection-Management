package in.sp.itransition.repository;

import in.sp.itransition.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Custom method to search for items by name or description (case insensitive)
    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    // Custom method to get the top 10 most recently created items
    List<Item> findTop10ByOrderByCreatedAtDesc();

    // Custom method to find items by tag name
    List<Item> findByTags_Name(String tag);
    
    List<Item> findByCollection_UserId(Long userId);
}
