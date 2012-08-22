CREATE TABLE ERASMUS_STUDENT_DATA (
  `ID_INTERNAL` int(11) NOT NULL auto_increment,
  `OID` bigint(20) not null,
  `HOME_INSTITUTION_NAME` varchar(255),
  `HOME_INSTITUTION_ADDRESS` varchar(255),
  `HOME_INSTITUTION_COORDINATOR_NAME` varchar(255),
  `HOME_INSTITUTION_PHONE` varchar(255),
  `HOME_INSTITUTION_FAX` varchar(255),
  `HOME_INSTITUTION_EMAIL` varchar(255),
  `HAS_DIPLOMA_OR_DEGREE` tinyint(1),
  `DIPLOMA_CONCLUSION_YEAR` int(11),
  `DIPLOMA_NAME` varchar(255),
  `EXPERIENCE_CARRYING_OUT_PROJECT` tinyint(1),
  `DATE_OF_ARRIVAL` varchar(100),
  `DATE_OF_DEPARTURE` varchar(100),
  `TYPES_OF_PROGRAMME` varchar(255),
  `MAIN_SUBJECT_THESIS` varchar(255),
  `HAS_CONTACTED_OTHER_STAFF` tinyint(1),
  `NAME_OF_CONTACT` varchar(255),
  `OID_ERASMUS_INDIVIDUAL_CANDIDACY` bigint(20),
  `OID_ROOT_DOMAIN_OBJECT` bigint(20),
  primary key (ID_INTERNAL),
  index (OID)
) type=InnoDB, character set latin1 ;

ALTER TABLE INDIVIDUAL_CANDIDACY ADD COLUMN `OID_ERASMUS_STUDENT_DATA` bigint(20);

CREATE TABLE ERASMUS_INDIVIDUAL_CANDIDACY_CURRICULAR_COURSES (
	`OID_CURRICULAR_COURSE` bigint(20),
	`OID_ERASMUS_INDIVIDUAL_CANDIDACY` bigint(20),
	PRIMARY KEY (OID_CURRICULAR_COURSE, OID_ERASMUS_INDIVIDUAL_CANDIDACY)
) type=InnoDB, character set latin1 ;