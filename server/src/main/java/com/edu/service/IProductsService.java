package com.edu.service;

import com.edu.pojo.Products;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
public interface IProductsService extends IService<Products> {

    boolean removeByName(String name);

    boolean addProduct(Products products);

    boolean decreaseNumberByName(String name);
}
