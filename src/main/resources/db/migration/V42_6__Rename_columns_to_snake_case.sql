-- Renommer les colonnes de user
alter table "user" rename column "userid" to user_id;
alter table "user" rename column "firstName" to first_name;
alter table "user" rename column "last_Name" to last_name;
alter table "user" rename column "user_Name" to user_name;

-- Renommer les colonnes de course
alter table course rename column "courseID" to course_id;
alter table course rename column "course_Name" to course_name;
alter table course rename column "start_Date" to start_date;
alter table course rename column "end_Date" to end_date;

-- Renommer les colonnes de suscribe
alter table suscribe rename column "Suscribe_uuid" to suscribe_uuid;
alter table suscribe rename column "course_ID" to course_id;
