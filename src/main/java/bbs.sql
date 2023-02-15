
drop table bbs;

create table bbs(
	seq int auto_increment primary key, -- increment : 값 자동 증가
	id varchar(50) not null,    -- 외래키 설정
	
	ref decimal(8) not null,
	step decimal(8) not null,
	depth decimal(8) not null,
	
	title varchar(200) not null,
	content varchar(4000) not null,
	wdate timestamp not null,-- date라고 해도 되고 timestamp라고 해도 된다.

	del decimal(1) not null,
	readcount decimal(8) not null
);

alter table bbs
add foreign key(id) references member(id);  -- 외래키 실행하는 부분

insert into bbs(id, 
		ref, step, depth, 
		title, content, wdate, del, readcount)
values('id', 
		(select ifnull(max(ref), 0)+1 from bbs b), 0, 0, 
		'title', 'content', now(), 0, 0));



