package in.sp.itransition.service;



import in.sp.itransition.model.Item;
import in.sp.itransition.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> searchItems(String query) {
        // Implement search logic using full-text search
    	 return itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }
}
