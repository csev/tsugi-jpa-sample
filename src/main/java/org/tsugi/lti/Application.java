/**
 * Copyright 2014 Unicon (R)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tsugi.lti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringValueResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

@ComponentScan({"org.tsugi.lti", "org.tsugi.zippy"})
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement // enables TX management and @Transaction
@EnableCaching // enables caching and @Cache* tags
@EnableWebMvcSecurity // enable spring security and web mvc hooks
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// allows @Secured flag - proxyTargetClass = true causes this to die
public class Application extends WebMvcConfigurerAdapter {

    final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
System.out.println("+=+=++++++++++++++++++++++++++++ main");

        SpringApplication.run(Application.class, args);
    }

    /**
     * Allows access to the various config values (from application.properties) using @Value
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer() {
            @Override
            protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess, StringValueResolver valueResolver) {
                log.info("doProcessProperties");
                super.doProcessProperties(beanFactoryToProcess, valueResolver);
            }
        };
    }

    /**
     * Creates a CacheManager which allows the spring caching annotations to work
     * Annotations: Cacheable, CachePut and CacheEvict
     * http://spring.io/guides/gs/caching/
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(); // not appropriate for production, try JCacheCacheManager or HazelcastCacheManager instead
    }

    @Configuration
    @Order(1) // HIGHEST
    public static class LTISecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @PostConstruct
        public void init() {
            log.info("init()");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            log.info("configure()");
            http.antMatcher("/tsugi/**").csrf().disable();
        }
    }

    @Order(67) // LOWEST
    @Configuration
    public static class NoAuthConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/console/**");
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // this ensures security context info (Principal, sec:authorize, etc.) is accessible on all paths
            http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
        }
    }

}
