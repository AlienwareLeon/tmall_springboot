package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository <Category,Integer> {
    /*
    * Category表名,Integer表里的主键类型
    * */
}
