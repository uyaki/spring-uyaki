package com.uyaki.pxc.config;

import com.baomidou.dynamic.datasource.plugin.MasterSlaveAutoRoutingPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 读写分离配置
 *
 * @author uyaki
 * @date 2020 /06/17
 */
@Configuration
public class MasterSlaveAutoRoutingConfig {
    /**
     * Master slave auto routing plugin master slave auto routing plugin.
     *
     * @return the master slave auto routing plugin
     */
    @Bean
    public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin() {
        return new MasterSlaveAutoRoutingPlugin();
    }
}
