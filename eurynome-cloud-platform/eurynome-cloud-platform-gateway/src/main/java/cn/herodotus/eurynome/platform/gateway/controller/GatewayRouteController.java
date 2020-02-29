package cn.herodotus.eurynome.platform.gateway.controller;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.platform.gateway.entity.GatewayRouteDefinition;
import cn.herodotus.eurynome.platform.gateway.service.GatewayRouteDefinitionService;
import cn.herodotus.eurynome.platform.gateway.service.GatewayRouteDefinitionSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网关中重要的两个概念：路由配置和路由规则
 * Spring Cloud Gateway在启动时将路由配置和规则加载到内存中，无法做到不重启网关就可以动态加载对应的路由配置和规则。
 * 该类通过REST方式，实现对内存中的路由配置和罪责进行加载。
 *
 * RESTful 的核心思想就是，客户端发出的数据操作指令都是"动词 + 宾语"的结构。比如，GET /articles这个命令，GET是动词，/articles是宾语。
 *
 * 动词通常就是五种 HTTP 方法，对应 CRUD 操作。
 * ·GET：读取（Read）
 * ·POST：新建（Create）
 * ·PUT：更新（Update）
 * ·PATCH：更新（Update），通常是部分更新
 * ·DELETE：删除（Delete）
 * 根据 HTTP 规范，动词一律大写。
 */
@RestController
@RequestMapping("/routes")
public class GatewayRouteController {

    // TODO 这个Controller放在此处无法进行安全控制可以直接访问。但是放在别处如何进行更新的通知是一个问题。
    // 考虑后面增加消息队列，用消息机制通知更新，然后把这个controller进行迁移
    private final GatewayRouteDefinitionSyncService gatewayRouteDefinitionSyncService;
    private final GatewayRouteDefinitionService gatewayRouteDefinitionService;

    @Autowired
    public GatewayRouteController(GatewayRouteDefinitionSyncService gatewayRouteDefinitionSyncService, GatewayRouteDefinitionService gatewayRouteDefinitionService) {
        this.gatewayRouteDefinitionSyncService = gatewayRouteDefinitionSyncService;
        this.gatewayRouteDefinitionService = gatewayRouteDefinitionService;
    }

    // TODO: Gateway 自身没有安全机制，相关Controller可以直接访问，后面需要提取出来。
    @GetMapping
    public Result<List<GatewayRouteDefinition>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        if (pageSize != 0) {
            Page<GatewayRouteDefinition> pages = gatewayRouteDefinitionService.findByPage(pageNumber, pageSize);
            return new Result<List<GatewayRouteDefinition>>().ok().data(pages.getContent());
        } else {
            return new Result<List<GatewayRouteDefinition>>().failed();
        }
    }


    /**
     * 增加路由
     *
     * 如果数据是简单、平面的key-value键值对，那么使用application/x-www-form-urlencoded简单实用，不需要额外的编解码。@RequestBody不能接收到参数
     *
     * 如果数据是复杂的嵌套关系，有多层数据，那么使用application/json会简化数据的处理。@RequestBody可以接收到参数
     * @param gatewayRouteDefinition
     * @return
     */
    @PostMapping
    public String add(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) {
        return this.gatewayRouteDefinitionSyncService.addRouteDefinition(gatewayRouteDefinition);
    }

    /**
     * 删除路由
     * @param id
     * @return
     */
    @DeleteMapping("/route/delete/{id}")
    public String delete(@PathVariable String id) {
        return this.gatewayRouteDefinitionSyncService.deleteRouteDefinition(id);
    }

    /**
     * 更新路由
     * @param gatewayRouteDefinition
     * @return
     */
    @PostMapping("/route/update")
    public String update(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) {
        return this.gatewayRouteDefinitionSyncService.updateRouteDefinition(gatewayRouteDefinition);
    }

    /**
     * 由于需要数据库和Redis进行同步，所以增加一个同步功能。
     *
     * TODO：Gateway数据和Redis同步功能。从数据库查出所有内容，把Redis清除后，再全部写入。
     * @return
     */
    public String sync() {
        return null;
    }
}
