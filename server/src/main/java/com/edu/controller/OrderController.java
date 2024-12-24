package com.edu.controller;


import com.edu.pojo.Order;
import com.edu.pojo.Shopping;
import com.edu.service.IOrderService;
import com.edu.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IShoppingService shoppingService;

    @GetMapping("/generateOrder")
    public ResponseEntity<Order> generateOrder() {
        List<Shopping> shoppingList = shoppingService.getShoppingList();
        if (shoppingList == null || shoppingList.isEmpty()) {
            return ResponseEntity.status(404).body(null);  // 没有购物项
        }
        Order order = orderService.generateOrderFromCart(shoppingList);
        if (order != null) {
            return ResponseEntity.ok(order);  // 返回生成的订单
        }
        return ResponseEntity.status(500).body(null);  // 生成订单失败
    }

}
