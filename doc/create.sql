create sequence SEQ_RECOMMLOG
/

create table ADMIN_ORG
(
    ID            VARCHAR2(50) not null
        constraint SYS_C0013620
            primary key,
    ORG_NAME      VARCHAR2(70),
    PARENT_ID     VARCHAR2(50),
    ORG_LEVEL     NUMBER(6),
    ORG_SECRET    VARCHAR2(20),
    EXTERNAL_NAME VARCHAR2(100),
    ORDER_ID      NUMBER(10),
    DELETED       VARCHAR2(12),
    DESCRIPTION   VARCHAR2(255),
    CRT_TIME      DATE          default NULL,
    CRT_USER      VARCHAR2(255) default NULL,
    CRT_NAME      VARCHAR2(255) default NULL,
    CRT_HOST      VARCHAR2(255) default NULL,
    UPD_TIME      DATE          default NULL,
    UPD_USER      VARCHAR2(255) default NULL,
    UPD_NAME      VARCHAR2(255) default NULL,
    UPD_HOST      VARCHAR2(255) default NULL,
    ATTR1         VARCHAR2(255) default NULL,
    ATTR2         VARCHAR2(255) default NULL,
    ATTR3         VARCHAR2(255) default NULL,
    ATTR4         VARCHAR2(255) default NULL,
    ORG_CODE      VARCHAR2(50),
    PATH_CODE     VARCHAR2(1000),
    PATH_NAME     VARCHAR2(1000),
    LAST_TIME     TIMESTAMP(6),
    ISDEL         NUMBER
)
/

comment on column ADMIN_ORG.ID is '主键ID---CASIC_ORG_CODE'
/

comment on column ADMIN_ORG.ORG_NAME is '组织名称'
/

comment on column ADMIN_ORG.PARENT_ID is '父级组织ID'
/

comment on column ADMIN_ORG.ORG_LEVEL is '机构层级'
/

comment on column ADMIN_ORG.ORG_SECRET is '保密资格等级---CASIC_ORG_SECRET'
/

comment on column ADMIN_ORG.EXTERNAL_NAME is '单位别名'
/

comment on column ADMIN_ORG.ORDER_ID is '本级单位排序值'
/

comment on column ADMIN_ORG.DELETED is '由更新类型synctype判断得出：新增和修改为0，删除为1'
/

comment on column ADMIN_ORG.ORG_CODE is '组织编码'
/

comment on column ADMIN_ORG.PATH_CODE is '组织编码层级展示'
/

comment on column ADMIN_ORG.PATH_NAME is '组织名称层级展示'
/

comment on column ADMIN_ORG.LAST_TIME is '数据抽取时间'
/

comment on column ADMIN_ORG.ISDEL is '删除标志'
/

create table ADMIN_USER
(
    ID           VARCHAR2(50) not null
        constraint SYS_C0013651
            primary key,
    NAME         VARCHAR2(255),
    P_ID         VARCHAR2(30),
    ORG_CODE     VARCHAR2(50),
    ORG_NAME     VARCHAR2(50),
    SECRET_LEVEL VARCHAR2(12),
    GENDER       VARCHAR2(50),
    ORDER_ID     NUMBER(10),
    EMP_CODE     VARCHAR2(20),
    BIRTH_DATE   DATE,
    O_TEL        VARCHAR2(12),
    O_EMAIL      VARCHAR2(128),
    WORK_POST    VARCHAR2(50),
    TEC_POST     VARCHAR2(50),
    DELETED      VARCHAR2(12),
    REFA         VARCHAR2(50),
    REFB         VARCHAR2(50),
    AVATAR       VARCHAR2(255),
    DESCRIPTION  VARCHAR2(255),
    CRT_TIME     DATE          default NULL,
    CRT_USER     VARCHAR2(255) default NULL,
    CRT_NAME     VARCHAR2(255) default NULL,
    CRT_HOST     VARCHAR2(255) default NULL,
    UPD_TIME     DATE          default NULL,
    UPD_USER     VARCHAR2(255) default NULL,
    UPD_NAME     VARCHAR2(255) default NULL,
    UPD_HOST     VARCHAR2(255) default NULL,
    ATTR1        VARCHAR2(255) default NULL,
    ATTR2        VARCHAR2(255) default NULL,
    ATTR3        VARCHAR2(255) default NULL,
    ATTR4        VARCHAR2(255) default NULL,
    PASSWORD     VARCHAR2(255),
    ATTR5        VARCHAR2(255),
    ATTR6        VARCHAR2(255),
    ATTR7        VARCHAR2(255),
    ATTR8        VARCHAR2(255),
    LAST_TIME    TIMESTAMP(6),
    ISDEL        NUMBER
)
/

comment on column ADMIN_USER.ID is '主键ID'
/

comment on column ADMIN_USER.NAME is '姓名---OPERATOR_NAME'
/

comment on column ADMIN_USER.P_ID is '身份证号'
/

comment on column ADMIN_USER.ORG_CODE is '组织ID---CASIC_ORG_CODE'
/

comment on column ADMIN_USER.ORG_NAME is '所在机构名称'
/

comment on column ADMIN_USER.SECRET_LEVEL is '密级-30：非密；40：一般一类；50：一般二类；60：重要一类；70：重要二类；80：核心一类；90：核心二类'
/

comment on column ADMIN_USER.GENDER is '性别---1：男；2：女；3：未知'
/

comment on column ADMIN_USER.ORDER_ID is '排序号'
/

comment on column ADMIN_USER.EMP_CODE is '出入证号'
/

comment on column ADMIN_USER.BIRTH_DATE is '生日'
/

comment on column ADMIN_USER.O_TEL is '办公电话'
/

comment on column ADMIN_USER.O_EMAIL is '办公邮件'
/

comment on column ADMIN_USER.WORK_POST is '行政岗位'
/

comment on column ADMIN_USER.TEC_POST is '技术岗位'
/

comment on column ADMIN_USER.DELETED is '由更新类型synctype判断得出：新增和修改为0，删除为1'
/

comment on column ADMIN_USER.REFA is '姓'
/

comment on column ADMIN_USER.REFB is '名'
/

comment on column ADMIN_USER.AVATAR is '头像'
/

comment on column ADMIN_USER.DESCRIPTION is '描述'
/

comment on column ADMIN_USER.PASSWORD is '密码'
/

comment on column ADMIN_USER.LAST_TIME is '数据抽取时间'
/

comment on column ADMIN_USER.ISDEL is '删除标志'
/

create table ADMIN_GATELOG
(
    ID         VARCHAR2(50) not null
        constraint SYS_C0013597
            primary key,
    MENU       VARCHAR2(255) default NULL,
    OPT        VARCHAR2(255) default NULL,
    URI        VARCHAR2(255) default NULL,
    CRT_TIME   DATE          default NULL,
    CRT_USER   VARCHAR2(255) default NULL,
    CRT_NAME   VARCHAR2(255) default NULL,
    CRT_HOST   VARCHAR2(255) default NULL,
    IS_SUCCESS CHAR,
    P_ID       VARCHAR2(256),
    LOG_DETAIL VARCHAR2(4000),
    OPT_INFO   CLOB,
    ORG_NAME   VARCHAR2(100),
    ORG_CODE   VARCHAR2(100),
    PATH_CODE  VARCHAR2(1000),
    PATH_NAME  VARCHAR2(1000),
    LAST_TIME  TIMESTAMP(6),
    ISDEL      NUMBER
)
/

comment on column ADMIN_GATELOG.ID is '序号'
/

comment on column ADMIN_GATELOG.MENU is '菜单'
/

comment on column ADMIN_GATELOG.OPT is '操作'
/

comment on column ADMIN_GATELOG.URI is '资源路径'
/

comment on column ADMIN_GATELOG.CRT_TIME is '操作时间'
/

comment on column ADMIN_GATELOG.CRT_USER is '操作人ID'
/

comment on column ADMIN_GATELOG.CRT_NAME is '操作人'
/

comment on column ADMIN_GATELOG.CRT_HOST is '操作主机'
/

comment on column ADMIN_GATELOG.LOG_DETAIL is '详细操作内容'
/

comment on column ADMIN_GATELOG.OPT_INFO is '操作内容'
/

comment on column ADMIN_GATELOG.LAST_TIME is '数据抽取时间'
/

comment on column ADMIN_GATELOG.ISDEL is '删除标志'
/

create table CALTKS_DK_ZHILIANGWENTIANLI
(
    ID                  NUMBER(19) not null
        constraint CALTKS_DK_ZHILIANGWENTIANLI_PK
            primary key,
    XINGHAOMINGCHENG    VARCHAR2(200 char),
    XINGHAOLEIBIE       VARCHAR2(200 char),
    CHANPINDAIHAO       VARCHAR2(200 char),
    CHANPINBIANHAO      VARCHAR2(200 char),
    CHANPINMINGCHENG    VARCHAR2(200 char),
    SUOSHUFENXITONG     VARCHAR2(200 char),
    GUZHANGSHIJIAN      VARCHAR2(200 char),
    GONGZUOJIEDUAN      VARCHAR2(200 char),
    GUZHANGGAISHU       VARCHAR2(3000 char),
    YUANYINFENXI        VARCHAR2(3000 char),
    YUANYINFENLEI       VARCHAR2(3000 char),
    YUANYINFENLEI2      VARCHAR2(200 char),
    PICIXINGWENTI       VARCHAR2(200 char),
    JIUZHENGCUOSHI      VARCHAR2(3000 char),
    WAIXIEGUANLIYUANYIN VARCHAR2(3000 char),
    JIAFANGGUANLI       VARCHAR2(3000 char),
    LAST_TIME           TIMESTAMP(6),
    ISDEL               NUMBER
)
/

comment on table CALTKS_DK_ZHILIANGWENTIANLI is '质量案例信息表'
/

comment on column CALTKS_DK_ZHILIANGWENTIANLI.LAST_TIME is '数据抽取时间'
/

comment on column CALTKS_DK_ZHILIANGWENTIANLI.ISDEL is '删除标志'
/

create table CALTKS_SYSTEM_FILE
(
    ID          NUMBER(19) not null
        constraint CALTKS_SYSTEM_FILE_PK
            primary key,
    FILE_NAME   VARCHAR2(255 char),
    FILE_TYPE   VARCHAR2(255 char),
    FILE_BINARY BLOB,
    SAVE_DATE   TIMESTAMP(6),
    LAST_TIME   TIMESTAMP(6),
    ISDEL       NUMBER
)
/

comment on table CALTKS_SYSTEM_FILE is '使用BLOB存储的知识源文件信息'
/

comment on column CALTKS_SYSTEM_FILE.LAST_TIME is '数据抽取时间'
/

comment on column CALTKS_SYSTEM_FILE.ISDEL is '删除标志'
/

create table CALTKS_META_KNOWLEDGE
(
    ID                         NUMBER(19) not null,
    ABSTRACT_TEXT              VARCHAR2(3000 char),
    SECURITY_LEVEL             VARCHAR2(255 char),
    KNOWLEDGE_SOURCE_FILE_PATH VARCHAR2(255 char),
    KTYPEID                    NUMBER(19) not null,
    DOMAIN_NODEID              NUMBER(19),
    UPLOAD_TIME                TIMESTAMP(6),
    UPLOADERID                 NUMBER(19) not null,
    IS_VISIBLE                 NUMBER(1),
    STATUS                     VARCHAR2(255 char),
    IDENTIFIER                 VARCHAR2(255 char),
    FLASH_FILE_PATH            VARCHAR2(255 char),
    TITLE_NAME                 VARCHAR2(255 char),
    VERID                      NUMBER(19) not null,
    COMMENTRECORDID            NUMBER(19),
    KNOWLEDGETYPE_ID           NUMBER(19),
    LAST_TIME                  TIMESTAMP(6),
    ISDEL                      NUMBER
)
/

comment on table CALTKS_META_KNOWLEDGE is '知识的基础信息'
/

comment on column CALTKS_META_KNOWLEDGE.LAST_TIME is '数据抽取时间'
/

comment on column CALTKS_META_KNOWLEDGE.ISDEL is '删除标志'
/

create table IRE_KNOWLEDGE_INFO
(
    ID               VARCHAR2(255) not null
        constraint I_KOWLEDGE_INFO_PK
            primary key,
    TITLE            VARCHAR2(255),
    CREATE_TIME      TIMESTAMP(6),
    AUTHOR           VARCHAR2(255),
    SECURITY_LEVEL   VARCHAR2(255),
    ABSTRACT_TEXT    VARCHAR2(3000),
    ORG              VARCHAR2(255),
    KTYPE            VARCHAR2(255),
    TAG_MODEL        VARCHAR2(255),
    KNOWLEDGE_SOURCE VARCHAR2(255),
    TAG_KEYWORDS     VARCHAR2(3000),
    TAG_DEVICE       VARCHAR2(255),
    SOURCE_ID        VARCHAR2(255),
    URL              VARCHAR2(255),
    DOMAIN           VARCHAR2(255),
    LAST_TIME        TIMESTAMP(6)
)
/

comment on table IRE_KNOWLEDGE_INFO is '知识信息'
/

comment on column IRE_KNOWLEDGE_INFO.TITLE is '标题'
/

comment on column IRE_KNOWLEDGE_INFO.CREATE_TIME is '时间'
/

comment on column IRE_KNOWLEDGE_INFO.AUTHOR is '作者'
/

comment on column IRE_KNOWLEDGE_INFO.SECURITY_LEVEL is '密级'
/

comment on column IRE_KNOWLEDGE_INFO.ABSTRACT_TEXT is '摘要'
/

comment on column IRE_KNOWLEDGE_INFO.ORG is '组织'
/

comment on column IRE_KNOWLEDGE_INFO.KTYPE is '知识类型'
/

comment on column IRE_KNOWLEDGE_INFO.TAG_MODEL is '相关型号'
/

comment on column IRE_KNOWLEDGE_INFO.KNOWLEDGE_SOURCE is '来源'
/

comment on column IRE_KNOWLEDGE_INFO.TAG_KEYWORDS is '关键词标签'
/

comment on column IRE_KNOWLEDGE_INFO.TAG_DEVICE is '相关设备'
/

comment on column IRE_KNOWLEDGE_INFO.SOURCE_ID is '源表id'
/

comment on column IRE_KNOWLEDGE_INFO.URL is 'url地址'
/

comment on column IRE_KNOWLEDGE_INFO.DOMAIN is '领域'
/

comment on column IRE_KNOWLEDGE_INFO.LAST_TIME is '更新时间'
/

create table IRE_USER_ACTION
(
    ID_NUM         VARCHAR2(255) not null
        constraint I_USER_ACTION_PK
            primary key,
    OPERATE_TYPE   VARCHAR2(255),
    OPERATE_OBJECT VARCHAR2(255),
    OPERATE_TIME   TIMESTAMP(6)
)
/

comment on table IRE_USER_ACTION is '用户行为表'
/

comment on column IRE_USER_ACTION.OPERATE_TYPE is '操作类型'
/

comment on column IRE_USER_ACTION.OPERATE_OBJECT is '操作对象'
/

comment on column IRE_USER_ACTION.OPERATE_TIME is '操作时间'
/

create table IRE_USER_FOLLOW
(
    ID_NUM        VARCHAR2(255) not null
        constraint I_USER_FOLLOW_PK
            primary key,
    USER_NAME     VARCHAR2(255),
    POST          VARCHAR2(255),
    USER_JOB      VARCHAR2(255),
    FOLLOW_MODEL  VARCHAR2(3000),
    FOLLOW_DEVICE VARCHAR2(3000),
    FOLLOW_PRO    VARCHAR2(3000),
    SECRET_LEVEL  VARCHAR2(12),
    ORG_CODE      VARCHAR2(50),
    ORG_NAME      VARCHAR2(50),
    LAST_TIME     TIMESTAMP(6)
)
/

comment on table IRE_USER_FOLLOW is '用户信息标签'
/

comment on column IRE_USER_FOLLOW.ID_NUM is '用户证件号'
/

comment on column IRE_USER_FOLLOW.USER_NAME is '姓名'
/

comment on column IRE_USER_FOLLOW.POST is '岗位'
/

comment on column IRE_USER_FOLLOW.USER_JOB is '职务'
/

comment on column IRE_USER_FOLLOW.FOLLOW_MODEL is '最近关注的型号'
/

comment on column IRE_USER_FOLLOW.FOLLOW_DEVICE is '最近关注的设备分系统'
/

comment on column IRE_USER_FOLLOW.FOLLOW_PRO is '关注的专业关键词'
/

comment on column IRE_USER_FOLLOW.SECRET_LEVEL is '密级'
/

comment on column IRE_USER_FOLLOW.ORG_CODE is '组织'
/

comment on column IRE_USER_FOLLOW.LAST_TIME is '更新时间'
/

create table IRE_USER_INFO
(
    ID_NUM    VARCHAR2(255) not null
        constraint I_USER_INFO_PK
            primary key,
    USER_NAME VARCHAR2(255),
    POST      VARCHAR2(255),
    USER_JOB  VARCHAR2(255)
)
/

comment on table IRE_USER_INFO is '用户信息'
/

comment on column IRE_USER_INFO.ID_NUM is '用户证件号'
/

comment on column IRE_USER_INFO.USER_NAME is '用户姓名'
/

comment on column IRE_USER_INFO.POST is '岗位'
/

comment on column IRE_USER_INFO.USER_JOB is '职务'
/

create table IRE_USER_RECOMMRESULT
(
	ID NUMBER not null
		constraint IRE_USER_RECOMMRESULT_PK
			primary key,
	ID_NUM VARCHAR2(255),
	KNOWLEDGE VARCHAR2(255),
	ISLIKE VARCHAR2(8),
	UPDATE_TIME TIMESTAMP(6)
)
/

comment on column IRE_USER_RECOMMRESULT.ID_NUM is '用户证件号'
/

comment on column IRE_USER_RECOMMRESULT.KNOWLEDGE is '知识'
/

comment on column IRE_USER_RECOMMRESULT.ISLIKE is '用户喜好程度'
/



create table ZZ_MESSAGE_INFO
(
    MSG_ID          VARCHAR2(40) not null
        primary key,
    SENDER          VARCHAR2(40),
    RECEIVER        VARCHAR2(40),
    CREATETIME      DATE,
    LEVELS          VARCHAR2(20),
    CONTENT         VARCHAR2(3000),
    TYPE            VARCHAR2(20),
    ORG_CODE        VARCHAR2(40),
    ORG_NAME        VARCHAR2(200),
    SENDER_SN       VARCHAR2(40),
    ISCROSS         VARCHAR2(20),
    IP              VARCHAR2(60),
    FILE_TYPE       VARCHAR2(4),
    MSG             VARCHAR2(900),
    FILE_ID         VARCHAR2(40),
    FRONT_ID        VARCHAR2(60),
    SENDER_NAME     VARCHAR2(300),
    RECEIVER_NAME   VARCHAR2(300),
    SENDER_AVATAR   VARCHAR2(600),
    RECEIVER_AVATAR VARCHAR2(600),
    SENDER_LEVELS   VARCHAR2(20),
    CANCEL          VARCHAR2(10),
    CANCEL_TIME     DATE,
    LAST_TIME       TIMESTAMP(6),
    ISDEL           NUMBER
)
/

comment on table ZZ_MESSAGE_INFO is '消息'
/

comment on column ZZ_MESSAGE_INFO.MSG_ID is '消息id'
/

comment on column ZZ_MESSAGE_INFO.SENDER is '发送人id'
/

comment on column ZZ_MESSAGE_INFO.RECEIVER is '接收人id'
/

comment on column ZZ_MESSAGE_INFO.CREATETIME is '发送时间'
/

comment on column ZZ_MESSAGE_INFO.LEVELS is '消息密级'
/

comment on column ZZ_MESSAGE_INFO.CONTENT is '消息内容(废弃)'
/

comment on column ZZ_MESSAGE_INFO.TYPE is '消息类型（USER私人 GROUP群 MEET会议）'
/

comment on column ZZ_MESSAGE_INFO.ORG_CODE is '字段暂时废弃不用'
/

comment on column ZZ_MESSAGE_INFO.ORG_NAME is '字段暂时废弃不用'
/

comment on column ZZ_MESSAGE_INFO.ISCROSS is '跨场所类型0科室内 1跨科室 2跨场所'
/

comment on column ZZ_MESSAGE_INFO.IP is '发送人IP'
/

comment on column ZZ_MESSAGE_INFO.FILE_TYPE is '是否附件类型1非附件2图片3附件'
/

comment on column ZZ_MESSAGE_INFO.MSG is '消息内容(文件名称)'
/

comment on column ZZ_MESSAGE_INFO.FILE_ID is '如果是附件，附件ID'
/

comment on column ZZ_MESSAGE_INFO.FRONT_ID is '前端消息ID'
/

comment on column ZZ_MESSAGE_INFO.LAST_TIME is '数据抽取时间'
/

comment on column ZZ_MESSAGE_INFO.ISDEL is '删除标志'
/





create table IRE_RECOMM_LOG
(
    ID        NUMBER(20) not null
        constraint I_RECOMM_LOG_PK
            primary key,
    ID_NUM    VARCHAR2(20),
    KNOWLEDGE VARCHAR2(255),
    POST_TIME TIMESTAMP(6),
    KTYPE     VARCHAR2(255)
)
/

comment on column IRE_RECOMM_LOG.POST_TIME is '推送时间'
/

create table ETL_TEMP
(
    ID           VARCHAR2(50 char) not null
        primary key,
    EXTRACT_TIME TIMESTAMP(6)
)
/

comment on column ETL_TEMP.ID is '表名'
/

comment on column ETL_TEMP.EXTRACT_TIME is '抽取时间'
/



create table PERSON_POST
(
    IDENTITY_NO VARCHAR2(100) not null
        constraint USER_POST_PK
            primary key,
    VDEFL       VARCHAR2(100),
    CRT_TIME    TIMESTAMP(6) default current_timestamp
)
/

comment on column PERSON_POST.IDENTITY_NO is '身份证'
/

comment on column PERSON_POST.VDEFL is '岗位'
/

create table CALTKS_TREE_NODE
(
    ID               NUMBER(19)        not null
        primary key,
    TREE_NODE_TYPE   VARCHAR2(50 char) not null,
    NODE_DESCRIPTION VARCHAR2(255 char),
    CODE             VARCHAR2(255 char),
    NODE_NAME        VARCHAR2(255 char),
    PARENT_ID        NUMBER(19),
    ORDER_ID         NUMBER(19),
    ORG_CODE         VARCHAR2(200 char),
    PATH_CODE        VARCHAR2(300 char),
    PATH_NAME        VARCHAR2(300 char),
    ORG_SECRET       VARCHAR2(200 char),
    EXTERNAL_NAME    VARCHAR2(300 char),
    DESCRIPTION      VARCHAR2(300 char),
    PARENT_CODE      VARCHAR2(300 char),
    ORG_LEVEL        NUMBER(19),
    LAST_TIME        TIMESTAMP(6),
    ISDEL            NUMBER
)
/

comment on column CALTKS_TREE_NODE.ISDEL is '删除标志'
/


create table IRE_TAG_WORD
(
    WORD     VARCHAR2(255) not null
        constraint IRE_TAG_WORD_PK
            primary key,
    TAG_TYPE VARCHAR2(255)
)
/

comment on column IRE_TAG_WORD.WORD is '词语'
/

comment on column IRE_TAG_WORD.TAG_TYPE is '词语类型（型号、设备分系统、阶段、领域、军兵种、指标、专业方向等）'
/

create table IRE_PERSON_JOB
(
    ID_NUM VARCHAR2(20) not null
        constraint SYS_C0012357
            primary key,
    NAME VARCHAR2(255),
    ORG_CODE VARCHAR2(255),
    TYPE VARCHAR2(255),
    PROFFESSION VARCHAR2(255),
    DIRECTION VARCHAR2(255)
)
/

comment on column IRE_PERSON_JOB.ID_NUM is '身份证'
/

comment on column IRE_PERSON_JOB.NAME is '姓名'
/

comment on column IRE_PERSON_JOB.ORG_CODE is '组织'
/

comment on column IRE_PERSON_JOB.TYPE is '类别'
/

comment on column IRE_PERSON_JOB.PROFFESSION is '专业'
/

comment on column IRE_PERSON_JOB.DIRECTION is '方向'
/

create table IRE_TAG_STRUCTURE
(
    ID NUMBER not null
        constraint SYS_C0012359
            primary key,
    PARENT_ID NUMBER,
    PARENT_IDS VARCHAR2(255),
    TEXT VARCHAR2(255),
    CODE VARCHAR2(255),
    PARENT_CODE VARCHAR2(255),
    PARENT_CODES VARCHAR2(255),
    LEAF NUMBER(2),
    GRADE NUMBER(2)
)
/

comment on column IRE_TAG_STRUCTURE.ID is '标识'
/

comment on column IRE_TAG_STRUCTURE.PARENT_ID is '父标识'
/

comment on column IRE_TAG_STRUCTURE.PARENT_IDS is '所有父标识'
/

comment on column IRE_TAG_STRUCTURE.TEXT is '名称'
/

comment on column IRE_TAG_STRUCTURE.CODE is '编码'
/

comment on column IRE_TAG_STRUCTURE.PARENT_CODE is '父编码'
/

comment on column IRE_TAG_STRUCTURE.PARENT_CODES is '所有父编码'
/

comment on column IRE_TAG_STRUCTURE.LEAF is '是否叶子节点（0根节点，1为叶子节点）'
/

comment on column IRE_TAG_STRUCTURE.GRADE is '级别'
/

create sequence SEQ_RECOMMRESULT
    minvalue 1
    nomaxvalue
    start with 1
    increment by 1
    nocache
    order;
/

create sequence SEQ_RECOMMLOG
    minvalue 1
    nomaxvalue
    start with 1
    increment by 1
    nocache
    order;
/

create sequence SEQ_TAG_STRUCTURE
    minvalue 1
    nomaxvalue
    start with 1
    increment by 1
    nocache
    order;
/