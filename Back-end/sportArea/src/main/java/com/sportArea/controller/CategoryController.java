package com.sportArea.controller;

import com.sportArea.entity.TargetCategory;
import com.sportArea.service.TargetCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorys")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final TargetCategoryService targetCategoryService;

    @Autowired
    public CategoryController(TargetCategoryService targetCategoryService) {
        this.targetCategoryService = targetCategoryService;
    }

    @GetMapping("/targets")
    public List<TargetCategory> getAllTargetCategory() {

        return targetCategoryService.findAll();
    }

    @GetMapping("/targets/{categoryId}")
    public TargetCategory getTargetCategoryById(@PathVariable("categoryId") Long categoryId) {
        TargetCategory category = targetCategoryService.findById(categoryId);

        return category;
    }

    @PostMapping("/targets")
    public ResponseEntity<String> saveTargetCategory(@RequestBody TargetCategory category) {

        targetCategoryService.save(category);

        return ResponseEntity.ok("Category was saved on target category successfully");
    }

    @DeleteMapping("/targets/{categoryId}")
    public ResponseEntity<String> deleteTargetCategory(@PathVariable("categoryId") Long categoryId) {

        targetCategoryService.delete(categoryId);

        return ResponseEntity.ok("Category was deleted from target category successfully");
    }
}
