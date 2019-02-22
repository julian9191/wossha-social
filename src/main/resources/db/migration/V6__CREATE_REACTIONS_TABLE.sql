CREATE TABLE TWSS_REACTIONS(
	ID NUMBER NOT NULL,
	UUID VARCHAR2(250 BYTE) NOT NULL,
	TYPE VARCHAR2(250 BYTE) NOT NULL,
	UUID_POST VARCHAR2(250 BYTE) NOT NULL,
	USERNAME VARCHAR2(250 BYTE) NOT NULL,
    CREATED TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT TWSS_REACTIONS_PK PRIMARY KEY (ID) ENABLE,
    CONSTRAINT TWSS_UUID_REACTIONS_UQ UNIQUE (UUID),
    CONSTRAINT FK_REACTIONS_POSTS FOREIGN KEY (UUID_POST) REFERENCES TWSS_POSTS(UUID)
);

CREATE SEQUENCE TWSS_REACTIONS_SEQ;
 
CREATE TRIGGER TWSS_REACTIONS_TRG
BEFORE INSERT ON TWSS_REACTIONS
FOR EACH ROW
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID IS NULL THEN
      SELECT TWSS_REACTIONS_SEQ.NEXTVAL INTO :NEW.ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/