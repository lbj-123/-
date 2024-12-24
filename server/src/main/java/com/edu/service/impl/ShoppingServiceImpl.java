package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.pojo.Products;
import com.edu.pojo.Shopping;
import com.edu.mapper.ShoppingMapper;
import com.edu.service.IProductsService;
import com.edu.service.IShoppingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
@Service
public class ShoppingServiceImpl extends ServiceImpl<ShoppingMapper, Shopping> implements IShoppingService {


    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private IShoppingService ShoppingService;
    @Autowired
    private IProductsService productsService;

    @Override
    public boolean addShopping(Shopping shopping) {
        Shopping existShopping = shoppingMapper.selectOne(new QueryWrapper<Shopping>()
                .eq("product_id", shopping.getProductId()));
        if (existShopping != null) {
            existShopping.setProductNumber(existShopping.getProductNumber() + shopping.getProductNumber());
            return shoppingMapper.updateById(existShopping) > 0;
        }
        return shoppingMapper.insert(shopping) > 0;
    }

    @Override
    public List<Shopping> getShoppingList() {
        return ShoppingService.list();
    }

    @Override
    public boolean removeByShopName(String productName) {
        QueryWrapper<Shopping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productName", productName);
        Shopping shopping = this.getOne(queryWrapper);
        if (shopping != null) {
            return this.remove(queryWrapper);
        }
        return false;
    }

    @Override
    public boolean decreaseNumberByName(String productName) {
        // 根据商品名称查询购物车中该商品
        Shopping shopping = shoppingMapper.selectOne(new QueryWrapper<Shopping>().eq("productName", productName));

        if (shopping != null && shopping.getProductNumber() >= 0) {
            // 如果商品数量减为 0，则删除该商品
            if (shopping.getProductNumber() == 0) {
                // 删除商品
                return shoppingMapper.delete(new QueryWrapper<Shopping>().eq("productName", productName)) > 0;
            }
            // 商品数量减 1
            shopping.setProductNumber(shopping.getProductNumber() - 1);

            // 如果数量仍然大于 0，则更新商品
            return shoppingMapper.updateById(shopping) > 0;
        }
        return false; // 如果没有该商品，或者商品数量为0
    }

    @Override
    public BigDecimal calculateTotalPrice() {
        BigDecimal sumPrice = BigDecimal.ZERO; // 初始化总价为零
        List<Shopping> shoppingList = ShoppingService.getShoppingList();
        if (shoppingList == null || shoppingList.isEmpty()) {
            return null; // 购物车为空，返回null
        }
        for (Shopping shopping : shoppingList) {
            // 获取商品信息
            Products product = productsService.getById(shopping.getProductId());
            if (product != null) {
                // 计算该商品的价格
                BigDecimal productTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(shopping.getProductNumber()));
                sumPrice = sumPrice.add(productTotalPrice);  // 累加总价
            }
        }
        return sumPrice;
    }
}
