package com.chientran.application.service;

import com.chientran.application.entity.Cart;
import com.chientran.application.entity.Product;
import com.chientran.application.entity.User;


public interface CartService {

    Cart addItemToCart(Product product, int quantity, User user);

    Cart updateItemToCart(Product product,int quantity,User user);

    Cart deleteItemFromCart(Product product, User user);

//    List<CartDTO> getListCartOfPersonByStatus(int status, long userId);
//    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);
//
//    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);
//
//    ShoppingCart deleteItemFromCart(Product product, Customer customer);
}
