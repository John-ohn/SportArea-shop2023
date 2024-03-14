package com.sportArea.service.Imp;

import com.sportArea.dao.CategoryRepository;
import com.sportArea.entity.Category;
import com.sportArea.entity.dto.CategoryDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImp implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImp.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll(){

        List<Category> categoryList = categoryRepository.findAll();

        if (!categoryList.isEmpty()) {
            logger.info("From BannerServiceImp method -findAll- return List of Banners.");

            return categoryList;
        } else {
            logger.warn("From CategoryServiceImp method -findAll- send war message " +
                    "(Don't find any Category. Categories list is empty. ({}))", HttpStatus.NOT_FOUND);
            throw new GeneralException("Don't find any Category. Categories list is empty.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Category findById(Long categoryId){

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            logger.info("From BannerServiceImp method -findAll- return List of Banners.");

            return category.get();
        } else {
            logger.warn("From CategoryServiceImp method -categoryId- send war message " +
                    "(Don't find any Category with id: {}. ({}))", categoryId, HttpStatus.NOT_FOUND);
            throw new GeneralException("Don't find any Category with id: "+ categoryId + ".", HttpStatus.NOT_FOUND);
        }
    }


    
    private CategoryDTO createCategoryDTOFromCategory(Category category) {
    	
    	return CategoryDTO.builder()
    			.categoryId(category.getCategoryId())
    			.categoryName(category.getCategoryName())
    			.subCategories(category.getSubCategories())
    			.build();
    	
    }
}
