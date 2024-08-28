package in.sp.itransition.controller;


import in.sp.itransition.model.Collection;
import in.sp.itransition.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping
    public String getCollections(Model model) {
        model.addAttribute("collections", collectionService.getAllCollections());
        return "collection/list";
    }

    @GetMapping("/{id}")
    public String getCollection(@PathVariable Long id, Model model) {
        model.addAttribute("collection", collectionService.getCollectionById(id));
        return "collection/detail";
    }

    @PostMapping
    public String createCollection(@ModelAttribute Collection collection) {
        collectionService.saveCollection(collection);
        return "redirect:/collections";
    }

    @DeleteMapping("/{id}")
    public String deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return "redirect:/collections";
    }
}