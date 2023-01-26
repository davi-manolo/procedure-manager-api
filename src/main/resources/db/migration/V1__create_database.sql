--Create User table
CREATE TABLE
  public.procedure_user (
    id bigserial NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    sur_name character varying(255) NOT NULL
  );

ALTER TABLE
  public.procedure_user
ADD
  CONSTRAINT procedure_user_pkey PRIMARY KEY (id);

--Create Procedure Type table
CREATE TABLE
  public.procedure_type (
    id bigserial NOT NULL,
    disabled boolean NOT NULL DEFAULT false,
    name character varying(255) NOT NULL,
    percentage double precision NOT NULL,
    user_id bigint NOT NULL
  );

ALTER TABLE
  public.procedure_type
ADD
  CONSTRAINT procedure_type_pkey PRIMARY KEY (id);

--Create Procedure table
CREATE TABLE
  public.procedure (
    id bigserial NOT NULL,
    customer character varying(255) NOT NULL,
    disabled boolean NOT NULL DEFAULT false,
    procedure_date date NOT NULL,
    registration_date timestamp without time zone NOT NULL,
    value numeric(19, 2) NOT NULL,
    value_received numeric(19, 2) NOT NULL,
    procedure_type_id bigint NOT NULL,
    user_id bigint NOT NULL
  );

ALTER TABLE
  public.procedure
ADD
  CONSTRAINT procedure_pkey PRIMARY KEY (id);

