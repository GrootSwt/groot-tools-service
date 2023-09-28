create table friend
(
    id              varchar(20)      not null
        primary key,
    user_id         varchar(20)      not null comment '用户id',
    friend_id       varchar(20)      not null comment '好友id',
    relation_status int              not null comment '关联状态',
    comment_name    varchar(20)      not null comment '备注名',
    create_time     datetime         not null comment '创建时间',
    update_time     datetime         not null comment '更新时间',
    deleted         bit default b'0' not null comment '删除标志'
)
    comment '好友';

create table memorandumWebSocketHandler
(
    id          varchar(20)      not null
        primary key,
    user_id     varchar(20)      not null comment '用户id',
    content     varchar(2000)    not null comment '内容',
    create_time datetime         not null comment '创建时间',
    update_time datetime         not null comment '更新时间',
    deleted     bit default b'0' not null comment '删除标志'
)
    comment '备忘录';

create table message
(
    id          varchar(20)      not null,
    sender_id   varchar(20)      not null comment '消息发送方id',
    receiver_id varchar(20)      not null comment '接收方id',
    read_status int              not null comment '消息读取状态',
    content     varchar(2000)    not null comment '消息内容',
    create_time datetime         not null comment '创建时间',
    update_time datetime         not null comment '创建时间',
    deleted     bit default b'0' not null comment '删除标志'
)
    comment '消息';

create table register
(
    id           varchar(20)      not null
        primary key,
    account      varchar(20)      not null comment '账户',
    password     varchar(20)      not null comment '密码',
    display_name varchar(20)      not null comment '表示名',
    phone_number varchar(20)      null comment '手机号',
    email        varchar(50)      null comment '邮件地址',
    create_time  datetime         not null comment '创建时间',
    update_time  datetime         not null comment '更新时间',
    deleted      bit default b'0' not null comment '删除标志',
    status       int              not null
)
    comment '注册';

create table user
(
    id           varchar(20)      not null
        primary key,
    account      varchar(20)      not null comment '账号',
    password     varchar(20)      not null comment '密码',
    display_name varchar(20)      not null comment '表示名',
    phone_number varchar(20)      null comment '电话号码',
    email        varchar(50)      null comment '邮件地址',
    create_time  datetime         not null comment '创建时间',
    update_time  datetime         not null comment '更新时间',
    deleted      bit default b'0' not null comment '删除标志'
)
    comment '用户';


