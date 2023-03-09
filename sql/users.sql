--------------------------------------------------------
--  File created - Th? N?m-th�ng 3-09-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "EWALLET_SI"."USERS" 
   (	"ID" NUMBER(19,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"CREATED_BY" VARCHAR2(50 CHAR), 
	"CREATED_DATE" TIMESTAMP (6), 
	"LAST_MODIFIED_BY" VARCHAR2(50 CHAR), 
	"LAST_MODIFIED_DATE" TIMESTAMP (6), 
	"ACTIVATED" NUMBER(1,0), 
	"ACTIVATION_KEY" VARCHAR2(20 CHAR), 
	"EMAIL" VARCHAR2(254 CHAR), 
	"FIRST_NAME" VARCHAR2(50 CHAR), 
	"IMAGE_URL" VARCHAR2(256 CHAR), 
	"LANG_KEY" VARCHAR2(6 CHAR), 
	"LAST_NAME" VARCHAR2(50 CHAR), 
	"LOGIN" VARCHAR2(50 CHAR), 
	"PASSWORD_HASH" VARCHAR2(60 CHAR), 
	"PHONE" VARCHAR2(255 CHAR), 
	"RESET_DATE" TIMESTAMP (6), 
	"RESET_KEY" VARCHAR2(20 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA" ;
REM INSERTING into EWALLET_SI.USERS
SET DEFINE OFF;
Insert into EWALLET_SI.USERS (ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE,ACTIVATED,ACTIVATION_KEY,EMAIL,FIRST_NAME,IMAGE_URL,LANG_KEY,LAST_NAME,LOGIN,PASSWORD_HASH,PHONE,RESET_DATE,RESET_KEY) values (3,'admin',to_timestamp('08-MAR-23 05.26.42.064792000 PM','DD-MON-RR HH.MI.SSXFF AM'),null,null,1,null,null,'admin',null,'en',null,'admin','$2a$10$gla9yr7N1Qnn4EMI7hNFIeY1bv6ADo1dU8BiQqhTMQBw7NW1GuSaK',null,null,null);
--------------------------------------------------------
--  DDL for Index SYS_C0017195
--------------------------------------------------------

  CREATE UNIQUE INDEX "EWALLET_SI"."SYS_C0017195" ON "EWALLET_SI"."USERS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA" ;
--------------------------------------------------------
--  DDL for Index UK_6DOTKOTT2KJSP8VW4D0M25FB7
--------------------------------------------------------

  CREATE UNIQUE INDEX "EWALLET_SI"."UK_6DOTKOTT2KJSP8VW4D0M25FB7" ON "EWALLET_SI"."USERS" ("EMAIL") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA" ;
--------------------------------------------------------
--  DDL for Index UK_OW0GAN20590JRB00UPG3VA2FN
--------------------------------------------------------

  CREATE UNIQUE INDEX "EWALLET_SI"."UK_OW0GAN20590JRB00UPG3VA2FN" ON "EWALLET_SI"."USERS" ("LOGIN") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA" ;
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "EWALLET_SI"."USERS" ADD CONSTRAINT "UK_OW0GAN20590JRB00UPG3VA2FN" UNIQUE ("LOGIN")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA"  ENABLE;
  ALTER TABLE "EWALLET_SI"."USERS" ADD CONSTRAINT "UK_6DOTKOTT2KJSP8VW4D0M25FB7" UNIQUE ("EMAIL")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA"  ENABLE;
  ALTER TABLE "EWALLET_SI"."USERS" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA"  ENABLE;
  ALTER TABLE "EWALLET_SI"."USERS" MODIFY ("PASSWORD_HASH" NOT NULL ENABLE);
  ALTER TABLE "EWALLET_SI"."USERS" MODIFY ("LOGIN" NOT NULL ENABLE);
  ALTER TABLE "EWALLET_SI"."USERS" MODIFY ("ACTIVATED" NOT NULL ENABLE);
  ALTER TABLE "EWALLET_SI"."USERS" MODIFY ("CREATED_BY" NOT NULL ENABLE);
  ALTER TABLE "EWALLET_SI"."USERS" MODIFY ("ID" NOT NULL ENABLE);
