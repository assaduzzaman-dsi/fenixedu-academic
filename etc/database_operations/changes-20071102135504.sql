--  SQL file representing changes to the functionalities model
--  Generated at Fri Nov 2 13:57:10 WET 2007
--  DO NOT EDIT THIS FILE, run the generating script instead

--  Preamble
SET AUTOCOMMIT = 0;

START TRANSACTION;

-- 
--  Inserting new functionalities
-- 

--  ID: 343319 UUID: '73bd2f92-0d09-48d5-ae78-ea1e15653077'
INSERT INTO `ACCESSIBLE_ITEM` (`UUID`, `OJB_CONCRETE_CLASS`, `KEY_ROOT_DOMAIN_OBJECT`, `KEY_PARENT`, `KEY_MODULE`, `KEY_AVAILABILITY_POLICY`, `NAME`, `TITLE`, `DESCRIPTION`, `PATH`, `PREFIX`, `RELATIVE`, `ENABLED`, `PARAMETERS`, `ORDER_IN_MODULE`, `VISIBLE`, `MAXIMIZED`, `PRINCIPAL`, `KEY_SUPERIOR_SECTION`, `SECTION_ORDER`, `KEY_SITE`, `LAST_MODIFIED_DATE_YEAR_MONTH_DAY`, `KEY_SECTION`, `ITEM_ORDER`, `INFORMATION`, `KEY_FUNCTIONALITY`, `SHOW_NAME`, `KEY_INTRODUCTION_UNIT_SITE`) SELECT '73bd2f92-0d09-48d5-ae78-ea1e15653077', 'net.sourceforge.fenixedu.domain.functionalities.ConcreteFunctionality', 1, NULL, `ID_INTERNAL`, NULL, 'pt11:Publicaçõesen12:Publications', 'pt11:Publicaçõesen12:Publications', NULL, '/departmentSite.do?method=showPublications', NULL, 1, 1, 'selectedDepartmentUnitID', 9, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL FROM `ACCESSIBLE_ITEM` WHERE `UUID` = '3adea1cf-e612-456f-b8be-c24882b77c3e';

COMMIT;
