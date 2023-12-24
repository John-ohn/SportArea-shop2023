package com.sportArea.controller;

import com.sportArea.entity.TargetCategory;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.TargetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorys")
public class CategoryController {

    private final GeneralLogg generalLogg;

    private final TargetCategoryService targetCategoryService;

    @Autowired
    public CategoryController(TargetCategoryService targetCategoryService, GeneralLogg generalLogg) {
        this.targetCategoryService = targetCategoryService;
        this.generalLogg = generalLogg;
    }

    @GetMapping("/targets")
    public List<TargetCategory> getAllTargetCategory() {

        List<TargetCategory> categoryList = targetCategoryService.findAll();

        generalLogg.getLoggerControllerInfo("CategoryController",
                "getAllTargetCategory",
                "/targets",
                "List of Target Category");

        return categoryList;
    }

    @GetMapping("/targets/{categoryId}")
    public TargetCategory getTargetCategoryById(@PathVariable("categoryId") Long categoryId) {
        TargetCategory category = targetCategoryService.findById(categoryId);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "getTargetCategoryById",
                "/targets/{categoryId}",
                "Target Category with categoryId " + categoryId);

        return category;
    }

    @PostMapping("/targets")
    public ResponseEntity<String> saveTargetCategory(@RequestBody TargetCategory category) {

        targetCategoryService.save(category);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "saveTargetCategory",
                "/targets",
                "message (saved on target category successfully) ");

        return ResponseEntity.ok("Category was saved on target category successfully");
    }

    @DeleteMapping("/targets/{categoryId}")
    public ResponseEntity<String> deleteTargetCategory(@PathVariable("categoryId") Long categoryId) {

        targetCategoryService.delete(categoryId);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "deleteTargetCategory",
                "/targets/{categoryId}",
                "message (Category was deleted ) categoryId " + categoryId);

        return ResponseEntity.ok("Category was deleted from target category successfully");
    }
}
