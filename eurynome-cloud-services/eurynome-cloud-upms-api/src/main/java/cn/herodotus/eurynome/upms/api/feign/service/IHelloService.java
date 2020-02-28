package cn.herodotus.eurynome.upms.api.feign.service;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 使用接口前，需要在每个服务上去写对应的调用，现在由服务提供者在对应的接口统一定义
 * 即我开发一个服务，然后我写一个对应服务的消费者调用接口，想调用我服务的，统一继承我的接口即可
 */

public interface IHelloService {
    @GetMapping("/hello")//这里的请求路径需要和upms.provider中的请求路径一致
    String test();//这里的方法名需要和upms.provider中的方法名一致
}
