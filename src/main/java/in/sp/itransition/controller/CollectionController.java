package in.sp.itransition.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.sp.itransition.model.Collection;
import in.sp.itransition.model.User;
import in.sp.itransition.service.CollectionService;
import in.sp.itransition.service.UserService;

@Controller
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listCollections(Model model) {
        model.addAttribute("collections", collectionService.getAllCollections());
        return "collections";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("collection", new Collection());
        return "create-collection";
    }

    @PostMapping
    public String createCollection(@ModelAttribute Collection collection) {
        User user = userService.findByEmail("abir4044@diu.edu.bd");
        if (user != null) {
            collection.setUser(user);
            collectionService.saveCollection(collection);
        } else {
            return "redirect:/collections?error=UserNotFound";
        }
        return "redirect:/collections";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Collection> collection = collectionService.getCollectionById(id);
        model.addAttribute("collection", collection.orElse(null));
        return "edit-collection";
    }

    @PostMapping("/edit/{id}")
    public String updateCollection(@PathVariable Long id, @ModelAttribute Collection collection) {
        Optional<Collection> existingCollection = collectionService.getCollectionById(id);
        if (existingCollection.isPresent()) {
            Collection updatedCollection = existingCollection.get();
            // Update the collection's attributes
            updatedCollection.setName(collection.getName());
            updatedCollection.setDescription(collection.getDescription());
            updatedCollection.setCategory(collection.getCategory());
            updatedCollection.setImageUrl(collection.getImageUrl());

            // Fetch and set the user (assuming you're using email or another method to find the current user)
            User user = userService.findByEmail("abir4044@diu.edu.bd");
            if (user != null) {
                updatedCollection.setUser(user);
            } else {
                return "redirect:/collections?error=UserNotFound";
            }

            collectionService.saveCollection(updatedCollection);
        } else {
            return "redirect:/collections?error=CollectionNotFound";
        }
        return "redirect:/collections";
    }


    @GetMapping("/delete/{id}")
    public String deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return "redirect:/collections";
    }
}
