package com.sportArea.controller;

import com.sportArea.entity.CategoryUa;
import com.sportArea.entity.TargetCategory;
import com.sportArea.entity.dto.CategoryDTO;
import com.sportArea.entity.dto.GeneralResponse;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.CategoryService;
import com.sportArea.service.TargetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final GeneralLogg generalLogg;

    private final TargetCategoryService targetCategoryService;

    private  final CategoryService categoryService;

    @Autowired
    public CategoryController(TargetCategoryService targetCategoryService,
    		CategoryService categoryService,
                              GeneralLogg generalLogg) {
        this.targetCategoryService = targetCategoryService;
        this.categoryService = categoryService;
        this.generalLogg = generalLogg;
    }

    @GetMapping()
    public ResponseEntity <List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> categoryUaList = categoryService.findAll();

        generalLogg.getLoggerControllerInfo("CategoryController",
                "getAllCategories",
                "/categorys",
                "List of Target Category");

        return ResponseEntity.ok(categoryUaList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        CategoryDTO categoryUa = categoryService.findById(categoryId);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "getCategoryById",
                "/categorys/{categoryId}",
                "Category with categoryId " + categoryId);

        return ResponseEntity.ok(categoryUa);
    }

    @GetMapping("/targets")
    public ResponseEntity<List<TargetCategory>> getAllTargetCategory() {

        List<TargetCategory> categoryList = targetCategoryService.findAll();

        generalLogg.getLoggerControllerInfo("CategoryController",
                "getAllTargetCategory",
                "/targets",
                "List of Target Category");

        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/targets/{targetCategoryId}")
    public TargetCategory getTargetCategoryById(@PathVariable("categoryId") Long categoryId) {
        TargetCategory category = targetCategoryService.findById(categoryId);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "getTargetCategoryById",
                "/targets/{categoryId}",
                "Target Category with categoryId " + categoryId);

        return category;
    }

    @PostMapping("/targets")
    public ResponseEntity<GeneralResponse> saveTargetCategory(@RequestBody TargetCategory category) {

        targetCategoryService.save(category);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "saveTargetCategory",
                "/targets",
                "message (saved on target category successfully) ");

        GeneralResponse generalResponse = new GeneralResponse(
                HttpStatus.CREATED.value(),
                "Category was saved on target category successfully");

        return ResponseEntity.ok(generalResponse);
    }

    @DeleteMapping("/targets/{targetCategoryId}")
    public ResponseEntity<GeneralResponse> deleteTargetCategory(@PathVariable("categoryId") Long categoryId) {

        targetCategoryService.delete(categoryId);

        generalLogg.getLoggerControllerInfo("CategoryController",
                "deleteTargetCategory",
                "/targets/{categoryId}",
                "message (Category was deleted ) categoryId " + categoryId);

        GeneralResponse generalResponse = new GeneralResponse(
                HttpStatus.CREATED.value(),
                "Category was deleted from target category successfully");

        return ResponseEntity.ok(generalResponse);
    }
}
