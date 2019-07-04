drop table tbl_user if exists;
drop table tbl_institute if exists;
drop table tbl_funds if exists;
drop table tbl_jwtauth if exists;


CREATE TABLE tbl_user(	
	usrid varchar(20) NOT NULL,
	passwd varchar(64) NOT NULL,
	SALT   varchar(16) NOT NULL,
	PRIMARY KEY (usrid)
);	

CREATE TABLE tbl_jwtauth(
	usrid varchar(20) NOT NULL,
	strtdt varchar(14) NOT NULL,
	enddt  varchar(14) NOT NULL,	
	jwtval varchar(400) NOT NULL,
	PRIMARY KEY (usrid, enddt)	
);
CREATE UNIQUE INDEX TBL_JWTAUTH_IDX1 
    ON tbl_jwtauth(jwtval, enddt);	


CREATE TABLE tbl_institute(
	institute_name varchar(20) NOT NULL,	
    institute_code char(3) NOT NULL,	
	ordseq varchar(2) NOT NULL,
	PRIMARY KEY (institute_code)	
);		


create table tbl_fund(
	hf_year char(4) NOT NULL,	
	hf_month varchar(2) NOT NULL,	
	institute_code char(3) NOT NULL,	
	hf_amount INTEGER,	
	PRIMARY KEY (hf_year, hf_month, institute_code  )	
);	
