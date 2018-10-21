### 建立数据表
```SQL
-- 用户表
CREATE TABLE sys_user(
	id CHAR(32) PRIMARY KEY,
	account VARCHAR(50) NOT NULL,
  NAME VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL
)

-- vip 详情
CREATE TABLE vip_detail(
	id CHAR(32) PRIMARY KEY,
	code VARCHAR(32),
  NAME VARCHAR(32),
  sex TINYINT(1),
  age TINYINT(1),
  qq VARCHAR(32),
  email VARCHAR(32),
  adress VARCHAR(128),
  zip VARCHAR(16),
  remark  VARCHAR(256),
  rank TINYINT(1),
  amount int COMMENT '消费金额，单位为分'
)

-- 消费记录表
CREATE TABLE consume(
id char(32) PRIMARY key,
vip_id char(32),
whens datetime,
orderno VARCHAR(32),
amount int COMMENT '总额，单位为分',
operator_id char(32),
remark VARCHAR(256),
flag TINYINT(1)
)

-- vip 等级
CREATE TABLE vip_rank(
no INT,
name VARCHAR(32),
needAmount int COMMENT '总额，单位为分',
discount TINYINT(3),
remark VARCHAR(256)
)

```
