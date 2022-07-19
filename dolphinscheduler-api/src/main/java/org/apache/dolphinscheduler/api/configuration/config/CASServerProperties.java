package org.apache.dolphinscheduler.api.configuration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cas.server")
public class CASServerProperties {
    private String prefix = "https://tianyin-dw-test-cas.iclass.cn";
    private String login = "https://tianyin-dw-test-cas.iclass.cn/login";
    private String logout = "https://tianyin-dw-test-cas.iclass.cn/logout";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}
