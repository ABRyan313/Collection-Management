package in.sp.itransition.service;



import in.sp.itransition.model.Item;
import in.sp.itransition.repository.ItemRepository;
import in.sp.itransition.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Item> getItemsByUserEmail(String email) {
        Long userId = userRepository.findByEmail(email).get().getId();
        return itemRepository.findByCollection_UserId(userId);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getRecentItems() {
        // Add logic to return the most recently added items
        return itemRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<Item> getItemsByTag(String tag) {
        // Add logic to return items by tag
        return itemRepository.findByTags_Name(tag);
    }
}
