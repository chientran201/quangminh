package com.chientran.application.service.impl;

import com.chientran.application.entity.CartProduct;
import com.chientran.application.exception.InternalServerException;
import com.chientran.application.exception.NotFoundException;
import com.chientran.application.repository.CartProductRepository;
import com.chientran.application.repository.CartRepository;
import com.chientran.application.repository.ProductRepository;
import com.chientran.application.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartProduct create(int quantity, String productId, long cartId, int size) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setQuantity(quantity);
        cartProduct.setSize(size);
        cartProduct.setProduct(productRepository.findById(productId).get());
        cartProduct.setCart(cartRepository.findById(cartId).get());
        cartProduct.setTotalPrice(cartProduct.getQuantity()*(cartProduct.getProduct().getSalePrice()-150000));
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public void delete(long id) {
        Optional<CartProduct> cartProduct = cartProductRepository.findById(id);
        try {
            cartProductRepository.delete(cartProduct.get());
            System.out.println("Log: >>>>" + cartProduct.get());
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa giỏ hàng!");
        }
    }

    @Override
    public List<CartProduct> getAllById(Long id) {
        return cartProductRepository.getAllById(id);
    }


}
