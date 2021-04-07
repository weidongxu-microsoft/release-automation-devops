package com.azure.dev.main;

import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import reactor.core.publisher.Mono;

class BasicAuthAuthenticationPolicy implements HttpPipelinePolicy {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC = "Basic";
    private final String token;

    BasicAuthAuthenticationPolicy(TokenCredential tokenCredential) {
        token = tokenCredential.getToken(new TokenRequestContext()).block().getToken();
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        if ("http".equals(context.getHttpRequest().getUrl().getProtocol())) {
            return Mono.error(new RuntimeException("token credentials require a URL using the HTTPS protocol scheme"));
        }
        context.getHttpRequest().getHeaders().set(AUTHORIZATION_HEADER, BASIC + " " + token);
        return next.process();
    }
}
