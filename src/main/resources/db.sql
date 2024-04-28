create table if not exists `configs` (
    `pkey` varchar(64) not null,
    `pval` varchar(128) not null
);

insert into configs (pkey, pval) values ('a', '1.0.0');
