package com.malick.ecommerce.repository;

import com.malick.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "productsCategory", path = "categories")
@CrossOrigin
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {



}
