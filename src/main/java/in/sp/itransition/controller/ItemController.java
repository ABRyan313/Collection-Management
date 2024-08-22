package in.sp.itransition.controller;



import in.sp.itransition.model.Item;
import in.sp.itransition.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String getItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "item/list";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id).orElse(null));
        return "item/detail";
    }

    @PostMapping
    public String createItem(@ModelAttribute Item item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/items";
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
}
