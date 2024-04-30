create table if not exists `configs` (
    `app` varchar(64) not null,
    `env` varchar(64) not null,
    `ns` varchar(64) not null,
    `pkey` varchar(64) not null,
    `pval` varchar(128) not null
);

insert into configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'a', 'aa100');
insert into configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'b', 'bb100');
insert into configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'c', 'cc100');
