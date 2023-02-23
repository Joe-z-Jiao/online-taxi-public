package com.mashibing.serviceprice.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成配置文件
 */
public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-price?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","95uoh16j")
                .globalConfig(builder -> {
                    builder.author("zhourj").fileOverride().outputDir("/Users/zhouruijie/Documents/workspaces/online-taxi-2023-public/online-taxi-public/service-price/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mashibing.serviceprice").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "/Users/zhouruijie/Documents/workspaces/online-taxi-2023-public/online-taxi-public/serviceprice/src/main/java/com/mashibing/serviceprice/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("price_rule");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
