-- Drop les FK puis les tables créées par V42_3 (varchar + camelCase)
alter table suscribe drop constraint if exists suscribe_user_fk;
alter table suscribe drop constraint if exists suscribe_course_fk;
drop table if exists suscribe cascade;
drop table if exists course cascade;
drop table if exists "user" cascade;

-- Recréation : uuid + snake_case (compatible naming strategy par défaut)
create table "user"
(
    user_id    uuid not null
        constraint user_pk primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    user_name  varchar(255),
    mail       varchar(255)
);

create table course
(
    course_id   uuid
        constraint course_pk primary key,
    course_name varchar(255),
    start_date  timestamp with time zone,
    end_date    timestamp with time zone
);

create table suscribe
(
    suscribe_uuid uuid
        constraint suscribe_pk primary key,
    user_id       uuid
        constraint suscribe_user_fk references "user"(user_id),
    course_id     uuid
        constraint suscribe_course_fk references course(course_id)
);
