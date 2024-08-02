package com.rafaelandrade.backend.services.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateDiscount {

    public static BigDecimal calculateDiscountInPercentage(BigDecimal originalPrice, BigDecimal currentPrice){
        BigDecimal discount = originalPrice.subtract(currentPrice).divide(originalPrice, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100.00));
        return discount;
    }

    public static BigDecimal calculateDiscountInMoney(BigDecimal originalPrice, BigDecimal discountPercentage){
        BigDecimal discountInMoney = originalPrice.multiply(discountPercentage.divide(BigDecimal.valueOf(100)));
        BigDecimal currentPrice = originalPrice.subtract(discountInMoney);
        return currentPrice;
    }

    public static BigDecimal calculateDiscountInMoneyWithOriginalPriceAndCurrentPrice(BigDecimal originalPrice, BigDecimal currentPrice){
        BigDecimal discount = originalPrice.subtract(currentPrice);
        return discount;
    }
}
