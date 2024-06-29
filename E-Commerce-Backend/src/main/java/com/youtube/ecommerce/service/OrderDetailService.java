package com.youtube.ecommerce.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.OrderDetailDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED = "Placed";
    private static final String KEY = "rzp_test_00p7x0b1xTiKDq";
    private static final String KEY_SECRET = "qaDWkgREx1y1XYWkHnDe97km";
    private static final String CURRENCY = "INR";


    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CartDao cartDao;

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout){
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).orElseThrow(() -> new RuntimeException("User not found"));

        for (OrderProductQuantity o : productQuantityList){
            Product product = productDao.findById(o.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId()
            );
            orderDetailDao.save(orderDetail);
        }

        // Empty the cart if not a single product checkout
        if (!isSingleProductCheckout){
            List<Cart> carts = cartDao.findByUser(user);
            carts.forEach(cart -> cartDao.deleteById(cart.getCartId()));
        }
    }

    public List<OrderDetail> getOrderDetails(){
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public TransactionDetails createTransaction(Double amount){
        //amount
        //currency
        //key
        //secret key
        //These are required to create razorpay payment gateway
        //Razorpay considers smallest unit of a currency that's why we need to multiply with 100
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100));
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
            Order order =  razorpayClient.orders.create(jsonObject);
            return prepareTransactionDetails(order);
        }catch (Exception e){
            System.out.printf(e.getMessage());
        }
        return null;
    }

    public TransactionDetails prepareTransactionDetails(Order order){
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
        return transactionDetails;
    }
}