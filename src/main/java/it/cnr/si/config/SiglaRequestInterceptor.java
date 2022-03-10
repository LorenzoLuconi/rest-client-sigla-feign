package it.cnr.si.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SiglaRequestInterceptor implements RequestInterceptor {
    private final String cds;
    private final String uo;
    private final String cdr;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("X-sigla-cd-cds", cds);
        requestTemplate.header("X-sigla-cd-unita-organizzativa", uo);
        requestTemplate.header("X-sigla-cd-cdr", cdr);
    }
}
