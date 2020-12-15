package com.depromeet.team5.ui;

import com.depromeet.team5.domain.store.MenuCategory;
import com.depromeet.team5.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    @PostMapping("/menu-categories")
    public String createMenuCategory(@RequestParam @Valid @Length(min = 1, max = 255) String enumName) {
        menuCategoryService.create(enumName);
        return "redirect:menu-categories";
    }

    @GetMapping("/menu-categories")
    public String createMenuCategory(Pageable pageable, Model model) {
        Page<MenuCategory> page = menuCategoryService.getMenuCategories(pageable);
        model.addAttribute("menuCategories", page.getContent());
        return "menu-categories/list";
    }
}
