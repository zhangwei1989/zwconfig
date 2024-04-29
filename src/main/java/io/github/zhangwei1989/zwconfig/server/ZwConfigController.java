package io.github.zhangwei1989.zwconfig.server;

import io.github.zhangwei1989.zwconfig.server.dal.ConfigsMapper;
import io.github.zhangwei1989.zwconfig.server.model.Configs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * config server endpoint
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/4/29
 */
@RestController
@Slf4j
public class ZwConfigController {

    @Autowired
    ConfigsMapper mapper;

    Map<String, Long> VERSIONS;

    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return mapper.list(app, env, ns);
    }

    @GetMapping("/version")
    public long version(String app, String env, String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }

    @RequestMapping("/update")
    public List<Configs> update(@RequestParam("app") String app,
                                @RequestParam("env") String env,
                                @RequestParam("ns") String ns,
                                @RequestBody Map<String, String> params) {
        params.forEach((k, v) -> {
            insertOrUpdate(new Configs(app, env, ns, k, v));
        });

        VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());

        return mapper.list(app, env, ns);
    }

    private void insertOrUpdate(Configs configs) {
        Configs conf = mapper.select(configs.getApp(), configs.getEnv(), configs.getNs(), configs.getPkey());
        if (conf == null) {
            mapper.insert(configs);
        } else {
            mapper.update(configs);
        }
    }

}
