package cn.herodotus.cloud.bpmn.ability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>Description: 工作流服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/28 15:17
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BpmnAbilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmnAbilityApplication.class, args);
	}

}
