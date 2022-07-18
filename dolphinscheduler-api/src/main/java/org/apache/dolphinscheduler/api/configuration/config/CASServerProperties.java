package org.apache.dolphinscheduler.api.configuration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cas.server")
public class CASServerProperties {
    private String prefix = "http://112.17.252.162:31477";
    private String login = "http://112.17.252.162:31477/login";
    private String logout = "http://112.17.252.162:31477/logout";

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
