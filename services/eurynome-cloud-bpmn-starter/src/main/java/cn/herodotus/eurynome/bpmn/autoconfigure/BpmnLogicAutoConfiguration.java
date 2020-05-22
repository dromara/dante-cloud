package cn.herodotus.eurynome.bpmn.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/4 12:10
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.bpmn.logic.configuration",
        "cn.herodotus.eurynome.bpmn.logic.controller",
        "cn.herodotus.eurynome.bpmn.logic.service",
        "cn.herodotus.eurynome.bpmn.logic.mapper"
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.eurynome.bpmn.logic.repository"
})
@EntityScan(basePackages = {
        "cn.herodotus.eurynome.bpmn.logic.entity"
})
@MapperScan("cn.herodotus.eurynome.bpmn.logic.mapper")

public class BpmnLogicAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Bean [Bpmn Logic] Auto Configure.");
    }
}
