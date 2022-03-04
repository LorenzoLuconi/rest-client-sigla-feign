package it.cnr.si.config;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import it.cnr.si.dto.docamm.FatturaAttivaDTO;

import java.util.List;

@Headers({"Content-Type: application/json"})
public interface Sigla {

    @RequestLine("GET /fatturaattiva?pg={pg}")
    FatturaAttivaDTO getFatturaByProgressivo(@Param("pg") long pg);

    @RequestLine("POST /fatturaattiva")
    List<FatturaAttivaDTO> inserisciFatture(List<FatturaAttivaDTO> fatture);

    @RequestLine("POST /fatturaattiva/inserisciDatiPerStampa?pg={pg}")
    Long inserisciDatiPerStampa(@Param("pg") long pg);

    @RequestLine("GET /fatturaattiva/print?pgStampa={pgStampa}")
    byte[] stampaFattura(@Param("pgStampa") long pgStampa);

}
