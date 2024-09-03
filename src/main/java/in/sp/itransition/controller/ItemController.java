package in.sp.itransition.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.sp.itransition.model.Collection;
import in.sp.itransition.model.Item;
import in.sp.itransition.service.ItemService;
import jakarta.validation.Valid;
import in.sp.itransition.service.CollectionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/collections")
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private CollectionService collectionService;

    // Show the add-item form for a specific collection
    @GetMapping("/{collectionId}/items/new")
    public String showAddItemForm(@PathVariable Long collectionId, Model model) {
        model.addAttribute("collectionId", collectionId); // Ensure this is added to the model
        model.addAttribute("item", new Item()); // Ensure this is initialized and added
        return "add-item"; // Ensure the correct template name
    }

    // Handle adding an item to a collection with file upload
    @PostMapping("/{collectionId}/items")
    public String addItemToCollection(
            @PathVariable Long collectionId,
            @Valid @ModelAttribute Item item,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("collectionId", collectionId);
            return "add-item";
        }

        try {
            itemService.addItemToCollection(collectionId, item, file);
            return "redirect:/collections/" + collectionId;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving item: " + e.getMessage());
            return "error-page"; 
        }
    }

    @GetMapping("/{collectionId}/items/list")
    public String showItemList(@PathVariable Long collectionId, Model model) {
        List<Item> items = itemService.getItemsByCollectionId(collectionId);
        model.addAttribute("items", items);
        return "item/list"; // Ensure this matches the path and file name
    }

    @GetMapping("/{collectionId}/items/{id}")
    public String getItem(@PathVariable Long collectionId, @PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id).orElse(null));
        return "item/detail";
    }

    @DeleteMapping("/{collectionId}/items/{id}")
    public String deleteItem(@PathVariable Long collectionId, @PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/collections/" + collectionId + "/items/list";
    }

    @GetMapping("/recent")
    public String getRecentItems(Model model) {
        model.addAttribute("items", itemService.getRecentItems());
        return "item/recent";
    }

    @GetMapping("/tag/{tag}")
    public String getItemsByTag(@PathVariable String tag, Model model) {
        model.addAttribute("items", itemService.getItemsByTag(tag));
        return "item/tag";
    }

    @GetMapping("/user/items")
    public String getUserItems(Principal principal, Model model) {
        String userEmail = principal.getName();
        List<Item> userItems = itemService.getItemsByUserEmail(userEmail);
        model.addAttribute("items", userItems);
        return "user_items";
    }

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * @return the collectionService
	 */
	public CollectionService getCollectionService() {
		return collectionService;
	}

	/**
	 * @param collectionService the collectionService to set
	 */
	public void setCollectionService(CollectionService collectionService) {
		this.collectionService = collectionService;
	}
}
