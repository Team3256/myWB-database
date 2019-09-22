CREATE TABLE STUDENT (
    "id" text,
    "first_name" text,
    "last_name" text,
    "email" text,
    "phone" text,
    "grade" integer,
    "role" text,
    "varsity" boolean,
    "safety_glasses" boolean,
    "shirt_size" text
);

CREATE TABLE PERMISSION (
    "student_id" text,
    "perm" text
);

CREATE TABLE ATTENDANCE (
    "student_id" text,
    "event_id" text,
    "hours" integer
);

CREATE TABLE EVENT (
     "id" text,
     "date" date,
     "start_time" timestamp,
     "end_time" timestamp,
     "type" text,
     "desc" text
);
