alter table IRE_RECOMM_LOG modify ID_NUM VARCHAR2(3000)
/
alter table IRE_USER_RECOMMRESULT modify ID_NUM VARCHAR2(3000)
/
alter table IRE_RECOMM_LOG
	add STATUS VARCHAR2(2)
/

comment on column IRE_RECOMM_LOG.STATUS is '状态,0成功，1失败'
/