package com.adaptive.repository;


import com.adaptive.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findByUuid(String uuid);

}
