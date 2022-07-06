
CREATE TABLE USER (
	userID	VARCHAR(100)	NOT NULL,
	totalcount int not null default 0
);

CREATE TABLE REVIEW_HISTORY (
	reviewID	varchar(100)	NOT NULL,
	userID	varchar(100)	NOT NULL,
	placeID	varchar(100)	NOT NULL,
	content   varchar(1000)        NULL,
	action varchar(10) not null,
	date datetime NOT NULL,
	point int not null default 0
);

CREATE TABLE PLACE (
	placeID	varchar(100)	NOT NULL
);

CREATE TABLE PHOTO (
	reviewID	varchar(100)	NOT NULL,
	photoID	varchar(100)	NULL
);
CREATE TABLE REVIEW (
	reviewID	varchar(100)	NOT NULL,
	userID	varchar(100)	NOT NULL,
	placeID	varchar(100)	NOT NULL
);
ALTER TABLE REVIEW ADD CONSTRAINT PK_REVIEW PRIMARY KEY (
	reviewID
);

ALTER TABLE REVIEW_HISTORY ADD CONSTRAINT PK_REVIEW_HISTORY PRIMARY KEY (
	reviewID
);

ALTER TABLE USER ADD CONSTRAINT PK_USER PRIMARY KEY (
	userID
);

ALTER TABLE PLACE ADD CONSTRAINT PK_PLACE PRIMARY KEY (
	placeID
);

ALTER TABLE REVIEW ADD CONSTRAINT FK_USER_TO_REVIEW_1 FOREIGN KEY (
	userID
)
REFERENCES USER (
	userID
);

ALTER TABLE REVIEW ADD CONSTRAINT FK_PLACE_TO_REVIEW_1 FOREIGN KEY (
	placeID
)
REFERENCES PLACE (
	placeID
);

create index idx_review_place_user on review(placeID,userID);
create index idx_review_place on review(placeID);
create index idx_review_history_place_user on review_history(placeID,userID);
create index idx_review_history_user on review_history(userID);

SET SQL_SAFE_UPDATES = 0;