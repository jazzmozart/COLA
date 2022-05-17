package io.trident.soda.security.menu;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sfinsight
 */
@Configuration
public class MenuAutoConfiguration {

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(MenuBootstrap.class)
    public MenuBootstrap bootstrap() {
        return new MenuBootstrap();
    }

    @Bean
    @ConditionalOnMissingBean(MenuManager.class)
    public MenuManager repository() {
        return new MenuManager();
    }

    @Bean
    @ConditionalOnMissingBean(MenuRegister.class)
    public MenuRegister register() {
        return new MenuRegister();
    }

}
