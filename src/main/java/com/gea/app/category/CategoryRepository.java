package com.gea.app.category;

import com.gea.app.category.entity.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
