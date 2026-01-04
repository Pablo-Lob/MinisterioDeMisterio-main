package org.example.processor;

import org.example.domain.ArtefactoMagico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ArtefactoProcessor implements ItemProcessor<ArtefactoMagico, ArtefactoMagico> {

    private static final Logger log = LoggerFactory.getLogger(ArtefactoProcessor.class);

    @Override
    public ArtefactoMagico process(@NonNull ArtefactoMagico item) {
        // Log eficiente del evento
        log.debug("Analizando artefacto: {}", item.getNombre());

        if ("Alto".equalsIgnoreCase(item.getNivelPeligrosidad())) {
            item.setEstadoProcesamiento("EN_CUARENTENA");
            log.warn("⚠ ALERTA: Artefacto peligroso detectado [{}] -> Enviado a CUARENTENA", item.getNombre());
        } else {
            item.setEstadoProcesamiento("PROCESADO_SEGURO");
            log.info(" Artefacto seguro verificado [{}]", item.getNombre());
        }
        return item;
    }
}