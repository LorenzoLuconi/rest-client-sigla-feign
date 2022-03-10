package it.cnr.si.service;


import it.cnr.si.dto.docamm.FatturaAttivaDTO;
import it.cnr.si.dto.docamm.FatturaAttivaRigaDTO;
import it.cnr.si.dto.docamm.FatturaAttivaScadDTO;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FatturaAttivaTest {

    private final SiglaService siglaService = new SiglaService();

    @Test
    public void inserisciFatture() {
        FatturaAttivaDTO fatturaAttivaDTO = new FatturaAttivaDTO();
        fatturaAttivaDTO.setEsercizio(LocalDateTime.now().getYear());
        fatturaAttivaDTO.setCd_cds_origine(PropertiesService.getProp("sigla.cds"));
        fatturaAttivaDTO.setCd_uo_origine(PropertiesService.getProp("sigla.uo"));
        fatturaAttivaDTO.setTi_causale_emissione("C");
        fatturaAttivaDTO.setTi_bene_servizio("B");
        fatturaAttivaDTO.setTi_fattura("F");
        fatturaAttivaDTO.setDt_registrazione(LocalDateTime.now().plusHours(1));
        fatturaAttivaDTO.setFl_intra_ue(Boolean.TRUE);
        fatturaAttivaDTO.setFl_liquidazione_differita(Boolean.FALSE);
        fatturaAttivaDTO.setCd_terzo(34791);
        fatturaAttivaDTO.setCd_terzo_uo_cds(8271);
        fatturaAttivaDTO.setCd_modalita_pag_uo_cds("BO");
        fatturaAttivaDTO.setPg_banca_uo_cds(Long.valueOf(2));
        fatturaAttivaDTO.setCd_tipo_sezionale("v/com");

        FatturaAttivaRigaDTO fatturaAttivaRigaDTO = new FatturaAttivaRigaDTO();
        fatturaAttivaRigaDTO.setDs_riga_fattura("TEST REST");
        fatturaAttivaRigaDTO.setQuantita(BigDecimal.ONE);
        fatturaAttivaRigaDTO.setCd_bene_servizio("20");
        fatturaAttivaRigaDTO.setCd_voce_iva("22%");
        fatturaAttivaRigaDTO.setCd_voce("31013");
        fatturaAttivaRigaDTO.setDs_accertamento("TEST REST");

        FatturaAttivaScadDTO fatturaAttivaScadDTO = new FatturaAttivaScadDTO();
        fatturaAttivaScadDTO.setCdr(PropertiesService.getProp("sigla.cdr"));
        fatturaAttivaScadDTO.setGae("P0000003");
        fatturaAttivaScadDTO.setIm_voce(BigDecimal.ZERO);
        fatturaAttivaRigaDTO.setRighescadvoc(Arrays.asList(fatturaAttivaScadDTO));
        fatturaAttivaDTO.setRighefat(Arrays.asList(fatturaAttivaRigaDTO));

        final List<FatturaAttivaDTO> fatturaAttivaDTOList = siglaService.inserisciFatture(Arrays.asList(fatturaAttivaDTO));
        Assert.assertEquals(1, fatturaAttivaDTOList.size());
        final Long pgFatturaAttiva = fatturaAttivaDTOList.stream().map(FatturaAttivaDTO::getPg_fattura_attiva).findAny().orElse(null);
        final FatturaAttivaDTO fatturaByProgressivo = siglaService.getFatturaByProgressivo(2022, pgFatturaAttiva);
        Assert.assertEquals(Integer.valueOf(34791), fatturaByProgressivo.getCd_terzo());

    }

    @Test
    public void stampaFattura2022() throws IOException {
        SiglaService siglaService = new SiglaService();

        final Long pgStampa = siglaService.inserisciDatiPerStampa(2022, Long.valueOf(7));
        Assert.assertNotNull(pgStampa);

        final byte[] bytes = siglaService.stampaFattura(pgStampa);
        Assert.assertNotNull(bytes);

        try (FileOutputStream stream = new FileOutputStream("fattura-2022-7.pdf")) {
            stream.write(bytes);
        }

    }

    @Test
    public void stampaFattura2021() throws IOException {
        SiglaService siglaService = new SiglaService();

        final Long pgStampa = siglaService.inserisciDatiPerStampa(2021, Long.valueOf(21));
        Assert.assertNotNull(pgStampa);

        final byte[] bytes = siglaService.stampaFattura(pgStampa);
        Assert.assertNotNull(bytes);

        try (FileOutputStream stream = new FileOutputStream("fattura-2021-21.pdf")) {
            stream.write(bytes);
        }

    }

}
