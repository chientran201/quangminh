package com.chientran.application.service;

import com.chientran.application.entity.CartProduct;

import java.util.List;

public interface CartProductService {
    public CartProduct create(int quantity, String productId, long cartId, int size);
    public void delete(long id);
    public List<CartProduct> getAllById(Long id);
}
