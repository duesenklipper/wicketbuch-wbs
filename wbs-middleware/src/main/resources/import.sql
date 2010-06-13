INSERT INTO User(id, name, vorname, username, passwort) VALUES (1,'Siefart','Olaf','osiefart','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (2,'Menzel','Carl-Eric','cmenzel','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (3,'Förther','Roland','rfoerther','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (4,'Jost','Otto','ojost','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (5,'Sommer','Britta','bsommer','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (6,'Kleinschmitt','Hans-Peter','hpkleinschmitt','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (7,'Lang','Monika','mlang','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (8,'Assner','Gregor','gassner','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (9,'Hammerbach','Daniela','dhammerbach','dummy');
INSERT INTO User(id, name, vorname, username, passwort) VALUES (10,'Schmidt-Gerstenstein','Alfons','aschmidt','dummy');

INSERT INTO hibernate_sequences(SEQUENCE_NAME, SEQUENCE_NEXT_HI_VALUE) VALUES ('User',11);

INSERT INTO ContactData(id, email) VALUES (1,'osiefart@wicketbuch.de');
INSERT INTO ContactData(id, email) VALUES (2,'cmenzel@wicketbuch.de');
INSERT INTO ContactData(id, email) VALUES (3,'rfoerther@wicketbuch.de');

INSERT INTO hibernate_sequences(SEQUENCE_NAME, SEQUENCE_NEXT_HI_VALUE) VALUES ('ContactData',4);


INSERT INTO Role(id, name) VALUES (1,'Administrator');
INSERT INTO Role(id, name) VALUES (2,'Manager');
INSERT INTO Role(id, name) VALUES (3,'User');

INSERT INTO hibernate_sequences(SEQUENCE_NAME, SEQUENCE_NEXT_HI_VALUE) VALUES ('Role',4);

INSERT INTO Principal(id, name) VALUES (1,'USER_ADMIN');
INSERT INTO Principal(id, name) VALUES (2,'PROJECT_ADMIN');
INSERT INTO Principal(id, name) VALUES (3,'TASK_ADMIN');
INSERT INTO Principal(id, name) VALUES (4,'TASK_WORKER');

INSERT INTO hibernate_sequences(SEQUENCE_NAME, SEQUENCE_NEXT_HI_VALUE) VALUES ('Principal',5);

-- Admin
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (1,1);
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (1,2);
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (1,3);
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (1,4);

-- Manager
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (2,2);
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (2,3);
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (2,4);

-- User
INSERT INTO Role_Principal(Role_id, principals_id) VALUES (3,4);

-- Olaf: Admin
INSERT INTO User_Role (User_id, roles_id) VALUES (1,1);
INSERT INTO User_ContactData (User_id, contactData_id) VALUES (1,1);

-- Carl-Eric: Manager
INSERT INTO User_Role (User_id, roles_id) VALUES (2,2);
INSERT INTO User_ContactData (User_id, contactData_id) VALUES (2,2);

-- Roland: User
INSERT INTO User_Role (User_id, roles_id) VALUES (3,3);
INSERT INTO User_ContactData (User_id, contactData_id) VALUES (3,3);

-- ojost: Admin
INSERT INTO User_Role (User_id, roles_id) VALUES (4,1);

-- bsommer: Admin
INSERT INTO User_Role (User_id, roles_id) VALUES (5,1);

-- hpkleinschmitt: Admin
INSERT INTO User_Role (User_id, roles_id) VALUES (6,1);

-- mlang: Manager
INSERT INTO User_Role (User_id, roles_id) VALUES (7,2);

-- gassner: Manager
INSERT INTO User_Role (User_id, roles_id) VALUES (8,2);

-- dhammerbach: User
INSERT INTO User_Role (User_id, roles_id) VALUES (9,3);

-- aSchmidt-Gerstenstein: User
INSERT INTO User_Role (User_id, roles_id) VALUES (10,3);

-- Projekte
INSERT INTO Project(id, kuerzel, name, budget, cost, start, ende, state, progress) VALUES (1,'ABGST','Abgeltungssteuer',480, '185.66','2008-06-01 00:00:00','2008-12-19 00:00:00','IN_ANALYSIS', 'RED');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, ende, state, progress) VALUES (2,'AFRST','Änderung Freistellungsauftrag',40, '177.31','2008-05-15 00:00:00','2008-07-15 00:00:00','FINAL', 'GREEN');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, state, progress) VALUES (3,'PBROK','Powerbroker',1450,'175','2008-07-01 00:00:00','IN_ANALYSIS', 'YELLOW');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, state, progress) VALUES (4,'BAUFI','Beratung Baufinanzierung',1850,'166.54','2007-02-15 00:00:00','IN_DEVELOPMENT', 'RED');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, ende, state, progress) VALUES (5,'SEPAU','Europäischer Zahlungsverkehr',271,'141.80','2007-08-01 00:00:00','2007-12-15 00:00:00','FINAL', 'YELLOW');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, ende, state, progress) VALUES (6,'OPTKD','Vertriebsoptimierung',670,'150.23','2005-03-01 00:00:00','2005-09-15 00:00:00','FINAL', 'GREEN');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, ende, state, progress) VALUES (7,'HSTBY','Optimierung Systemverfügbarkeit',55,'152.79','2008-03-01 00:00:00','2008-04-05 00:00:00','FINAL', 'RED');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, state, progress) VALUES (8,'ORMYS','DB Systemwechsel',160,'139.82','2009-04-01 00:00:00','IN_ANALYSIS', 'YELLOW');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, ende, state, progress) VALUES (9,'JBOSS','AS Systemwechsel',95,'140.96','2008-01-15 00:00:00','2008-03-15 00:00:00','FINAL', 'GREEN');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, state, progress) VALUES (10,'PKRPB','Privatkredit im Internet',680,'179.4','2008-10-01 00:00:00','IN_ANALYSIS', 'RED');
INSERT INTO Project(id, kuerzel, name, budget, cost, start, state, progress) VALUES (11,'EKAUS','Elektronischer Kontoauszug',240,'163.8','2008-07-01 00:00:00','IN_DEVELOPMENT', 'YELLOW');

INSERT INTO hibernate_sequences(SEQUENCE_NAME, SEQUENCE_NEXT_HI_VALUE) VALUES ('Project',12);

-- Tasks
INSERT INTO Task(id, name) VALUES (1,'ABGST');
INSERT INTO hibernate_sequences(SEQUENCE_NAME, SEQUENCE_NEXT_HI_VALUE) VALUES ('Task',12);

INSERT INTO Project_Task(Project_id, tasks_id) VALUES (1,1);

