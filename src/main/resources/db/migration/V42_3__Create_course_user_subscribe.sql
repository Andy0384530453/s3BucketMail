create table if not exists "user"
(
    "userid"    varchar(255) not null
        constraint user_pk primary key,
    "firstName" varchar(255) not null,
    "last_Name" varchar(255) not null,
    "user_Name" varchar(255),
    "mail"      varchar(255)
);

create table if not exists "course"
(
    "courseID"   varchar(255)
        constraint course_pk primary key,
    "course_Name" varchar(255),
    "start_Date"  timestamp with time zone,
    "end_Date"    timestamp with time zone
);

create table if not exists "suscribe"
(
    "Suscribe_uuid" varchar(255)
        constraint suscribe_pk primary key,
    "user_id"       varchar(255)
        constraint suscribe_user_fk references "user"("userid"),
    "course_ID"     varchar(255)
        constraint suscribe_course_fk references "course"("courseID")
);
