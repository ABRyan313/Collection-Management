package in.sp.itransition.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    private final ItemService itemService;
    private final CollectionService collectionService;

    public ItemController(ItemService itemService, CollectionService collectionService) {
        this.itemService = itemService;
        this.collectionService = collectionService;
    }

    // Show the add-item form for a specific collection
    @GetMapping("/{collectionId}/items/new")
    public String showAddItemForm(@PathVariable Long collectionId, Model model) {
        Optional<Collection> collection = collectionService.getCollectionById(collectionId);
        if (collection.isPresent()) {
            model.addAttribute("collectionId", collectionId);
            model.addAttribute("item", new Item());
            return "add-item";
        }
        return "redirect:/collections/" + collectionId + "/items?error=CollectionNotFound";
    }

    // Handle adding an item to a collection with file upload
    @PostMapping("/{collectionId}/items")
    public String addItemToCollection(
            @PathVariable Long collectionId,
            @Valid @ModelAttribute("item") Item item,
            BindingResult result,
            @RequestParam("file") MultipartFile file, // File uploaded from the form
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("collectionId", collectionId);
            return "add-item";
        }

        try {
            // Call the service method to save the item with the file
            itemService.saveItem(collectionId, item, file);

            // Redirect to the collection view after successful addition
            return "redirect:/collections/" + collectionId;
        } catch (IOException e) {
            // Log the error and show an appropriate message
            log.error("Error saving item file", e);
            model.addAttribute("errorMessage", "Error saving item: " + e.getMessage());
            return "error-page"; // Redirect to a generic error page or customize as needed
        }
    }


    
    @GetMapping("/{collectionId}/items/list")
    public String showItemList(@PathVariable Long collectionId, Model model) {
        List<Item> items = itemService.getItemsByCollectionId(collectionId);
        model.addAttribute("items", items);
        model.addAttribute("collectionId", collectionId); 
        return "item/list";
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

    // Getters, setters, and log retrieval method if needed
}
