create table if not exists POP_APPLY(
 ID int IDENTITY(1,1) NOT NULL,
 NAME varchar(25) not null,
 COMPANY varchar(25) NULL,
 APPLY_DATE datetime  NULL
);
create table if not exists POP_APPLY_SON(
 ID int IDENTITY(1,1) not null,
 POP_APPLY_ID int not null,
 GOODS_CODE varchar(20) null,
 AMOUNT int null
);
create table if not exists TEST_USERS(
  ID int  IDENTITY(1,1) NOT NULL,
  USERNAME varchar(64)  NOT NULL,
  PASSWORD varchar(300)  NULL,
  ENABLED bit  NULL
);
create table if not exists POP_IN_STOCK (
  ID int  IDENTITY(1,1) NOT NULL,
  TASKID int  NULL,
  POP_CODE varchar(100)  NULL,
  POP_NAME varchar(100)  NULL,
  VERSION varchar(50)  NULL,
  BRAND varchar(50)  NULL,
  IN_STOCK_COUNT decimal(18)  NULL,
  COMMENT varchar(1024)  NULL
);