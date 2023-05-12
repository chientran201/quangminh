package com.chientran.application.service.impl;

import com.chientran.application.entity.Cart;
import com.chientran.application.entity.CartProduct;
import com.chientran.application.entity.Product;
import com.chientran.application.entity.User;
import com.chientran.application.repository.CartProductRepository;
import com.chientran.application.repository.CartRepository;
import com.chientran.application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addItemToCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
        }
        Set<CartProduct> cartProducts = cart.getCartProduct();
        CartProduct cartProduct = findCartProduct(cartProducts, product.getId());

        if (cartProducts == null) {
            cartProducts = new HashSet<>();
            if (cartProduct == null) {
                cartProduct = new CartProduct();
                cartProduct.setProduct(product);
                cartProduct.setTotalPrice(quantity * product.getPrice());
                cartProduct.setQuantity(quantity);
                cartProduct.setCart(cart);
                cartProducts.add(cartProduct);
                cartProductRepository.save(cartProduct);
            }
        } else {
            if (cartProduct == null) {
                cartProduct = new CartProduct();
                cartProduct.setProduct(product);
                cartProduct.setTotalPrice(quantity * product.getPrice());
                cartProduct.setQuantity(quantity);
                cartProduct.setCart(cart);
                cartProducts.add(cartProduct);
                cartProductRepository.save(cartProduct);
            } else {
                cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                cartProduct.setTotalPrice(cartProduct.getTotalPrice() + ( quantity * product.getPrice()));
                cartProductRepository.save(cartProduct);
            }
        }

        cart.setCartProduct(cartProducts);

        int totalItems = totalItems(cart.getCartProduct());
        double totalPrice = totalPrice(cart.getCartProduct());

        cart.setTotalPrices(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItemToCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();

        Set<CartProduct> cartItems = cart.getCartProduct();

        CartProduct item = findCartProduct(cartItems, product.getId());

        item.setQuantity(quantity);
        item.setTotalPrice(quantity * product.getPrice());

        cartProductRepository.save(item);

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);

        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);

        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteItemFromCart(Product product, User user) {
        Cart cart = user.getCart();

        Set<CartProduct> cartProducts = cart.getCartProduct();

        CartProduct item = findCartProduct(cartProducts, product.getId());

        cartProducts.remove(item);

        cartProductRepository.delete(item);

        double totalPrice = totalPrice(cartProducts);
        int totalItems = totalItems(cartProducts);

        cart.setCartProduct(cartProducts);
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);

        return cartRepository.save(cart);
    }
        private CartProduct findCartProduct(Set<CartProduct> cartProducts,String productId) {
            if (cartProducts == null) {
                return null;
            }
            CartProduct cartProduct = null;

            for (CartProduct product : cartProducts) {
                if (product.getProduct().getId() == productId) {
                    cartProduct = product;
                }
            }
            return cartProduct;
        }

    private int totalItems(Set<CartProduct> cartProducts){
        int totalItems = 0;
        for(CartProduct product : cartProducts){
            totalItems += product.getId();
        }
        return totalItems;
    }

    private double totalPrice(Set<CartProduct> cartProducts){
        double totalPrice = 0.0;

        for(CartProduct item : cartProducts){
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
    }
}
