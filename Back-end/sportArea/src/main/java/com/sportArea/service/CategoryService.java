package com.sportArea.service;

import java.util.List;

import com.sportArea.entity.Category;
import com.sportArea.entity.dto.CategoryDTO;

public interface CategoryService {
	
	List<Category> findAll();
	
	Category findById(Long categoryId);

}
