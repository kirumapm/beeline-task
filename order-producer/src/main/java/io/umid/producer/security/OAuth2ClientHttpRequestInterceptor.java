package io.umid.producer.security;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Setter
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2ClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Value("${client.registration-id:keycloak}")
    private String clientRegistrationId;

    private SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();

    private final OAuth2AuthorizedClientManager authorizedClientManager;


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.trace("Intercepting request");

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

            var oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(clientRegistrationId)
                    .principal(securityContextHolderStrategy.getContext().getAuthentication())
                    .build();

            log.debug("Authorizing service, getting access token");
            OAuth2AuthorizedClient authorizedClient = authorizedClientManager
                    .authorize(oAuth2AuthorizeRequest);


            request.getHeaders().setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
