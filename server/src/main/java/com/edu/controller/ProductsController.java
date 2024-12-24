package com.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.pojo.Products;
import com.edu.service.IProductsService;
import com.edu.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private IProductsService iProductsService;
    @PostMapping("/add")
    public RespBean addProduct(@RequestBody Products products){
        boolean isSaved = iProductsService.addProduct(products);
        if(isSaved){
            return RespBean.success("商品添加成功");
        }
        return RespBean.error("商品添加失败");
    }

    @GetMapping("/getAll")
    public List<Products> getAllProducts(){
        return iProductsService.list();
    }

    @PutMapping("/update")
    public RespBean updateProduct(@RequestParam String name,@RequestParam BigDecimal price){
        Products existingProduct = iProductsService.getOne(new QueryWrapper<Products>().eq("name",name));
        if(existingProduct == null){
            return RespBean.error("商品不存在，更新失败");
        }
        existingProduct.setPrice(price);
        if(iProductsService.updateById(existingProduct)){
            return RespBean.success("商品价格更新成功");
        }
        return RespBean.error("商品价格更新失败");
    }

    @DeleteMapping("/delete")
    public RespBean deleteProduct(@RequestParam String name){
        boolean isRemoved = iProductsService.removeByName(name);
        if(isRemoved){
            return RespBean.success("删除商品成功");
        }
        return RespBean.error("没有该商品，删除失败！");
    }

    @DeleteMapping("/decreaseNumber")
    public RespBean decreaseNumber(@RequestParam String name){
        boolean isUpdated = iProductsService.decreaseNumberByName(name);
        if (isUpdated) {
            return RespBean.success("商品减少成功");
        }
        return RespBean.error("商品减少失败");
    }
}

