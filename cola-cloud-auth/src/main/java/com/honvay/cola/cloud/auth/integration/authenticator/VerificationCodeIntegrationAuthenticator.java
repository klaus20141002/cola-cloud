package com.honvay.cola.cloud.auth.integration.authenticator;

import com.honvay.cola.cloud.auth.integration.IntegrationAuthentication;
import com.honvay.cola.cloud.framework.core.protocol.Result;
import com.honvay.cola.cloud.vcc.client.VccClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

/**
 * 集成验证码认证
 * @author LIQIU
 * @date 2018-3-31
 **/
@Component
public class VerificationCodeIntegrationAuthenticator extends DefaultAuthenticator {

    private final static String VERIFICATION_CODE_AUTH_TYPE = "vc";

    @Autowired
    private VccClient vccClient;

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String vcToken = integrationAuthentication.getAuthParameter("vc_token");
        String vcCode = integrationAuthentication.getAuthParameter("vc_code");
        //验证验证码
        Result<Boolean> result = vccClient.validate(vcToken, vcCode, null);
        if (!result.getData()) {
            throw new OAuth2Exception("验证码错误");
        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return VERIFICATION_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
