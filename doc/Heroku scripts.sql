
Drop table Bed;

CREATE TABLE Bed (
  id serial primary key,
  patientId int DEFAULT NULL,
  floorId int DEFAULT NULL,
  x int DEFAULT NULL,
  y int DEFAULT NULL
);

Drop table DoctorPatient;

CREATE TABLE DoctorPatient (
  id serial primary key,
  patientId int DEFAULT NULL,
  doctorId int DEFAULT NULL
);

Drop table Floor;

CREATE TABLE Floor (
  id serial primary key,
  image bytea
);

Drop TABLE MedicalShift;

CREATE TABLE MedicalShift (
  id serial primary key,
  fecha varchar(255) NULL,
  doctorId int DEFAULT NULL,
  patientName varchar(255) DEFAULT NULL,
  time varchar(255) DEFAULT NULL
);

Drop TABLE Medicine;

CREATE TABLE Medicine (
  id serial primary key,
  name varchar(255) DEFAULT NULL,
  observations varchar(255) DEFAULT NULL
);


Drop TABLE Study;

CREATE TABLE Study (
  id serial primary key,
  type varchar(255) DEFAULT NULL,
  observations varchar(255) DEFAULT NULL,
  patientId int DEFAULT NULL,
  doctorId int DEFAULT NULL,
  priority int DEFAULT NULL
);

Drop TABLE StudyType;

CREATE TABLE StudyType (
  id serial primary key,
  name varchar(255) DEFAULT NULL
);

Drop TABLE Users;

CREATE TABLE Users (
  id serial primary key,
  firstName varchar(255) DEFAULT NULL,
  lastName varchar(255) DEFAULT NULL,
  roleId int DEFAULT NULL,
  userName varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  dni varchar(255) DEFAULT NULL,
 profilePicture BYTEA DEFAULT NULL
);

Drop TABLE UserMedicine;

CREATE TABLE UserMedicine (
  id serial primary key,
  observations varchar(255) DEFAULT NULL,
  patientId int DEFAULT NULL,
  doctorId int DEFAULT NULL,
  date int DEFAULT NULL,
  medicineId int DEFAULT NULL
);

Drop TABLE UserTreatment;

CREATE TABLE UserTreatment (
  id serial primary key,
  observations varchar(255) DEFAULT NULL,
  patientId int DEFAULT NULL,
  doctorId int DEFAULT NULL,
  fecha int DEFAULT NULL
);


INSERT INTO Medicine (name, observations) VALUES
('Ibuprofeno', NULL),
('Morfina', NULL),
('Penicilina', NULL),
('Paracetamol', NULL),
('Atenolol', NULL),
('Diclofenac', NULL);

INSERT INTO StudyType (name) VALUES
('Analisis de Sangre'),
('Analisis de Orina');


INSERT INTO Users (firstName, lastName, roleId, userName, password) VALUES
('quimey', 'funes', 0, 'quimey', '12345'),
('Juan Roman', 'Riquelme', 1, 'riquelme', '12345'),
('Cristiano', 'Ronaldo', 1, 'ronaldo', '12345'),
('admin', 'admin', 4, 'admin', '123456');


INSERT INTO DoctorPatient (patientId, doctorId) VALUES
(3, 1),
(2, 1);
