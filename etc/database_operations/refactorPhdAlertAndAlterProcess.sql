alter table PHD_ALERT RENAME ALERT,
	change `OID_ROOT_DOMAIN_OBJECT_FOR_ACTIVE_PHD_ALERTS` `OID_ROOT_DOMAIN_OBJECT_FOR_ACTIVE_ALERTS` bigint(20),
	change `KEY_ROOT_DOMAIN_OBJECT_FOR_ACTIVE_PHD_ALERTS` `KEY_ROOT_DOMAIN_OBJECT_FOR_ACTIVE_ALERTS` int(11),
	add column `ENTITY` text,
	add column `OID_WHO_CREATED` bigint(20);

alter table `PROCESS` add column `VALIDATED_BY_ERASMUS_COORDINATOR` tinyint(1),
	add column `VALIDATED_BY_GRI` tinyint(1);


