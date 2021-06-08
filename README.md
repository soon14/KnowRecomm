## KnowRecomm

`工程结构目录`
# config

 ireconfig-repo 配置文件目录

# **doc**

   create.sql 数据库表结构

# **irecomm-api**

    共用模块

# **irecomm-calc**

     标签匹配计算模块

# **irecomm-post**
     推送模块

# **irecomm-tag**
     打标签模块

# **irecomm-webservice**
     webservice获取用户岗位信息

# 数据流程
服务间启动没有先后依赖关系，只是库表数据间处理有流程。  
`目前流程如下`  
**1**、kettle数据抽取抽取，更ETL_TEMP表时间字段

**2**、tag服务每20分钟执行调度任务去获取抽取的最新时间，再根据时间（抽取结束1-2小时内）和redis中的处理标记DEAL_FLAG为POST_OVER_FLG进行打标签  
将用户和知识标签存入IRE_USER_FOLLOW和IRE_KNOWLEDGE_INFO表中

**3**、calc计算服务每20分钟执行调度任务，根据redis中的处理标记为TAG_OVER_FLG按规则进行计算产生匹配结果放入到redis中

**4**、post根据定时调度时间在处理标记为CALC_OVER_FLG执行推送，推送时间按照quartz配置进行更改

**5**、相关接口为手动执行产生每个过程的数据，不去做处理标记判断

irecommpost/getresults？size=10&current=1&id_num=11
接口根据用户身份证进行模糊匹配获取计算匹配结果


