package com.chientran.application.controller.shop;

import com.chientran.application.entity.Cart;
import com.chientran.application.entity.Product;
import com.chientran.application.entity.User;
import com.chientran.application.service.CartService;
import com.chientran.application.service.ProductService;
import com.chientran.application.service.UserService;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        String username = principal.getName();
        User user = userService.findByEmail(username);
        System.out.println("usename:  " + user);
        Cart cart = user.getCart();
        if(cart == null){
            model.addAttribute("check", "No item in your cart");
        }
        session.setAttribute("totalItems", cart.getTotalItems());
        model.addAttribute("subTotal", cart.getTotalPrices());
        model.addAttribute("shoppingCart", cart);
        return "shop/cart";
    }


    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") String productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpServletRequest request){

        Product product = productService.getProductById(productId);
        String username = principal.getName();
        User user = userService.findByEmail(username);

        Cart cart = cartService.addItemToCart(product, quantity, user);
        return "redirect:" + request.getHeader("Referer");

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


    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemFromCart(@RequestParam("id") String productId,
                                     Model model,
                                     Principal principal){

            String username = principal.getName();
            User user = userService.findByEmail(username);
            Product product = productService.getProductById(productId);
            Cart cart = cartService.deleteItemFromCart(product, user);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";


    }
}
