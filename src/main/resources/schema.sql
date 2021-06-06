drop table if exists posts;
drop table if exists tags;
drop table if exists post_tag;

create table posts(
	id bigint identity not null,
	visible boolean default false not null,
	title varchar(255) not null,
	author varchar(255) not null,
	create_date datetime not null,
	edit_date timestamp not null,
	content varchar(20000) not null,
	tags_line varchar(1000) not null,
	primary key (id)
);

create table tags(
	id bigint identity not null,
	name varchar(255) not null,
	primary key (id)
);

create table post_tag(
	post bigint not null,
	tag bigint not null,
	foreign key (post) references posts(id),
	foreign key (tag) references tags(id)
);