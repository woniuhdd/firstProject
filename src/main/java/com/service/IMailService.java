package com.service;

import com.model.Email;

public interface IMailService {

    /**
     * 纯文本发送
     *
     * @param mail
     */
    public void send(Email mail);

    /**
     * 模版发送 freemarker
     *
     * @param mail
     */
    public void sendFreemarker(Email mail);

}
