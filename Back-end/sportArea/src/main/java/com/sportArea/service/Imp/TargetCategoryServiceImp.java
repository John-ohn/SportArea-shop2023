package com.sportArea.service.Imp;

import com.sportArea.dao.TargetCategoryRepository;
import com.sportArea.entity.TargetCategory;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.TargetCategoryService;
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
public class TargetCategoryServiceImp implements TargetCategoryService {

    Logger logger = LoggerFactory.getLogger(TargetCategoryServiceImp.class);
    private final TargetCategoryRepository targetCategoryRepository;

    @Autowired
    public TargetCategoryServiceImp(TargetCategoryRepository targetCategoryRepository) {
        this.targetCategoryRepository = targetCategoryRepository;
    }

    @Override
    public List<TargetCategory> findAll() {
        List<TargetCategory> list = targetCategoryRepository.findAll();
        logger.info("From TargetCategoryServiceImp methods - findAll - return list of TargetCategory");
        return list;
    }

    @Override
    public TargetCategory findById(Long categoryId) {
        Optional<TargetCategory> category = targetCategoryRepository.findById(categoryId);
        if (category.isPresent()) {
            logger.info("From TargetCategoryServiceImp methods - findById - return TargetCategory");
            return category.get();
        } else {
            throw new GeneralException("TargetCategory with this categoryId :" + categoryId + " don't exist ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void save(TargetCategory categoryName) {
        if (!(categoryName == null)){
            targetCategoryRepository.save(categoryName);
            logger.info("From TargetCategoryServiceImp methods - save - save new object TargetCategory");
        }else {
            throw new GeneralException("TargetCategory object is empty ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Long categoryId) {
        Optional<TargetCategory> category = targetCategoryRepository.findById(categoryId);
        if (category.isPresent()) {
            targetCategoryRepository.delete(category.get());
            logger.info("From TargetCategoryServiceImp methods - delete - delete object TargetCategory with categoryId : {}  ", categoryId );
        }else{
            throw new GeneralException("TargetCategory with this categoryId :" + categoryId + " don't exist ", HttpStatus.NOT_FOUND);
        }

    }
}
