package com.zf.study.mem;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CodeAutoGenerate {
    public static void main(String[] args) {

        //模块名
        String module = "study-mem";

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath+module+"/src/main/java");
        globalConfig.setAuthor("zf")
                    .setOpen(false)
                    .setFileOverride( true )
                    .setActiveRecord( true )// 不需要ActiveRecord特性的请改为false
                    .setEnableCache( false )// XML 二级缓存
                    .setBaseResultMap( true )// XML ResultMap
                    .setBaseColumnList( false );// XML columList


        //数据源配置
        DataSourceConfig dataConfig = new DataSourceConfig();
        dataConfig.setDbType(DbType.MYSQL)
                  .setDriverName("com.mysql.cj.jdbc.Driver")
                  .setUrl("jdbc:mysql://192.168.146.128:3306/amy?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC")
                  .setUsername("root")
                  .setPassword("root");

        //包名配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.zf.study.mem")
                     .setController("controller")
                     .setService("service")
                     .setServiceImpl("service.impl")
                     .setEntity("entity")
                     .setMapper("mapper");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setInclude(new String[]{"stock","stock_order"}) //需要生成的表，不写就是生成全部
                .setEntityBuilderModel( true )
                //使用lombok注解
                .setEntityLombokModel( true );

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        //资源配置
        String resourcesPath = projectPath+ module+ "/src/main/resources";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add( new FileOutConfig( "/templates/mapper.xml.vm" ) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return resourcesPath + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";

            }
        } );
        cfg.setFileOutConfigList( focList );
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig)
                     .setDataSource(dataConfig)
                     .setPackageInfo(packageConfig)
                     .setStrategy(strategy)
                     .setCfg(cfg)
                     .setTemplate(new TemplateConfig().setXml(null));
        autoGenerator.execute();
    }
}
