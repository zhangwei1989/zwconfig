package io.github.zhangwei1989.zwconfig.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configs model
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configs {

    private String app;

    private String env;

    private String ns;

    private String pkey;

    private String pval;

}
