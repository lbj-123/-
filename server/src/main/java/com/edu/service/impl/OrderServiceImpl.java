package com.edu.service.impl;

import com.edu.pojo.Order;
import com.edu.mapper.OrderMapper;
import com.edu.pojo.OrderItem;
import com.edu.pojo.Products;
import com.edu.pojo.Shopping;
import com.edu.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.service.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {


    @Autowired
    private IProductsService productsService;
    @Override
    public Order generateOrderFromCart(List<Shopping> shoppingList) {
        BigDecimal totalPrice = BigDecimal.ZERO;  // 初始化总价
        List<OrderItem> orderItems = new ArrayList<>();  // 存储订单商品列表
//        StringBuilder productsDetails = new StringBuilder();

        for (Shopping shopping : shoppingList) {
            // 获取商品信息
            Products product = productsService.getById(shopping.getProductId());
            if (product != null) {
                // 计算该商品的价格
                BigDecimal productTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(shopping.getProductNumber()));
                totalPrice = totalPrice.add(productTotalPrice);  // 累加总价

//                // 拼接商品信息
//                productsDetails.append("Product: ").append(product.getName())
//                        .append(", Quantity: ").append(shopping.getProductNumber())
//                        .append(", Price: ").append(product.getPrice())
//                        .append("\n");

                // 创建 OrderItem 对象
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(product.getName());
                orderItem.setProductNumber(shopping.getProductNumber());
                orderItem.setProductPrice(product.getPrice());
                orderItem.setTotalPrice(productTotalPrice);
                // 添加到订单商品列表
                orderItems.add(orderItem);
            }
        }

        // 创建并返回订单对象
        Order order = new Order();
        order.setSumPrice(totalPrice);
        order.setProducts(orderItems);
        return order;

    }
}
