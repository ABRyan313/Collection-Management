package in.sp.itransition.controller;

import in.sp.itransition.model.Tag;
import in.sp.itransition.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public String getAllTags(Model model) {
        model.addAttribute("tags", tagService.getAllTags());
        return "tag/list";
    }

    @GetMapping("/{id}")
    public String getTag(@PathVariable Long id, Model model) {
        Optional<Tag> tag = tagService.getTagById(id);
        if (tag.isPresent()) {
            model.addAttribute("tag", tag.get());
            return "tag/detail";
        } else {
            return "redirect:/tags";
        }
    }

    @PostMapping
    public String createTag(@ModelAttribute Tag tag) {
        tagService.saveTag(tag);
        return "redirect:/tags";
    }

    @DeleteMapping("/{id}")
    public String deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/tags";
    }
}
