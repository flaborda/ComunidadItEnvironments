CREATE SEQUENCE public.usuarios_id_seq
    INCREMENT 1
    START 5
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.usuarios_id_seq
    OWNER TO postgres;

-- Table: public.usuarios

-- DROP TABLE public.usuarios;

CREATE TABLE public.usuarios
(
    id integer NOT NULL DEFAULT nextval('usuarios_id_seq'::regclass),
    nombre text COLLATE pg_catalog."default" NOT NULL,
    contrasenia text COLLATE pg_catalog."default" NOT NULL,
    activo boolean NOT NULL,
    CONSTRAINT usuarios_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuarios
    OWNER to postgres;


    