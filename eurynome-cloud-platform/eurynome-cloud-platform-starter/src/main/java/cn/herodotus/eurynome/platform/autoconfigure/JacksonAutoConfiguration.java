package cn.herodotus.eurynome.platform.autoconfigure;

import cn.herodotus.eurynome.component.common.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <p>Description: TODO </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/8 17:15
 */
@Slf4j
@Configuration
@AutoConfigureBefore(org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class)
public class JacksonAutoConfiguration {

    @Bean(name = "jacksonObjectMapper")
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        log.debug("[Herodotus] |- Bean [Jackson Object Mapper] Auto Configure.");
        return JacksonUtils.getObjectMapper();
    }

    /**
     * 转换器全局配置
     *
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(jacksonObjectMapper());
    }

}
