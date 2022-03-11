package it.cnr.si.dto.docamm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class FatturaAttivaDTO {

    private Integer esercizio;

    private List<FatturaAttivaRigaDTO> righefat;

    private List<FatturaAttivaIntraDTO> righeIntra;

    private String numeroFattura;

    private String cod_errore;
    private String desc_errore;
    private String ti_causale_emissione;
    private String ti_fattura;
    private String utcr;
    private String cd_cds_origine;
    private String cd_uo_origine;

    private Long pg_fattura_esterno;
    private String cd_tipo_sezionale;
    private LocalDateTime dt_registrazione;
    // NOTE VARCHAR(300)
    private java.lang.String note;
    private Long pg_fattura_attiva;
    private java.math.BigDecimal cambio;

    // CD_DIVISA VARCHAR(10) NOT NULL
    private java.lang.String cd_divisa;

    // CD_MODALITA_PAG VARCHAR(10)
    private java.lang.String cd_modalita_pag;

    // CD_MODALITA_PAG_UO_CDS VARCHAR(10) NOT NULL
    private java.lang.String cd_modalita_pag_uo_cds;

    // CD_TERZO DECIMAL(8,0) NOT NULL
    private java.lang.Integer cd_terzo;

    // CD_TERZO_UO_CDS DECIMAL(8,0) NOT NULL
    private java.lang.Integer cd_terzo_uo_cds;

    // DS_FATTURA_ATTIVA VARCHAR(200)
    private java.lang.String ds_fattura_attiva;

    // FL_EXTRA_UE CHAR(1) NOT NULL
    private java.lang.Boolean fl_extra_ue;

    // FL_INTRA_UE CHAR(1) NOT NULL
    private java.lang.Boolean fl_intra_ue;

    // FL_LIQUIDAZIONE_DIFFERITA CHAR(1) NOT NULL
    private java.lang.Boolean fl_liquidazione_differita;

    // FL_SAN_MARINO CHAR(1) NOT NULL
    private java.lang.Boolean fl_san_marino;

    // PG_BANCA DECIMAL(10,0)
    private java.lang.Long pg_banca;

    // PG_BANCA_UO_CDS DECIMAL(10,0) NOT NULL
    private java.lang.Long pg_banca_uo_cds;

    private String rif_ordine;

    private java.math.BigDecimal Im_totale_imponibile;

    private java.math.BigDecimal Im_totale_iva;

    private LocalDateTime dt_protocollo;
    private java.lang.Long nr_protocollo_iva;
    private String ti_bene_servizio;
    private String fl_pagamento_anticipato;

}
