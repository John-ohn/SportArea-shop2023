package com.sportArea.service;

import java.util.List;

import com.sportArea.entity.CategoryUa;
import com.sportArea.entity.dto.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> findAll();

	CategoryDTO findById(Long categoryId);

}
