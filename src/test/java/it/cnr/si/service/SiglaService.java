package it.cnr.si.service;

import com.google.gson.*;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import it.cnr.si.config.AuthenticationOnlyErrorDecoder;
import it.cnr.si.config.Sigla;
import it.cnr.si.config.SiglaRequestInterceptor;
import it.cnr.si.dto.docamm.FatturaAttivaDTO;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class SiglaService {

    private final String baseUrl = PropertiesService.getProp("sigla.url");
    private final String username = PropertiesService.getProp("sigla.username");
    private final String password = PropertiesService.getProp("sigla.password");

    private final String cds = PropertiesService.getProp("sigla.cds");
    private final String uo = PropertiesService.getProp("sigla.uo");
    private final String cdr = PropertiesService.getProp("sigla.cdr");

    private final Sigla sigla;
    private final Sigla siglaPrint;

    public SiglaService() {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(json.getAsLong()), ZoneId.of("Europe/Rome"));
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(ZonedDateTime.of(src, ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }).create();

        final SiglaRequestInterceptor siglaRequestInterceptor = new SiglaRequestInterceptor(cds, cdr, uo);
        final BasicAuthRequestInterceptor basicAuthRequestInterceptor = new BasicAuthRequestInterceptor(username, password);
        final AuthenticationOnlyErrorDecoder authenticationOnlyErrorDecoder = new AuthenticationOnlyErrorDecoder();

        sigla = Feign.builder()
                .decoder(new GsonDecoder(gson))
                .encoder(new GsonEncoder(gson))
                .requestInterceptor(basicAuthRequestInterceptor)
                .requestInterceptor(siglaRequestInterceptor)
                .errorDecoder(authenticationOnlyErrorDecoder)
                .target(Sigla.class, baseUrl);

        siglaPrint = Feign.builder()
                .encoder(new GsonEncoder(gson))
                .requestInterceptor(basicAuthRequestInterceptor)
                .requestInterceptor(siglaRequestInterceptor)
                .errorDecoder(authenticationOnlyErrorDecoder)
                .target(Sigla.class, baseUrl);

    }

    public FatturaAttivaDTO getFatturaByProgressivo(Integer esercizio, Long pg) {
        return sigla.getFatturaByProgressivo(esercizio, pg);
    }

    public List<FatturaAttivaDTO> inserisciFatture(List<FatturaAttivaDTO> fatture) {
        return sigla.inserisciFatture(fatture);
    }

    public Long inserisciDatiPerStampa(Integer esercizio, Long pgFattura) {
        return sigla.inserisciDatiPerStampa(esercizio, pgFattura);
    }

    public byte[] stampaFattura(Long pgStampa) {
        return siglaPrint.stampaFattura(pgStampa);
    }

}
