CREATE TABLE public.address(
  id BIGSERIAL PRIMARY KEY,
  city VARCHAR(254)
);

ALTER TABLE public.address OWNER TO rttest;

CREATE TABLE public.person(
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(254),
  middle_name VARCHAR(254),
  last_name VARCHAR(254),
  email VARCHAR(254),
  address_id BIGINT
);

ALTER TABLE public.person OWNER TO rttest;

ALTER TABLE public.person
  ADD CONSTRAINT fk_person_address FOREIGN KEY (address_id)
  REFERENCES public.address (ID);

CREATE INDEX idx_person_address ON public.person (address_id);

CREATE TABLE "user"(
  id BIGSERIAL PRIMARY KEY,
  login VARCHAR(254),
  passwd VARCHAR(254),
  person_id BIGINT,
  email VARCHAR (254)
);
ALTER TABLE public."user" OWNER TO rttest;

ALTER TABLE public."user"
  ADD CONSTRAINT fk_user_person FOREIGN KEY (person_id)
  REFERENCES public.person (ID);

ALTER TABLE "user"
   ADD CONSTRAINT chk_user_login_unic UNIQUE (login);

INSERT INTO public."user"(login, passwd, person_id, email) VALUES('guest', 'guest', null, null);
select nextval ('user_id_seq'::regclass);

INSERT INTO public."user"(login, passwd, person_id, email) VALUES('test2', 'test2', null, null);
select nextval ('user_id_seq'::regclass);


CREATE TABLE public."role"(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(254) not null
);
ALTER TABLE public."role" OWNER TO rttest;

CREATE TABLE public.user_role(
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL
);

ALTER TABLE public.user_role OWNER TO rttest;

ALTER TABLE user_role
   ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);

ALTER TABLE public.user_role
  ADD CONSTRAINT fk_user_role_user FOREIGN KEY (user_id)
  REFERENCES public."user" (ID);

ALTER TABLE public.user_role
  ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_id)
  REFERENCES public."role" (ID);

INSERT INTO public.role VALUES (1, 'Гость');
select nextval ('role_id_seq'::regclass);
INSERT INTO public.role VALUES (2, 'Оператор');
select nextval ('role_id_seq'::regclass);

INSERT INTO public.user_role VALUES (1, 1);


CREATE TABLE public.ticket(
  id BIGSERIAL PRIMARY KEY,
  person_id BIGINT,
  theme VARCHAR(254),
  body VARCHAR(2000)
);

ALTER TABLE public.ticket
  ADD CONSTRAINT fk_ticket_person FOREIGN KEY (person_id)
  REFERENCES public.person (ID);

CREATE INDEX idx_ticket_person ON public.ticket (person_id);

CREATE TABLE public.ticket_recipient(
  ticket_id BIGINT NOT NULL,
  person_id BIGINT NOT NULL
);

ALTER TABLE public.ticket_recipient
   ADD CONSTRAINT ticket_recipient_pkey PRIMARY KEY (ticket_id, person_id);



INSERT INTO public.person(first_name, middle_name, last_name, email) VALUES('test', 'test', 'test', null);
select nextval ('person_id_seq'::regclass);
INSERT INTO public.ticket(person_id, theme, body) VALUES (1,'test theme', 'test message');
select nextval ('ticket_id_seq'::regclass);
INSERT INTO public.ticket_recipient(ticket_id, person_id) VALUES (1, 1);

INSERT INTO public.person(first_name, middle_name, last_name, email) VALUES('test2', 'test2', 'test2', null);
select nextval ('person_id_seq'::regclass);
