package com.qding.bigdata.ds.config;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.qding.framework.common.util.SpringContextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.qding.bigdata.ds.controller"})
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private Logger logger = LoggerFactory.getLogger(SwaggerConfig.class.getName());

    @Value("${swagger.enable:false}")
    private boolean enable;

    @Bean
    public Docket customDocket() {
        ApiSelectorBuilder apis = new Docket(DocumentationType.SWAGGER_2)
                /*.groupName("Default Api")

                // 在这里我们代用一个私有方法apis()，传入Predicate<RequestHandler>列表

                .paths(PathSelectors.any())
                .build()*/
                .select()
                .apis(Predicates.or(apis()));
                if(!enable){
                    apis.paths(PathSelectors.none());
                }
        return apis.build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("大数据", "#", "#");
        return new ApiInfo("北斗星api接口",//大标题 title
                "北斗星api接口",//小标题
                "0.0.1",//版本
                "",//termsOfServiceUrl
                contact,//作者
                "",//链接显示文字
                ""//网站链接
        );
    }

    /**
     * 根据basePackage指定多个要扫描的包，然后再过滤掉一些特定的api
     * @return
     */
    private List<Predicate<RequestHandler>> apis() {
        List<Predicate<RequestHandler>> apis = new ArrayList<Predicate<RequestHandler>>();
        ComponentScan componentScan = SwaggerConfig.class.getAnnotation(ComponentScan.class);
        if (null != componentScan) {
            Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
                @Override
                public boolean apply(RequestHandler requestHandler){
                    // 过滤掉特定的API
                    boolean filterIsOk = true;
                    try {
                        SwaggerApiFilter swaggerApiFilter = new SwaggerApiFilter();
                        filterIsOk = swaggerApiFilter == null ? true : swaggerApiFilter.apply(requestHandler);
                    } catch (Exception e){
                        logger.error("execute SwaggerApiFilter error: {}", e.getMessage());
                    }
                    return filterIsOk;
                }
            };
            apis.add(predicate);
        }
        return apis;
    }


}
