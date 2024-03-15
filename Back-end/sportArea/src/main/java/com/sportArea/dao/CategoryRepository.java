package com.sportArea.dao;

import com.sportArea.entity.CategoryUa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;


import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryUa, Long> {

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT DISTINCT c FROM CategoryUa c LEFT join fetch c.subCategories s " +
            "left join fetch c.categoryEn  e ")
    List<CategoryUa> findAll();

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c From CategoryUa c LEFT join fetch c.subCategories " +
            "left join fetch c.categoryEn where c.categoryId=:categoryId ")
    Optional<CategoryUa> findById(@Param("categoryId") Long categoryId);

}
