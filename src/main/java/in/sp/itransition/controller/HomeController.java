package in.sp.itransition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.sp.itransition.service.CollectionService;
import in.sp.itransition.service.ItemService;
import in.sp.itransition.service.TagService;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private TagService tagService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("recentItems", itemService.getRecentItems());
        model.addAttribute("topCollections", collectionService.getTopCollections());
        model.addAttribute("tags", tagService.getAllTags());
        return "home";
    }
    
	/*
	 * @GetMapping("/collections") public String showCollectionsManagementPage(Model
	 * model) { model.addAttribute("collections",
	 * collectionService.getAllCollections()); return "collections"; }
	 */
}
