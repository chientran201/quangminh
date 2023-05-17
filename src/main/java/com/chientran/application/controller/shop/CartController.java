package com.chientran.application.controller.shop;

import com.chientran.application.entity.Cart;
import com.chientran.application.entity.CartProduct;
import com.chientran.application.entity.Product;
import com.chientran.application.entity.User;
import com.chientran.application.exception.NotFoundException;
import com.chientran.application.model.dto.DetailProductInfoDTO;
import com.chientran.application.service.CartProductService;
import com.chientran.application.service.CartService;
import com.chientran.application.service.ProductService;
import com.chientran.application.service.UserService;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;


    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        String username = principal.getName();
        User user = userService.findByEmail(username);
        Cart cart = user.getCart();
        List<CartProduct> cartProducts = cartProductService.getAllById(cart.getId());

        if(cart == null){
            model.addAttribute("check", "No item in your cart");
        }
        session.setAttribute("totalItems", cart.getTotalItems());
        model.addAttribute("subTotal", cart.getTotalPrices());
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("cartId",cart.getId());
        model.addAttribute("productCart",cartProducts);
        return "shop/cart";
    }


    @GetMapping("/add-to-cart")
    public String addItemToCart(Model model, @RequestParam String id,@RequestParam int size,Principal principal) {
        String username = principal.getName();
        User user = userService.findByEmail(username);
        Cart cart = cartService.findByUserId(user.getId());
        CartProduct cartProduct = cartProductService.create(1,id,cart.getId(),size);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") String productId,
                             Model model,
                             Principal principal
    ){
            String username = principal.getName();
            User user = userService.findByEmail(username);
            Product product = productService.getProductById(productId);
            Cart cart = cartService.updateItemToCart(product, quantity, user);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
//        }

    }

    @GetMapping("/delete-cartItem/{id}")
    public String delete(@PathVariable long id) {
        cartProductService.delete(id);
        return "redirect:/cart";
    }


//    @DeleteMapping("/delete-cartItem/{id}")
//    public ResponseEntity<Object> deleteCartProduct(@PathVariable long id) {
//        cartProductService.delete(id);
//
//        return ResponseEntity.ok("Xóa thành công!");
//    }


//    @RequestMapping(value = "/update-cart", method = RequestMethod.GET, params = "action=delete")
//    public String deleteItemFromCart(@RequestParam long id,
//                                     Model model
//                                     ){
//            cartProductService.delete(id);
//            return "redirect:/cart";
//
//
//    }

}
