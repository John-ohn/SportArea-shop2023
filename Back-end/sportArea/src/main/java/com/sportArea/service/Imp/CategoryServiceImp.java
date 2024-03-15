package com.sportArea.service.Imp;

import com.sportArea.dao.CategoryRepository;
import com.sportArea.entity.CategoryEn;
import com.sportArea.entity.CategoryUa;
import com.sportArea.entity.SubCategoryEn;
import com.sportArea.entity.SubCategoryUa;
import com.sportArea.entity.dto.CategoryDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    public List<CategoryDTO> findAll(){

        List<CategoryUa> categoryUaList = categoryRepository.findAll();

        if (!categoryUaList.isEmpty()) {
            List<CategoryDTO> categoryList = categoryUaList.stream()
                    .sorted(Comparator.comparing(CategoryUa::getCategoryName))
                    .map(this::createCategoryDTOFromCategory)
                    .toList();
            logger.info("From BannerServiceImp method -findAll- return List of Banners.");

            return categoryList;
        } else {
            logger.warn("From CategoryServiceImp method -findAll- send war message " +
                    "(Don't find any Category. Categories list is empty. ({}))", HttpStatus.NOT_FOUND);
            throw new GeneralException("Don't find any Category. Categories list is empty.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public CategoryDTO findById(Long categoryId){

        Optional<CategoryUa> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            logger.info("From BannerServiceImp method -findAll- return List of Banners.");

            return createCategoryDTOFromCategory(category.get());
        } else {
            logger.warn("From CategoryServiceImp method -categoryId- send war message " +
                    "(Don't find any Category with id: {}. ({}))", categoryId, HttpStatus.NOT_FOUND);
            throw new GeneralException("Don't find any Category with id: "+ categoryId + ".", HttpStatus.NOT_FOUND);
        }
    }



    private CategoryDTO createCategoryDTOFromCategory(CategoryUa categoryUa) {
        categoryUa.setSubCategories(orderSubCategoryUa(categoryUa.getSubCategories()));


        CategoryEn categoryEn= categoryUa.getCategoryEn();
        categoryEn.setSubCategoriesEn(orderSubCategoryEn(categoryUa.getCategoryEn().getSubCategoriesEn()));
        categoryUa.setCategoryEn(null);

        return CategoryDTO.builder()

                .categoryId(categoryUa.getCategoryId())
                .categoryUa(categoryUa)
                .categoryEn(categoryEn)
                .build();

    }

    private List<SubCategoryUa> orderSubCategoryUa(List<SubCategoryUa> list){
        return   list.stream().sorted(Comparator.comparing(SubCategoryUa::getSubCategoryName)).toList();
    }
    private List<SubCategoryEn> orderSubCategoryEn(List<SubCategoryEn> list){
        return   list.stream().sorted(Comparator.comparing(SubCategoryEn::getSubCategoryName))
                .distinct()
                .toList();
    }
}
