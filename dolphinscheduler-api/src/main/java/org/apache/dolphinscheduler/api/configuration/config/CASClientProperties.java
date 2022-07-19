package org.apache.dolphinscheduler.api.configuration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cas.client")
public class CASClientProperties {
    private String prefix = "http://112.17.252.162:30227";
    private String login = "http://112.17.252.162:30227/dolphinscheduler/login/cas";
    private String logoutRelative = "/dolphinscheduler/logout/cas";
    private String logout = "http://112.17.252.162:30227/dolphinscheduler/logout/cas";

    private String frontUrl = "http://112.17.252.162:30227/dolphinscheduler/ui/transition";

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

    public String getLogoutRelative() {
        return logoutRelative;
    }

    public void setLogoutRelative(String logoutRelative) {
        this.logoutRelative = logoutRelative;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getFrontUrl(){
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl){
        this.frontUrl = frontUrl;
    }
}
