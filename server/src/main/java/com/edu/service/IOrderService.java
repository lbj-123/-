package com.edu.service;

import com.edu.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.pojo.Shopping;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
public interface IOrderService extends IService<Order> {

    Order generateOrderFromCart(List<Shopping> shoppingList);
}
