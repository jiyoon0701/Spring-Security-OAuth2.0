package com.cos.security1.config.auth;

import com.cos.security1.model.ClientType;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class ClientDetailsImpl extends BaseClientDetails {
    private static final long serialVersionUID = -8263549600098155096L;

    private ClientType clientType;

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
