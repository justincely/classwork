-- Block to drop and re-add each time to ensure prestine DB.
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT usage ON schema public TO public;
GRANT create ON schema public TO public;

CREATE TABLE AIRPORT (
  Airport_code CHAR(3) PRIMARY KEY,
  City VARCHAR(50),
  State VARCHAR(50),
  Name VARCHAR(50)
);

CREATE TABLE AIRPLANE_TYPE (
  Type_name VARCHAR(40) PRIMARY KEY,
  Company VARCHAR(80),
  Max_seats INT CHECK (Max_seats <= 600)
);

CREATE TABLE CAN_LAND (
  Airport CHAR(3) REFERENCES AIRPORT (Airport_code),
  Type VARCHAR(40) REFERENCES AIRPLANE_TYPE (Type_name)
);

CREATE TABLE AIRPLANE (
  Airplane_id SERIAL PRIMARY KEY,
  Total_no_of_seats INT,
  Type VARCHAR(40) REFERENCES AIRPLANE_TYPE (Type_name)
);

-- Rename Number -> Num to avoid reserved keyword
CREATE TABLE FLIGHT (
  Num SERIAL PRIMARY KEY UNIQUE,
  Airline VARCHAR(40),
  Weekdays VARCHAR(40)
);

CREATE TABLE FARE (
  Code CHAR(9) NOT NULL,
  Amount DOUBLE PRECISION CHECK (Amount >= 0 AND Amount <= 10000),
  Restrictions VARCHAR(80),
  Flight_No INT REFERENCES FLIGHT (Num),
  PRIMARY KEY (Code, FLight_No)
);

CREATE TABLE FLIGHT_LEG (
  Leg_no INT CHECK (leg_no >= 1 AND leg_no <= 4),
  DEPARTURE_AIRPORT CHAR(3) REFERENCES AIRPORT (Airport_code),
  ARRIVEL_AIRPORT CHAR(3) REFERENCES AIRPORT (Airport_code),
  Scheduled_dep_time TIME,
  Scheduled_arr_time TIME,
  Flight_num INT REFERENCES FLIGHT (Num),
  PRIMARY KEY (Leg_no, Flight_num)
);

-- Date -> FDate to avoid reserved
CREATE TABLE FLIGHT_INSTANCE (
  Fdate DATE NOT NULL CHECK (Fdate >= current_date),
  No_of_avail_seates INT,
  Leg_no INT NOT NULL,
  Flight_num INT NOT NULL UNIQUE,
  Plane INT REFERENCES AIRPLANE (Airplane_id),
  FOREIGN KEY (Leg_no, Flight_num) REFERENCES FLIGHT_LEG (Leg_no, Flight_num),
  PRIMARY KEY (Fdate, Leg_no)
);

CREATE TABLE SEAT_RESERVATION (
  Seat_no INT NOT NULL UNIQUE,
  Customer_name VARCHAR(50),
  Cphone INT,
  Leg_no INT NOT NULL,
  Fdate DATE NOT NULL,
  FOREIGN KEY (Leg_no, Fdate) REFERENCES FLIGHT_INSTANCE (Leg_no, Fdate),
  PRIMARY KEY (Seat_no, Leg_no, Fdate)
);


-- SQL inserts

-- Question A
INSERT INTO AIRPORT VALUES ('IAD', 'Dulles', 'Virginia', 'Dulles International Airport');


INSERT INTO AIRPLANE_TYPE VALUES ('747', 'Boeing', 400);
INSERT INTO AIRPLANE_TYPE VALUES ('767', 'Boeing', 400);
INSERT INTO AIRPLANE_TYPE VALUES ('777', 'Boeing', 400);
INSERT INTO AIRPLANE_TYPE VALUES ('A300', 'Airbus', 200);
INSERT INTO AIRPLANE_TYPE VALUES ('MD-11', 'McDonnell Douglas', 200);
INSERT INTO AIRPLANE_TYPE VALUES ('Space Shuttle', 'NASA', 6);
INSERT INTO AIRPLANE_TYPE VALUES ('Concord', 'Air France', 50);


INSERT INTO CAN_LAND VALUES ('IAD', '747');
INSERT INTO CAN_LAND VALUES ('IAD', '767');
INSERT INTO CAN_LAND VALUES ('IAD', '777');
INSERT INTO CAN_LAND VALUES ('IAD', 'A300');
INSERT INTO CAN_LAND VALUES ('IAD', 'MD-11');
INSERT INTO CAN_LAND VALUES ('IAD', 'Space Shuttle');
INSERT INTO CAN_LAND VALUES ('IAD', 'Concord');


-- Question B
INSERT INTO FLIGHT VALUES (198, 'United', 'mwf');

INSERT INTO FARE VALUES ('UN5N9F', 400, '', 198);
INSERT INTO FARE VALUES ('UN5N8F', 300, 'm', 198);

SELECT * FROM FARE JOIN FLIGHT ON FARE.flight_no=FLIGHT.num;

-- Question C
INSERT INTO AIRPORT VALUES ('BWI', 'Baltimore', 'Marylad', 'Baltimore Washington International');
INSERT INTO AIRPORT VALUES ('SFO', 'San Fransico', 'California', 'San Fransico International');

INSERT INTO FLIGHT VALUES (200, 'Delta', 'mwf');
INSERT INTO FLIGHT VALUES (201, 'Delta', 'mwf');
INSERT INTO FLIGHT VALUES (202, 'Delta', 'mwf');
INSERT INTO FLIGHT VALUES (400, 'Delta', 'mwf');
INSERT INTO FLIGHT VALUES (401, 'Delta', 'mwf');
INSERT INTO FLIGHT VALUES (402, 'Delta', 'mwf');

INSERT INTO FARE VALUES ('DL1421', 100, '', 200);
INSERT INTO FARE VALUES ('DL1421', 200, '', 201);
INSERT INTO FARE VALUES ('DL1421', 600, '', 202);
INSERT INTO FARE VALUES ('DL1421', 300, '', 400);
INSERT INTO FARE VALUES ('DL1421', 200, '', 401);
INSERT INTO FARE VALUES ('DL1421', 400, '', 402);

INSERT INTO FLIGHT_LEG VALUES (1, 'BWI', 'SFO', current_time + interval '1 hour', current_time + interval '4 hour', 200);
INSERT INTO FLIGHT_LEG VALUES (2, 'BWI', 'SFO', current_time + interval '2 hour', current_time + interval '5 hour', 201);
INSERT INTO FLIGHT_LEG VALUES (3, 'BWI', 'SFO', current_time + interval '3 hour', current_time + interval '6 hour', 202);
INSERT INTO FLIGHT_LEG VALUES (1, 'SFO', 'BWI', current_time + interval '1 hour', current_time + interval '4 hour', 400);
INSERT INTO FLIGHT_LEG VALUES (2, 'SFO', 'BWI', current_time + interval '2 hour', current_time + interval '5 hour', 401);
INSERT INTO FLIGHT_LEG VALUES (3, 'SFO', 'BWI', current_time + interval '3 hour', current_time + interval '6 hour', 402);

INSERT INTO AIRPLANE VALUES (DEFAULT, 200, '747');
INSERT INTO AIRPLANE VALUES (DEFAULT, 15, 'Space Shuttle');

-- Adjusted 2017-08-14 to 2017-08-15 to pass constraint on leg instance date
-- specified in requirements.
INSERT INTO FLIGHT_INSTANCE VALUES (date '2017-08-15', 2, 1, 200, 1);
INSERT INTO FLIGHT_INSTANCE VALUES (date '2017-08-15', 5, 2, 201, 1);
INSERT INTO FLIGHT_INSTANCE VALUES (date '2017-08-15', 30, 3, 202, 1);
INSERT INTO FLIGHT_INSTANCE VALUES (date '2017-08-20', 15, 1, 400, 2);
INSERT INTO FLIGHT_INSTANCE VALUES (date '2017-08-20', 15, 2, 401, 2);
INSERT INTO FLIGHT_INSTANCE VALUES (date '2017-08-20', 15, 3, 402, 2);
