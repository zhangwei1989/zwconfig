create table if not exists `configs` (
    `app` varchar(64) not null,
    `env` varchar(64) not null,
    `ns` varchar(64) not null,
    `pkey` varchar(64) not null,
    `pval` varchar(128) not null
);

insert into configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'zw.a', 'dev100');
insert into configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'zw.b', 'http://localhost:9129');
insert into configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'zw.c', 'cc100');
