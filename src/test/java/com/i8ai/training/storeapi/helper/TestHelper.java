package com.i8ai.training.storeapi.helper;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;

public class TestHelper {
    public static final Long PRODUCT_A_ID = 0xAL;
    public static final String PRODUCT_A_CODE = "a_product_code";
    public static final String PRODUCT_A_NAME = "a_product_name";
    public static final String PRODUCT_A_MEASURE = "a_product_measure";
    public static final Long PRODUCT_B_ID = 0xBL;
    public static final String PRODUCT_B_CODE = "b_product_code";
    public static final String PRODUCT_B_NAME = "b_product_name";
    public static final String PRODUCT_B_MEASURE = "b_product_measure";
    public static final Long SHOP1_ID = 0x1L;
    public static final String SHOP1_NAME = "name of shop 1";
    public static final String SHOP1_ADDRESS = "address of shop 1";
    public static final Long SHOP2_ID = 0x2L;
    public static final String SHOP2_NAME = "name of shop 2";
    public static final String SHOP2_ADDRESS = "address of shop 2";
    public static final double SALE1A_SPENT_AMOUNT = 300.0;
    public static final double SALE1A_INCOME_AMOUNT = 500.0;
    public static final double SALE1B_SPENT_AMOUNT = 200.0;
    public static final double SALE1B_INCOME_AMOUNT = 450.0;
    public static final double SALE2A_SPENT_AMOUNT = 350.0;
    public static final double SALE2A_INCOME_AMOUNT = 400.0;
    public static final double SALE2B_SPENT_AMOUNT = 250.0;
    public static final double SALE2B_INCOME_AMOUNT = 550.0;
    public static final double PRODUCT_A_SPENT_AMOUNT = SALE1A_SPENT_AMOUNT  + SALE2A_SPENT_AMOUNT;
    public static final double PRODUCT_A_INCOME_AMOUNT = SALE1A_INCOME_AMOUNT + SALE2A_INCOME_AMOUNT;
    public static final double PRODUCT_B_SPENT_AMOUNT = SALE1B_SPENT_AMOUNT + SALE2B_SPENT_AMOUNT;
    public static final double PRODUCT_B_INCOME_AMOUNT = SALE1B_INCOME_AMOUNT + SALE2B_INCOME_AMOUNT;
    public static final double SHOP1_SPENT_AMOUNT = SALE1A_SPENT_AMOUNT +SALE1B_SPENT_AMOUNT;
    public static final double SHOP1_INCOME_AMOUNT = SALE1A_INCOME_AMOUNT +SALE1B_INCOME_AMOUNT;
    public static final double SHOP2_SPENT_AMOUNT = SALE2A_SPENT_AMOUNT +SALE2B_SPENT_AMOUNT;
    public static final double SHOP2_INCOME_AMOUNT = SALE2A_INCOME_AMOUNT +SALE2B_INCOME_AMOUNT;
    public static final double SPENT_AMOUNT = PRODUCT_A_SPENT_AMOUNT + PRODUCT_B_SPENT_AMOUNT;
    public static final double INCOME_AMOUNT = PRODUCT_A_INCOME_AMOUNT + PRODUCT_B_INCOME_AMOUNT;
    public static final Product PRODUCT_A = new Product(PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    public static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);
    public static final Shop SHOP1 = new Shop(SHOP1_ID, SHOP1_NAME, SHOP1_ADDRESS, null);
    public static final Shop SHOP2 = new Shop(SHOP2_ID, SHOP2_NAME, SHOP2_ADDRESS, null);
}
