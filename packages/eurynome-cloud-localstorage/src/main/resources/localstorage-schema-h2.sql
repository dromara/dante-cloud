create table security_metadata
(
    metadata_id    varchar(64) not null
        constraint security_metadata_pkey
            primary key,
    create_time    timestamp,
    ranking        integer,
    update_time    timestamp,
    description    varchar(512),
    is_reserved    boolean,
    reversion      integer,
    status         integer,
    authority_type varchar(255),
    class_name     varchar(512),
    metadata_code  varchar(128),
    metadata_name  varchar(1024),
    method_name    varchar(128),
    parent_id      varchar(64),
    request_method varchar(20),
    service_id     varchar(128),
    url            varchar(2048)
);

create index security_metadata_id_idx
    on security_metadata (metadata_id);

