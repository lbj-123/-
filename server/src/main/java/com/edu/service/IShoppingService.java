package com.edu.service;

import com.edu.pojo.Shopping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
public interface IShoppingService extends IService<Shopping> {

    boolean addShopping(Shopping shopping);

    List<Shopping> getShoppingList();

    boolean removeByShopName(String productName);

    boolean decreaseNumberByName(String productName);

    BigDecimal calculateTotalPrice();

}
