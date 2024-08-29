package in.sp.itransition.controller;

import in.sp.itransition.model.Collection;
import in.sp.itransition.model.User;
import in.sp.itransition.service.CollectionService;
import in.sp.itransition.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    	
        collection.setId(1L);
        collection.setImageUrl("ab");
        User user1 = userService.findByEmail("abir4044@diu.edu.bd");
        collection.setUser(user1);
        
    	collectionService.saveCollection(collection);
        return "redirect:/collections";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Collection> collection = collectionService.getCollectionById(id);
        model.addAttribute("collection", collection);
        return "edit-collection";
    }

    @PostMapping("/edit/{id}")
    public String updateCollection(@PathVariable Long id, @ModelAttribute Collection collection) {
        collection.setId(id);
        collectionService.saveCollection(collection);
        return "redirect:/collections";
    }

    @GetMapping("/delete/{id}")
    public String deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return "redirect:/collections";
    }
}
