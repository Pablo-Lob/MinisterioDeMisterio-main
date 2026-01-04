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
    public ArtefactoMagico process(@NonNull ArtefactoMagico item) throws Exception { // Añade throws
        log.debug("Analizando artefacto: {}", item.getNombre());

        // Simulación de un error crítico para probar la tolerancia a fallos
        if ("ERROR_TEST".equals(item.getNombre())) {
            log.error("Simulando fallo crítico en artefacto: {}", item.getNombre());
            throw new RuntimeException("Fallo simulado para probar FaultTolerant");
        }

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