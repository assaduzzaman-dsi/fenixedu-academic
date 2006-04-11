DROP TABLE RESEARCH_EVENT;
DROP TABLE EVENT_PARTICIPATION;

CREATE TABLE `EVENT_PARTICIPATION` (
  `ID_INTERNAL` int(11) NOT NULL auto_increment,
  `KEY_EVENT` int(11) NOT NULL default '0',
  `KEY_PARTY` int(11) NOT NULL default '0',
  `ROLE` varchar(100),
  `KEY_ROOT_DOMAIN_OBJECT` int(11) NOT NULL default '1',
  PRIMARY KEY  (`ID_INTERNAL`),
  KEY `KEY_EVENT` (`KEY_EVENT`),
  KEY `KEY_PARTY` (`KEY_PARTY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `RESEARCH_EVENT` (
  `ID_INTERNAL` int(11) unsigned NOT NULL auto_increment,
  `NAME` longtext,
  `START_DATE` varchar(10),
  `END_DATE` varchar(10),
  `LOCATION` varchar(50),
  `FEE` int(11),
  `TYPE` varchar(50),
  `KEY_ROOT_DOMAIN_OBJECT` int(11) NOT NULL default '1',
  PRIMARY KEY  (`ID_INTERNAL`)
) Type=InnoDB DEFAULT CHARSET=latin1;

