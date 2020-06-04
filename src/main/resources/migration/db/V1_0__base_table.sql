create table user(
  user_id   varchar(64),
  user_username   varchar(64),
  user_nick   varchar(64),
  user_password   varchar(64),
  user_role   varchar(32),
  primary key (user_id)
);

create table transfer(
  transfer_id   varchar(64),
  transfer_filename   varchar(128),
  transfer_extension    varchar(8),
  transfer_size   bigint,
  transfer_createTime   bigint,
  transfer_uid    varchar(64),
  transfer_targetUser   varchar(64),
  transfer_targetFolder   varchar(64),
  primary key (transfer_id)
);

create table folder(
  folder_id varchar(64),
  folder_name varchar(64),
  folder_comment varchar(128),
  folder_uid varchar(64),
  folder_createTime bigint,
  primary key(folder_id)
);

create table folder_user(
  folder_id varchar(64) references folder(folder_id),
  user_id varchar(64) references user(user_id)
);

insert into user(user_id, user_username, user_nick, user_password, user_role) values ('10001', 'admin', 'Admin', '$2a$10$27hdTOwfMHMyyHpVTsuXY.riZsvxjZLdywGIgEHWVv5RI.rHDhrXq', 'admin');

insert into folder values ('e8d3a8c9a234b4f5e8d223da3e1a','开发部','','10001',1591149747968);

insert into folder_user values ('e8d3a8c9a234b4f5e8d223da3e1a', '10001');
