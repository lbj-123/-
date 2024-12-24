package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.pojo.Products;
import com.edu.mapper.ProductsMapper;
import com.edu.pojo.Shopping;
import com.edu.pojo.Users;
import com.edu.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @Autowired
    private ProductsMapper productsMapper;
    @Override
    public boolean removeByName(String name) {
        QueryWrapper<Products> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Products products = this.getOne(queryWrapper);
        if (products != null) {
            return this.remove(queryWrapper);
        }
        return false;
    }

    @Override
    public boolean addProduct(Products products) {
        Products existProduct = productsMapper.selectOne(new QueryWrapper<Products>()
                .eq("name",products.getName()));
        if(existProduct!=null){
            existProduct.setNumber(existProduct.getNumber()+products.getNumber());
            return productsMapper.updateById(existProduct) > 0;
        }
        return productsMapper.insert(products)>0;
    }

    @Override
    public boolean decreaseNumberByName(String name) {
        // 根据商品名称查询商品
        Products product = productsMapper.selectOne(new QueryWrapper<Products>().eq("name", name));
        // 如果商品存在且数量大于 0
        if (product != null && product.getNumber()>=0) {
            // 如果商品数量减为 0，则删除该商品
            if (product.getNumber() == 0) {
                // 删除商品
                return productsMapper.delete(new QueryWrapper<Products>().eq("name", name)) > 0;
            }
            // 商品数量减 1
            product.setNumber(product.getNumber() - 1);

            // 如果数量仍然大于 0，则更新商品
            return productsMapper.updateById(product) > 0;
        }
        // 商品不存在或数量为 0，返回 false
        return false;
    }

}

