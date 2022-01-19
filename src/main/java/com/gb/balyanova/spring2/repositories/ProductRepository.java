package com.gb.balyanova.spring2.repositories;


//import com.gb.balyanova.spring2.entities.Category;
import com.gb.balyanova.spring2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByPriceBetween(Integer min, Integer max);

    Optional<Product> findByTitle(String title);

    @Query("select p from Product p where p.price < 100")
    List<Product> findLowPriceProducts(Integer min);

    @Query("select p from Product p where price > 1000")
    List<Product> findMoreThanValue(Integer max);

    @Modifying
    @Query("update Product p set p.title = :title where p.id = :id ")
    void updateProduct(Long id, String title);

    @Query("select p from Product p where p.id = ?1")
    Optional<Product> findByIdQuery(Long id);

//    @Query("select p from Product p where p.id = :id")
//    Optional<Product> findByIdQuery(@Param ("id") Long id);


//    @Query("select p from Product p where [.title = ?1") //поиск по первому аргументу
//    Integer hqlGetPriceByTitle(String title);
//
//    @Query(value = "select price from product where name = :title", nativeQuery = true) //поиск по первому аргументу
//    Integer nativeSqlGetPriceByTitle(String title);
}
