package com.dailycodework.dreamshops.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.dailycodework.dreamshops.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByName(String name);

    Category findBy(Long id);

    boolean existsByName(String name);
}
