
drop table member;

create table member(
	id varchar(50) primary key,
	pwd varchar(50) not null,
	name varchar(50) not null,
	email varchar(50) unique, 
	auth int -- 관리자냐 아니냐 구분하기 위해 
);

select id
from member
where id='abc';   -- 당연히 빈칸이 나온다. table을 만들지 않았기 떄문에

select count(*)
from member
where id='abc';   -- count 그룹 함수를 통해서 table에 자료가 있는지 확인이 가능하다 위와 동일

select * from member;



