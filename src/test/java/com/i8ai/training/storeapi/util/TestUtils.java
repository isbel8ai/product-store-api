package com.i8ai.training.storeapi.util;

public class TestUtils {
    public static final Long PRODUCT_A_ID = 0x0AL;
    public static final String PRODUCT_A_CODE = "a_product_code";
    public static final String PRODUCT_A_NAME = "a_product_name";
    public static final String PRODUCT_A_MEASURE = "a_product_measure";
    public static final double PRODUCT_A_COST = 2.0;
    public static final double PRODUCT_A_PRICE = 3.0;

    public static final Long PRODUCT_B_ID = 0x0BL;
    public static final String PRODUCT_B_CODE = "b_product_code";
    public static final String PRODUCT_B_NAME = "b_product_name";
    public static final String PRODUCT_B_MEASURE = "b_product_measure";
    public static final double PRODUCT_B_COST = 4.0;
    public static final double PRODUCT_B_PRICE = 6.0;

    public static final Long LOT_A_ID = 0xA0L;
    public static final Long LOT_B_ID = 0xB0L;

    public static final double LOT_A_AMOUNT = 2000;
    public static final double LOT_B_AMOUNT = 1000;

    public static final Long SHOP1_ID = 0x10L;
    public static final String SHOP1_NAME = "name of shop 1";
    public static final String SHOP1_ADDRESS = "address of shop 1";

    public static final Long SHOP2_ID = 0x20L;
    public static final String SHOP2_NAME = "name of shop 2";
    public static final String SHOP2_ADDRESS = "address of shop 2";

    public static final Long PACK1A_ID = 0x1AL;
    public static final Long PACK1B_ID = 0x1BL;
    public static final Long PACK2A_ID = 0x2AL;
    public static final Long PACK2B_ID = 0x2BL;

    public static final double PACK1A_AMOUNT = 300.0;
    public static final double PACK1B_AMOUNT = 200.0;
    public static final double PACK2A_AMOUNT = 350.0;
    public static final double PACK2B_AMOUNT = 250.0;

    public static final double SALE_1A35_AMOUNT = 150.0;
    public static final double SALE_1A40_AMOUNT = 100.0;
    public static final double SALE_1B45_AMOUNT = 50.0;
    public static final double SALE_1B50_AMOUNT = 75.0;
    public static final double SALE_2A55_AMOUNT = 180.0;
    public static final double SALE_2A60_AMOUNT = 130.0;
    public static final double SALE_2B65_AMOUNT = 120.0;
    public static final double SALE_2B70_AMOUNT = 90.0;

    public static final double PACK1A_SALES_AMOUNT = SALE_1A35_AMOUNT + SALE_1A40_AMOUNT;
    public static final double PACK1A_SALES_EXPENSES = PACK1A_SALES_AMOUNT * PRODUCT_A_COST;
    public static final double PACK1A_SALES_INCOME = PACK1A_SALES_AMOUNT * PRODUCT_A_PRICE;

    public static final double PACK1B_SALES_AMOUNT = SALE_1B45_AMOUNT + SALE_1B50_AMOUNT;
    public static final double PACK1B_SALES_EXPENSES = PACK1B_SALES_AMOUNT * PRODUCT_B_COST;
    public static final double PACK1B_SALES_INCOME = PACK1B_SALES_AMOUNT * PRODUCT_B_PRICE;

    public static final double PACK2A_SALES_AMOUNT = SALE_2A55_AMOUNT + SALE_2A60_AMOUNT;
    public static final double PACK2A_SALES_EXPENSES = PACK2A_SALES_AMOUNT * PRODUCT_A_COST;
    public static final double PACK2A_SALES_INCOME = PACK2A_SALES_AMOUNT * PRODUCT_A_PRICE;

    public static final double PACK2B_SALES_AMOUNT = SALE_2B65_AMOUNT + SALE_2B70_AMOUNT;
    public static final double PACK2B_SALES_EXPENSES = PACK2B_SALES_AMOUNT * PRODUCT_B_COST;
    public static final double PACK2B_SALES_INCOME = PACK2B_SALES_AMOUNT * PRODUCT_B_PRICE;

    public static final double PRODUCT_A_EXPENSES = PACK1A_SALES_EXPENSES + PACK2A_SALES_EXPENSES;
    public static final double PRODUCT_A_INCOME = PACK1A_SALES_INCOME + PACK2A_SALES_INCOME;
    public static final double PRODUCT_B_EXPENSES = PACK1B_SALES_EXPENSES + PACK2B_SALES_EXPENSES;
    public static final double PRODUCT_B_INCOME = PACK1B_SALES_INCOME + PACK2B_SALES_INCOME;
    public static final double SHOP1_EXPENSES = PACK1A_SALES_EXPENSES + PACK1B_SALES_EXPENSES;
    public static final double SHOP1_INCOME = PACK1A_SALES_INCOME + PACK1B_SALES_INCOME;
    public static final double SHOP2_EXPENSES = PACK2A_SALES_EXPENSES + PACK2B_SALES_EXPENSES;
    public static final double SHOP2_INCOME = PACK2A_SALES_INCOME + PACK2B_SALES_INCOME;

    public static final double NET_SALES_EXPENSES = PRODUCT_A_EXPENSES + PRODUCT_B_EXPENSES;
    public static final double NET_SALES_INCOME = PRODUCT_A_INCOME + PRODUCT_B_INCOME;
}
