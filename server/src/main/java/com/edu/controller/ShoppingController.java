package com.edu.controller;


import com.edu.pojo.Shopping;
import com.edu.service.IShoppingService;
import com.edu.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    private IShoppingService iShoppingService;
    @PostMapping("/add")
    public ResponseEntity<String> addShopping(@RequestBody Shopping shopping){
        boolean isSaved = iShoppingService.addShopping(shopping);
        if(isSaved){
            return ResponseEntity.ok("购物项添加成功！");
        }
        return ResponseEntity.status(500).body("添加购物项失败！");
    }
    @DeleteMapping("/deleteByName")
    public RespBean deleteShoppingByName(@RequestParam String productName){
        boolean isRemoved = iShoppingService.removeByShopName(productName);
        if(isRemoved){
            return RespBean.success("删除商品成功");
        }
        return RespBean.error("没有该商品，删除失败！");
    }

    @DeleteMapping("/deleteNumber")
    public RespBean decreaseNumber(@RequestParam String productName) {
        boolean isUpdated = iShoppingService.decreaseNumberByName(productName);
        if (isUpdated) {
            return RespBean.success("商品数量减少成功");
        }
        return RespBean.error("商品减少失败");
    }

    @GetMapping("/getAll")
    public List<Shopping> getAllShoppings(){
        return iShoppingService.list();
    }

    @GetMapping("/generateSumPrice")
    public RespBean sumPrice(){
        BigDecimal totalPrice = iShoppingService.calculateTotalPrice();
        if (totalPrice == null) {
            return RespBean.error("购物车为空，总价为零");
        }
        return RespBean.success("购物车总价计算成功", totalPrice);
    }
}
