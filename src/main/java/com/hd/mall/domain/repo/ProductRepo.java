package com.hd.mall.domain.repo;

import com.hd.mall.domain.Lable_Info;
import com.hd.mall.domain.Product_Info;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product_Info,Integer> {
    @Query(value = "select * from product_info where lable_id =?1", nativeQuery = true)
    List<Product_Info> findByLableId(int id);
}
