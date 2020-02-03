CREATE TABLE lot (
  id         SERIAL NOT NULL, 
  "date"     timestamp NOT NULL, 
  cost       float8 NOT NULL, 
  amount     float8 NOT NULL, 
  product_id int4 NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE pack (
  id      SERIAL NOT NULL, 
  "date"  timestamp NOT NULL, 
  amount  float8 NOT NULL, 
  lot_id  int4 NOT NULL, 
  shop_id int4 NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE product (
  id          SERIAL NOT NULL, 
  code        varchar(255) NOT NULL UNIQUE, 
  name        varchar(255) NOT NULL, 
  measure     varchar(10) NOT NULL, 
  description varchar(255), 
  PRIMARY KEY (id));
CREATE TABLE sale (
  id     SERIAL NOT NULL, 
  amount float8 NOT NULL, 
  price  float8 NOT NULL, 
  packid int4 NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE shop (
  id          SERIAL NOT NULL, 
  name        varchar(255) NOT NULL UNIQUE, 
  address     varchar(255) NOT NULL, 
  descripcion varchar(255), 
  PRIMARY KEY (id));
ALTER TABLE lot ADD CONSTRAINT FKlot646825 FOREIGN KEY (product_id) REFERENCES product (id);
ALTER TABLE pack ADD CONSTRAINT FKpack415159 FOREIGN KEY (lot_id) REFERENCES lot (id);
ALTER TABLE sale ADD CONSTRAINT FKsale907373 FOREIGN KEY (packid) REFERENCES pack (id);
ALTER TABLE pack ADD CONSTRAINT FKpack49864 FOREIGN KEY (shop_id) REFERENCES shop (id);

