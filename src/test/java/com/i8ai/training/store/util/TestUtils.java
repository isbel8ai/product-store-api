package com.i8ai.training.store.util;

import com.i8ai.training.store.model.*;

import java.util.Date;

public class TestUtils {
    public static final Long PRODUCT_A_ID = 0x0AL;
    public static final String PRODUCT_A_CODE = "a_product_code";
    public static final String PRODUCT_A_NAME = "a_product_name";
    public static final String PRODUCT_A_MEASURE = "a_product_measure";
    public static final Double PRODUCT_A_COST = 2.0;
    public static final Double PRODUCT_A_PRICE = 3.0;

    public static final Long PRODUCT_B_ID = 0x0BL;
    public static final String PRODUCT_B_CODE = "b_product_code";
    public static final String PRODUCT_B_NAME = "b_product_name";
    public static final String PRODUCT_B_MEASURE = "b_product_measure";
    public static final Double PRODUCT_B_COST = 4.0;
    public static final Double PRODUCT_B_PRICE = 6.0;

    public static final Long LOT_A_ID = 0xA0L;
    public static final Long LOT_B_ID = 0xB0L;

    public static final Double LOT_A_AMOUNT = 2000.0;
    public static final Double LOT_B_AMOUNT = 1000.0;

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

    public static final Double PACK1A_AMOUNT = 300.0;
    public static final Double PACK1B_AMOUNT = 200.0;
    public static final Double PACK2A_AMOUNT = 350.0;
    public static final Double PACK2B_AMOUNT = 250.0;

    public static final Double SALE_1A35_AMOUNT = 150.0;
    public static final Double SALE_1A40_AMOUNT = 100.0;
    public static final Double SALE_1B45_AMOUNT = 50.0;
    public static final Double SALE_1B50_AMOUNT = 75.0;
    public static final Double SALE_2A55_AMOUNT = 180.0;
    public static final Double SALE_2A60_AMOUNT = 130.0;
    public static final Double SALE_2B65_AMOUNT = 120.0;
    public static final Double SALE_2B70_AMOUNT = 90.0;

    public static final Double PACK1A_SALES_AMOUNT = SALE_1A35_AMOUNT + SALE_1A40_AMOUNT;
    public static final Double PACK1A_SALES_EXPENSES = PACK1A_SALES_AMOUNT * PRODUCT_A_COST;
    public static final Double PACK1A_SALES_INCOME = PACK1A_SALES_AMOUNT * PRODUCT_A_PRICE;

    public static final Double PACK1B_SALES_AMOUNT = SALE_1B45_AMOUNT + SALE_1B50_AMOUNT;
    public static final Double PACK1B_SALES_EXPENSES = PACK1B_SALES_AMOUNT * PRODUCT_B_COST;
    public static final Double PACK1B_SALES_INCOME = PACK1B_SALES_AMOUNT * PRODUCT_B_PRICE;

    public static final Double PACK2A_SALES_AMOUNT = SALE_2A55_AMOUNT + SALE_2A60_AMOUNT;
    public static final Double PACK2A_SALES_EXPENSES = PACK2A_SALES_AMOUNT * PRODUCT_A_COST;
    public static final Double PACK2A_SALES_INCOME = PACK2A_SALES_AMOUNT * PRODUCT_A_PRICE;

    public static final Double PACK2B_SALES_AMOUNT = SALE_2B65_AMOUNT + SALE_2B70_AMOUNT;
    public static final Double PACK2B_SALES_EXPENSES = PACK2B_SALES_AMOUNT * PRODUCT_B_COST;
    public static final Double PACK2B_SALES_INCOME = PACK2B_SALES_AMOUNT * PRODUCT_B_PRICE;

    public static final Double PRODUCT_A_EXPENSES = PACK1A_SALES_EXPENSES + PACK2A_SALES_EXPENSES;
    public static final Double PRODUCT_A_INCOME = PACK1A_SALES_INCOME + PACK2A_SALES_INCOME;
    public static final Double PRODUCT_B_EXPENSES = PACK1B_SALES_EXPENSES + PACK2B_SALES_EXPENSES;
    public static final Double PRODUCT_B_INCOME = PACK1B_SALES_INCOME + PACK2B_SALES_INCOME;
    public static final Double SHOP1_EXPENSES = PACK1A_SALES_EXPENSES + PACK1B_SALES_EXPENSES;
    public static final Double SHOP1_INCOME = PACK1A_SALES_INCOME + PACK1B_SALES_INCOME;
    public static final Double SHOP2_EXPENSES = PACK2A_SALES_EXPENSES + PACK2B_SALES_EXPENSES;
    public static final Double SHOP2_INCOME = PACK2A_SALES_INCOME + PACK2B_SALES_INCOME;

    public static final Double NET_SALES_EXPENSES = PRODUCT_A_EXPENSES + PRODUCT_B_EXPENSES;
    public static final Double NET_SALES_INCOME = PRODUCT_A_INCOME + PRODUCT_B_INCOME;

    public static final Product PRODUCT_A = Product.builder()
            .id(PRODUCT_A_ID).code(PRODUCT_A_CODE).name(PRODUCT_A_NAME).measure(PRODUCT_A_MEASURE).build();
    public static final Product PRODUCT_B = Product.builder()
            .id(PRODUCT_B_ID).code(PRODUCT_B_CODE).name(PRODUCT_B_NAME).measure(PRODUCT_B_MEASURE).build();

    public static final Shop SHOP1 = Shop.builder().id(SHOP1_ID).name(SHOP1_NAME).address(SHOP1_ADDRESS).build();
    public static final Shop SHOP2 = Shop.builder().id(SHOP2_ID).name(SHOP2_NAME).address(SHOP2_ADDRESS).build();

    public static final Lot LOT_A = Lot.builder()
            .id(LOT_A_ID).received(new Date(5)).amount(LOT_A_AMOUNT).cost(PRODUCT_A_COST).product(PRODUCT_A).build();
    public static final Lot LOT_B = Lot.builder()
            .id(LOT_B_ID).received(new Date(10)).amount(LOT_B_AMOUNT).cost(PRODUCT_B_COST).product(PRODUCT_B).build();

    public static final Pack PACK1A = Pack.builder()
            .id(PACK1A_ID).delivered(new Date(15)).amount(PACK1A_AMOUNT).lot(LOT_A).shop(SHOP1).build();
    public static final Pack PACK1B = Pack.builder()
            .id(PACK1B_ID).delivered(new Date(20)).amount(PACK1B_AMOUNT).lot(LOT_B).shop(SHOP1).build();
    public static final Pack PACK2A = Pack.builder()
            .id(PACK2A_ID).delivered(new Date(25)).amount(PACK2A_AMOUNT).lot(LOT_A).shop(SHOP2).build();
    public static final Pack PACK2B = Pack.builder()
            .id(PACK2B_ID).delivered(new Date(30)).amount(PACK2B_AMOUNT).lot(LOT_B).shop(SHOP2).build();

    public static final Sale SALE_1A35 = Sale.builder()
            .id(0x1A35L).registered(new Date(35)).amount(SALE_1A35_AMOUNT).price(PRODUCT_A_PRICE).pack(PACK1A).build();
    public static final Sale SALE_1A40 = Sale.builder()
            .id(0x1A35L).registered(new Date(40)).amount(SALE_1A40_AMOUNT).price(PRODUCT_A_PRICE).pack(PACK1A).build();
    public static final Sale SALE_1B45 = Sale.builder()
            .id(0x1A35L).registered(new Date(45)).amount(SALE_1B45_AMOUNT).price(PRODUCT_B_PRICE).pack(PACK1B).build();
    public static final Sale SALE_1B50 = Sale.builder()
            .id(0x1A35L).registered(new Date(50)).amount(SALE_1B50_AMOUNT).price(PRODUCT_B_PRICE).pack(PACK1B).build();
    public static final Sale SALE_2A55 = Sale.builder()
            .id(0x1A35L).registered(new Date(55)).amount(SALE_2A55_AMOUNT).price(PRODUCT_A_PRICE).pack(PACK2A).build();
    public static final Sale SALE_2A60 = Sale.builder()
            .id(0x1A35L).registered(new Date(60)).amount(SALE_2A60_AMOUNT).price(PRODUCT_A_PRICE).pack(PACK2A).build();
    public static final Sale SALE_2B65 = Sale.builder()
            .id(0x1A35L).registered(new Date(65)).amount(SALE_2B65_AMOUNT).price(PRODUCT_B_PRICE).pack(PACK2B).build();
    public static final Sale SALE_2B70 = Sale.builder()
            .id(0x1A35L).registered(new Date(70)).amount(SALE_2B70_AMOUNT).price(PRODUCT_B_PRICE).pack(PACK2B).build();
}
