SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: amir
--

CREATE TABLE public.users (
                              id bigint NOT NULL,
                              email character varying(255),
                              name character varying(255),
                              password character varying(255)
);


ALTER TABLE public.users OWNER TO amir;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: amir
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO amir;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: amir
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: amir
--

SELECT pg_catalog.setval('public.users_id_seq', 20, true);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);



SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: amir
--

CREATE TABLE public.roles (
                              role_id bigint NOT NULL,
                              name character varying(255)
);


ALTER TABLE public.roles OWNER TO amir;

--
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: amir
--

CREATE SEQUENCE public.roles_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_role_id_seq OWNER TO amir;

--
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: amir
--

ALTER SEQUENCE public.roles_role_id_seq OWNED BY public.roles.role_id;


--
-- Name: roles role_id; Type: DEFAULT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.roles ALTER COLUMN role_id SET DEFAULT nextval('public.roles_role_id_seq'::regclass);


--
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: amir
--

SELECT pg_catalog.setval('public.roles_role_id_seq', 1, true);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- Name: roles uk_ofx66keruapi6vyqpv6f2or37; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uk_ofx66keruapi6vyqpv6f2or37 UNIQUE (name);


--
-- PostgreSQL database dump complete
--

CREATE TABLE public.users_roles (
                                    user_id bigint NOT NULL,
                                    role_id bigint NOT NULL
);


ALTER TABLE public.users_roles OWNER TO amir;

--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: amir
--

--
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users_roles fk2o0jvgh89lemvvo17cbqvdxaa; Type: FK CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: users_roles fkj6m8fwv7oqv74fcehir1a9ffy; Type: FK CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES public.roles(role_id);

CREATE TABLE public.tickets (
                                id bigint NOT NULL,
                                content character varying(255),
                                seen boolean NOT NULL,
                                severity integer NOT NULL,
                                status integer,
                                title character varying(255),
                                user_id bigint NOT NULL,
                                created timestamp without time zone,
                                updated timestamp without time zone
);


ALTER TABLE public.tickets OWNER TO amir;

--
-- Name: tickets_id_seq; Type: SEQUENCE; Schema: public; Owner: amir
--

CREATE SEQUENCE public.tickets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tickets_id_seq OWNER TO amir;

--
-- Name: tickets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: amir
--

ALTER SEQUENCE public.tickets_id_seq OWNED BY public.tickets.id;


--
-- Name: tickets id; Type: DEFAULT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public.tickets_id_seq'::regclass);


--
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: amir
--

--
-- Name: tickets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: amir
--

SELECT pg_catalog.setval('public.tickets_id_seq', 9, true);


--
-- Name: tickets tickets_pkey; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--


CREATE TABLE public.ticket_to_ticket (
                                         id bigint NOT NULL,
                                         reply_id bigint NOT NULL,
                                         ticket_id bigint NOT NULL
);


ALTER TABLE public.ticket_to_ticket OWNER TO amir;

--
-- Name: ticket_to_ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: amir
--

CREATE SEQUENCE public.ticket_to_ticket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ticket_to_ticket_id_seq OWNER TO amir;

--
-- Name: ticket_to_ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: amir
--

ALTER SEQUENCE public.ticket_to_ticket_id_seq OWNED BY public.ticket_to_ticket.id;


--
-- Name: ticket_to_ticket id; Type: DEFAULT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.ticket_to_ticket ALTER COLUMN id SET DEFAULT nextval('public.ticket_to_ticket_id_seq'::regclass);


--
-- Data for Name: ticket_to_ticket; Type: TABLE DATA; Schema: public; Owner: amir
--

--
-- Name: ticket_to_ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: amir
--

SELECT pg_catalog.setval('public.ticket_to_ticket_id_seq', 9, true);


--
-- Name: ticket_to_ticket ticket_to_ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.ticket_to_ticket
    ADD CONSTRAINT ticket_to_ticket_pkey PRIMARY KEY (id);



CREATE TABLE public.subscription (
                                     subscription_id bigint NOT NULL,
                                     notified boolean NOT NULL,
                                     ticket_id bigint,
                                     user_id bigint
);


ALTER TABLE public.subscription OWNER TO amir;

--
-- Name: subscription_subscription_id_seq; Type: SEQUENCE; Schema: public; Owner: amir
--

CREATE SEQUENCE public.subscription_subscription_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subscription_subscription_id_seq OWNER TO amir;

--
-- Name: subscription_subscription_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: amir
--

ALTER SEQUENCE public.subscription_subscription_id_seq OWNED BY public.subscription.subscription_id;


--
-- Name: subscription subscription_id; Type: DEFAULT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.subscription ALTER COLUMN subscription_id SET DEFAULT nextval('public.subscription_subscription_id_seq'::regclass);


--
-- Data for Name: subscription; Type: TABLE DATA; Schema: public; Owner: amir
--

--
-- Name: subscription_subscription_id_seq; Type: SEQUENCE SET; Schema: public; Owner: amir
--

SELECT pg_catalog.setval('public.subscription_subscription_id_seq', 8, true);


--
-- Name: subscription subscription_pkey; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.subscription
    ADD CONSTRAINT subscription_pkey PRIMARY KEY (subscription_id);


--
-- Name: subscription ukfexny9hebtpmk1ixhbdan9xox; Type: CONSTRAINT; Schema: public; Owner: amir
--

ALTER TABLE ONLY public.subscription
    ADD CONSTRAINT ukfexny9hebtpmk1ixhbdan9xox UNIQUE (ticket_id, user_id);

