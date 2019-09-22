CREATE TABLE "user" (
    "id" text,
    "first_name" text,
    "last_name" text,
    "email" text,
    "phone" text,
    "grade" integer,
    "role" text,
    "varsity" boolean,
    "shirt_size" text,
    "jacket_size" text,
    "discord_id" text
);

CREATE TABLE "permission" (
    "student_id" text,
    "perm" text
);

CREATE TABLE "subteam" (
    "student_id" text,
    "subteam" text
);

CREATE TABLE "attendance" (
    "student_id" text,
    "event_id" text,
    "hours" integer
);

CREATE TABLE "event" (
     "id" text,
     "date" date,
     "start_time" timestamp,
     "end_time" timestamp,
     "type" text,
     "desc" text
);
