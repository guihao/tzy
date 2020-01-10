package com.hd.mall.domain.repo;

import com.hd.mall.domain.Lable_Info;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface LableRepo extends CrudRepository<Lable_Info,Integer> {

    @Query(value = "select * from lable_info where lable_parentid =?1", nativeQuery = true)
    List<Lable_Info> findByParentId(int id);

    @Query(value = "select * from lable_info where lable_level ='1'", nativeQuery = true)
    List<Lable_Info> findByLevel();
}
