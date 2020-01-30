# Product Store Api
Management system for a local shop business. It help owners to supervise and control the live cycle of the products they sell, from the moment are received to they are sold.

## Requirements 

- Management of products
- Management of shops
- Product existence on main warehouse, for all and for specific product
- Product existence on a shop, for all and for specific product
- History of products receipt on the main warehouse in a lapse of time, filtering by product
- History of products delivered to shops in a lapse of time, filtering by product and shop
- History of sales on shops in a lapse of time, filtering by product and shop
- Get balance in a lapse of time, filtering by product and shop

## API Definition
| Action                                                  | URL                                    | Method | Parameters                       |
|---------------------------------------------------------|----------------------------------------|--------|----------------------------------|
| List all products                                       | /product                               | GET    |                                  |
| Add a product                                           | /product                               | POST   | code, name, measure, description |
| Get a product information                               | /product/{product_id}                  | GET    |                                  |
| Update a product information                            | /product/{product_id}                  | PUT    | code, name, measure, description |
| Delete a product                                        | /product/{product_id}                  | DELETE |                                  |
| List all shops                                          | /shop                                  | GET    |                                  |
| Add a shop                                              | /shop                                  | POST   | name, address, description       |
| Get a shop information                                  | /shop/{shop_id}                        | GET    |                                  |
| Update a product information                            | /shop/{shop_id}                        | PUT    | name, address, description       |
| Delete a product                                        | /shop/{shop_id}                        | DELETE |                                  |
| History of receipts with filters                        | /lot                                   | GET    | start_date, end_date, product_id |
| Register a product receipt                              | /lot                                   | POST   | date, cost, amount, product_id   |
| Delete a product receipt record                         | /lot/{lot_id}                          | DELETE |                                  |
| History of deliveries with filters                      | /pack                                  | GET    | product_id, shop_id              |
| Register a product pack                                 | /pack                                  | POST   | date, amount, lot_id, shop_id    |
| Delete a product pack record                            | /pack/{pack_id}                        | DELETE |                                  |
| Get existence of all products on the main warehouse     | /existence                             | GET    |                                  |
| Get existence of specific product on the main warehouse | /existence/{product_id}                | GET    |                                  |
| Get existence of all products on a shop                 | /shop/{shop_id}/existence              | GET    |                                  |
| Get existence of specific product on a shop             | /shop/{shop_id}/existence/{product_id} | GET    |                                  |
| Register a product sale                                 | /shop/{shop_id}/sale                   | POST   |                                  |
| History of sales with filters                           | /sale                                  | GET    | product_id, shop_id              |
| Get balance with filters                                | /balance                               | GET    | product_id, shop_id              |
