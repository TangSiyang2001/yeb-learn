package com.tsy.yebserver.config.data;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steven.T
 * @date 2021/11/13
 */
@Configuration(proxyBeanMethods = false)
@MapperScan("com.tsy.yebserver.dao.mapper")
public class MybatisConfiguration {
    /**
     * 注册分页插件
     * 另外，千万要注意，存xml的文件夹命名时要用/而不是用点隔开,或者索性就在application.yml里面配置：
     * mapper-locations: classpath:mapper/*.xml
     * 然后xml存在resource下的mapper里面
     *
     * @return 分页拦截器
     */
    @Bean
    MybatisPlusInterceptor pageInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
