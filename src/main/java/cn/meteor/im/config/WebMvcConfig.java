package cn.meteor.im.config;

import cn.meteor.im.interceptors.ApiInterceptor;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Meteor
 * SpringMVC配置类
 */
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"cn.meteor.im.web", "cn.meteor.im.interceptors"})
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    private ApiInterceptor apiInterceptor;

    @Autowired
    public WebMvcConfig(ApiInterceptor apiInterceptor) {
        logger.info("开始配置springMVC...");
        this.apiInterceptor = apiInterceptor;
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.meteor.im.web"))
                .paths(PathSelectors.any())
                .build();

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("配置Swagger-ui...");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

    /**
     * 静态资源处理
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        logger.info("开启静态资源处理...");
        configurer.enable();
    }


    @Bean
    protected ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setUseCodeAsDefaultMessage(false);
        source.setDefaultEncoding("UTF-8");
        source.setBasename("classpath:ValidatedMessage");
        return source;
    }

    @Bean
    protected LocalValidatorFactoryBean getLocalValidatorFactoryBean() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setProviderClass(HibernateValidator.class);
        validatorFactoryBean.setValidationMessageSource(getMessageSource());
        return validatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return getLocalValidatorFactoryBean();
    }

    /**
     * 配置文件上传
     *
     * @return
     */
    @Bean
    protected CommonsMultipartResolver getMultipartResolver() {
        logger.info("开始配置文件上传...");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(10485760000L);
        resolver.setMaxInMemorySize(10960);
        return resolver;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Web.IM restful API")
                .termsOfServiceUrl("http://127.0.0.1:8080")
                .description("Web.IM restful API")
                .license("MIT")
                .licenseUrl("https://mit-license.org/")
                .version("1.0")
                .build();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("开始配置拦截器...");
        registry
                .addInterceptor(apiInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/localAuth");
        super.addInterceptors(registry);
    }
}
