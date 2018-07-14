package cn.meteor.im.config;

import cn.meteor.im.filter.CorsFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

/**
 * @author Meteor
 */
public class WebContextInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {
                new CharacterEncodingFilter("UTF-8",true)
        };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        super.customizeRegistration(registration);
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {PropertyConfig.class, WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
