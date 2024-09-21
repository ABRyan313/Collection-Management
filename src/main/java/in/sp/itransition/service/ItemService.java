package in.sp.itransition.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.sp.itransition.model.Collection;
import in.sp.itransition.model.Item;
import in.sp.itransition.repository.CollectionRepository;
import in.sp.itransition.repository.ItemRepository;
import in.sp.itransition.repository.UserRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    
    @Autowired
    private CollectionService collectionService;
    
    @Autowired
    private FileService fileService;
    
    private static final String UPLOAD_DIR = "uploads";

   
    public ItemService(ItemRepository itemRepository, CollectionRepository collectionRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
    }
    
    public List<Item> getItemsByCollectionId(Long collectionId) {
        return itemRepository.findByCollectionId(collectionId);
    }

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


    public void saveItem(Long collectionId, Item item, MultipartFile file) throws IOException {
        // Retrieve the collection and associate it with the item
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid collection ID: " + collectionId));
        item.setCollection(collection);

        // Handle file upload if the file is provided and not empty
        if (file != null && !file.isEmpty()) {
            // Save the file and get the saved file path
            String filePath = fileService.saveFile(file, UPLOAD_DIR);
            item.setFileName(file.getOriginalFilename());
            item.setFilePath(filePath);
            item.setData(file.getBytes()); // Store the file data as a byte array
        }

        // Save the item with associated collection and file data
        itemRepository.save(item);
    }


    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getRecentItems() {
        return itemRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<Item> getItemsByTag(String tag) {
        return itemRepository.findByTags_Name(tag);
    }

    // Method to add an item to a collection with file upload handling
    public void addItemToCollection(Long collectionId, Item item, MultipartFile file) throws IOException {
        Optional<Collection> collectionOpt = collectionService.getCollectionById(collectionId);
        if (collectionOpt.isPresent()) {
            Collection collection = collectionOpt.get();

            // Save the file and get the saved file path
            String filePath = fileService.saveFile(file, UPLOAD_DIR);
            item.setFilePath(filePath); // Set the file path in the item object (update Item class accordingly)

            item.setCollection(collection);
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Collection not found");
        }
    }

	/**
	 * @return the collectionRepository
	 */
	public CollectionRepository getCollectionRepository() {
		return collectionRepository;
	}
}
