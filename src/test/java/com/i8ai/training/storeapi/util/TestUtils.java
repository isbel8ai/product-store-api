package com.i8ai.training.storeapi.util;

import com.i8ai.training.storeapi.model.*;

import java.util.Date;

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

    public static final Product PRODUCT_A = new Product(PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    public static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);

    public static final Shop SHOP1 = new Shop(SHOP1_ID, SHOP1_NAME, SHOP1_ADDRESS, null);
    public static final Shop SHOP2 = new Shop(SHOP2_ID, SHOP2_NAME, SHOP2_ADDRESS, null);

    public static final Lot LOT_A = new Lot(LOT_A_ID, new Date(5), LOT_A_AMOUNT, PRODUCT_A_COST, PRODUCT_A);
    public static final Lot LOT_B = new Lot(LOT_B_ID, new Date(10), LOT_B_AMOUNT, PRODUCT_B_COST, PRODUCT_B);

    public static final Pack PACK1A = new Pack(PACK1A_ID, new Date(15), PACK1A_AMOUNT, LOT_A, SHOP1);
    public static final Pack PACK1B = new Pack(PACK1B_ID, new Date(20), PACK1B_AMOUNT, LOT_B, SHOP1);
    public static final Pack PACK2A = new Pack(PACK2A_ID, new Date(25), PACK2A_AMOUNT, LOT_A, SHOP2);
    public static final Pack PACK2B = new Pack(PACK2B_ID, new Date(30), PACK2B_AMOUNT, LOT_B, SHOP2);

    public static final Sale SALE_1A35 = new Sale(null, new Date(35), SALE_1A35_AMOUNT, PRODUCT_A_PRICE, PACK1A);
    public static final Sale SALE_1A40 = new Sale(null, new Date(40), SALE_1A40_AMOUNT, PRODUCT_A_PRICE, PACK1A);
    public static final Sale SALE_1B45 = new Sale(null, new Date(45), SALE_1B45_AMOUNT, PRODUCT_B_PRICE, PACK1B);
    public static final Sale SALE_1B50 = new Sale(null, new Date(50), SALE_1B50_AMOUNT, PRODUCT_B_PRICE, PACK1B);
    public static final Sale SALE_2A55 = new Sale(null, new Date(55), SALE_2A55_AMOUNT, PRODUCT_A_PRICE, PACK2A);
    public static final Sale SALE_2A60 = new Sale(null, new Date(60), SALE_2A60_AMOUNT, PRODUCT_A_PRICE, PACK2A);
    public static final Sale SALE_2B65 = new Sale(null, new Date(65), SALE_2B65_AMOUNT, PRODUCT_B_PRICE, PACK2B);
    public static final Sale SALE_2B70 = new Sale(null, new Date(70), SALE_2B70_AMOUNT, PRODUCT_B_PRICE, PACK2B);
}
