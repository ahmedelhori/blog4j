drop table if exists blogposts;
drop table if exists tags;
drop table if exists blogpost_tag;

create table blogposts(
	id bigint identity not null,
	visible boolean default false not null,
	title varchar(255) not null,
	author varchar(255) not null,
	create_date datetime not null,
	edit_date timestamp not null,
	content varchar(20000) not null,
	primary key (id)
);

create table tags(
	id bigint identity not null,
	name varchar(255) not null,
	primary key (id)
);

create table blogpost_tag(
	blogpost bigint not null,
	tag bigint not null,
	foreign key (blogpost) references blogposts(id),
	foreign key (tag) references tags(id)
);