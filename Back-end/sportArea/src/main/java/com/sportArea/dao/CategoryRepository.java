package com.sportArea.dao;

import com.sportArea.entity.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT DISTINCT c FROM Category c LEFT join fetch c.subCategories")
    List<Category> findAll();

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c From Category c LEFT join fetch c.subCategories where c.categoryId=:categoryId ")
    Optional<Category> findById(@Param("categoryId") Long categoryId);

}
