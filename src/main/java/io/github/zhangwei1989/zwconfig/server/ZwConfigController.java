package io.github.zhangwei1989.zwconfig.server;

import lombok.extern.slf4j.Slf4j;
import io.github.zhangwei1989.zwconfig.server.mapper.ConfigsMapper;
import io.github.zhangwei1989.zwconfig.server.model.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ZwConfigController
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/4/30
 */
@Slf4j
@RestController
public class ZwConfigController {

    @Autowired
    ConfigsMapper configsMapper;

    private Map<String, Long> VERSIONS = new ConcurrentHashMap<>();

    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return configsMapper.list(app, env, ns);
    }

    @PostMapping("/update")
    public List<Configs> update(@RequestParam String app,
                                @RequestParam String env,
                                @RequestParam String ns,
                                @RequestBody Map<String, String> params) {
        params.keySet().forEach((k) -> {
            Configs configs = new Configs();
            configs.setApp(app);
            configs.setEnv(env);
            configs.setNs(ns);
            configs.setPkey(k);
            Configs oldConfigs = configsMapper.select(configs);

            if (oldConfigs == null) {
                configsMapper.insert(new Configs(app, env, ns, k, params.get(k)));
            } else {
                configsMapper.update(new Configs(app, env, ns, k, params.get(k)));
            }
            VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());
        });

        return configsMapper.list(app, env, ns);
    }

    @GetMapping("/version")
    public Long version(String app, String env, String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }
}
