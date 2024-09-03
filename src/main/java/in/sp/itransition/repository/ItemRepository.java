package in.sp.itransition.repository;

import in.sp.itransition.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Search for items by name or description (case insensitive)
    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    // Get the top 10 most recently created items
    List<Item> findTop10ByOrderByCreatedAtDesc();

    // Find items by tag name
    List<Item> findByTags_Name(String tag);

    // Find items belonging to collections of a specific user
    List<Item> findByCollection_UserId(Long userId);
    
    // Find items by collection ID, useful when adding or listing items within a specific collection
    List<Item> findByCollectionId(Long collectionId);

    // Optional: Find an item by name within a specific collection to handle duplicates
    boolean existsByNameAndCollectionId(String name, Long collectionId);
}

