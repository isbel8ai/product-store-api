ALTER TABLE lot DROP CONSTRAINT FKlot646825;
ALTER TABLE pack DROP CONSTRAINT FKpack415159;
ALTER TABLE sale DROP CONSTRAINT FKsale907373;
ALTER TABLE pack DROP CONSTRAINT FKpack49864;
DROP TABLE IF EXISTS lot CASCADE;
DROP TABLE IF EXISTS pack CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS sale CASCADE;
DROP TABLE IF EXISTS shop CASCADE;
