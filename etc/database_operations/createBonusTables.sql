DROP TABLE ANUAL_BONUS_INSTALLMENT;
CREATE TABLE ANUAL_BONUS_INSTALLMENT (
  ID_INTERNAL int(11) unsigned NOT NULL auto_increment,
  KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
  YEAR int(4) NOT NULL,
  PAYMENT_PARTIAL_DATE varchar(24) NOT NULL default '',
  ASSIDUOUSNESS_YEAR_MONTHS text NOT NULL,
  PRIMARY KEY  (ID_INTERNAL),
  UNIQUE KEY U1 (PAYMENT_PARTIAL_DATE)
) ;

DROP TABLE EMPLOYEE_BONUS_INSTALLMENT;
CREATE TABLE EMPLOYEE_BONUS_INSTALLMENT (
  ID_INTERNAL int(11) unsigned NOT NULL auto_increment,
  KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
  KEY_EMPLOYEE int(11) unsigned NOT NULL,
  KEY_ANUAL_BONUS_INSTALLMENT int(11) unsigned NOT NULL,
  INSTALLMENT_P1_VALUE double not null,
  INSTALLMENT_P2_VALUE double not null,
  COST_CENTER_CODE int(2) not null,
  SUB_COST_CENTER_CODE int(2) not null,
  EXPLORATION_UNIT int(2) not null,
  PRIMARY KEY  (ID_INTERNAL),
  KEY KEY_EMPLOYEE (KEY_EMPLOYEE),
  KEY KEY_ANUAL_BONUS_INSTALLMENT (KEY_ANUAL_BONUS_INSTALLMENT),
  UNIQUE KEY U1 (KEY_EMPLOYEE,KEY_ANUAL_BONUS_INSTALLMENT)
) ;

DROP TABLE EMPLOYEE_MONTHLY_BONUS_INSTALLMENT;
CREATE TABLE EMPLOYEE_MONTHLY_BONUS_INSTALLMENT (
  ID_INTERNAL int(11) unsigned NOT NULL auto_increment,
  KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
  KEY_EMPLOYEE_BONUS_INSTALLMENT int(11) unsigned NOT NULL,
  MONTH int(2) NOT NULL,
  P1_VALUE double not null,
  P2_VALUE double not null,
  PRIMARY KEY  (ID_INTERNAL),
  KEY `KEY_EMPLOYEE_BONUS_INSTALLMENT` (`KEY_EMPLOYEE_BONUS_INSTALLMENT`)
) ;