package com.common.model;

import org.springframework.stereotype.Component;


@Component
public class Audience {

//    private static Properties systemProperties;
//
//    static {
//        Resource resource = new ClassPathResource("system.properties");
//        try {
//            InputStream is = resource.getInputStream();
//            systemProperties = new Properties();
//            systemProperties.load(is);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private String clientId = systemProperties.getProperty("audience.clientId");
//    private String base64Secret = systemProperties.getProperty("audience.base64Secret");
//    private String name = systemProperties.getProperty("audience.name");
//    private int expiresSecond = Integer.parseInt(systemProperties.getProperty("audience.expiresSecond"));

    private String clientId = "098f6bcd4621d373cade4e832627b4f6";
    private String base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
    private String name = "restapiuser";
    private int expiresSecond = 86400000;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}
