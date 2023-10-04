package com.sportArea.service;

import com.sportArea.entity.TargetCategory;

import java.util.List;

public interface TargetCategoryService {

    List<TargetCategory> findAll();

    TargetCategory findById(Long categoryId);

    void save(TargetCategory categoryName );

    void delete (Long categoryId);
}
