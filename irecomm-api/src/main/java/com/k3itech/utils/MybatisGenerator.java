package com.k3itech.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.*;

/**
  * @author dell
 * @since 2021-05-16
 */
public class MybatisGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void codeGenerate(String model,List<String> tablename){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String[] tables= tablename.toArray(new String[tablename.size()]);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        String projectPath = "D:\\工作\\项目\\航天二院\\new\\irecomm\\irecomm-api";
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("jobob");
        gc.setOpen(false);
        //实体属性 Swagger2 注解
//        gc.setSwagger2(true);
        // 是否文件覆盖
        gc.setFileOverride(true);
        // 不需要ActiveRecord(实体类继承Model)特性的请改为false
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML ColumnList
//        gc.setBaseColumnList(true);
//        gc.setIdType(IdType.INPUT);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setUrl("jdbc:oracle:thin:@192.168.0.50:1521:orcl");
        // dsc.setSchemaName("public");
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("test");
        dsc.setPassword("test");
        dsc.setTypeConvert(new OracleTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);

                if ( fieldType.toLowerCase().contains( "timestamp" ) ) {
                    return DbColumnType.TIMESTAMP;
                }

                if ( fieldType.toLowerCase().contains( "date" ) ) {
                    return DbColumnType.DATE;
                }
                if ( fieldType.toLowerCase().contains( "blob" ) ) {
                    return DbColumnType.BYTE_ARRAY;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
}


        });

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName(model);
        pc.setParent("com.k3itech.irecomm");
        mpg.setPackageInfo(pc);


        System.out.println("model:"+model);
        System.out.println("tablesize:"+tablename.size());
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setInclude(tables);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    public static void main(String[] args) {
       Map<String,List<String>> modeltable = new HashMap<>();
       List<String> caltks=new ArrayList<>();
//               ["CALTKS_DK_ZHILIANGWENTIANLI","CALTKS_META_KNOWLEDGE","CALTKS_SYSTEM_FILE"];
       caltks.add("CALTKS_DK_ZHILIANGWENTIANLI");
       caltks.add("CALTKS_META_KNOWLEDGE");
       caltks.add("CALTKS_SYSTEM_FILE");
        caltks.add("CALTKS_TREE_NODE");
        modeltable.put("caltks",caltks);
        List<String> recomm=new ArrayList<>();
        recomm.add("IRE_TAG_WORD");
        recomm.add("IRE_KNOWLEDGE_INFO");
        recomm.add("IRE_USER_ACTION");
        recomm.add("IRE_USER_FOLLOW");
        recomm.add("IRE_USER_INFO");
        recomm.add("IRE_USER_RECOMMRESULT");
        recomm.add("IRE_RECOMM_LOG");
        recomm.add("ETL_TEMP");
//        recomm.add("USER_POST");
        recomm.add("PERSON_POST");
        modeltable.put("re", recomm);
        List<String> yunque=new ArrayList<>();
        yunque.add("ADMIN_GATELOG");
        yunque.add("ADMIN_ORG");
        yunque.add("ADMIN_USER");
        yunque.add("ZZ_MESSAGE_INFO");

        modeltable.put("yunque", yunque);

        for (Map.Entry<String,List<String>> entry:modeltable.entrySet() ) {
            System.out.println("entrykey:"+entry.getKey()+"   valuesize:"+entry.getValue().size());
            codeGenerate(entry.getKey(),entry.getValue());

        }





    }

}