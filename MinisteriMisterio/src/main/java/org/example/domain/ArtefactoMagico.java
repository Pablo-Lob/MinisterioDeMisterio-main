package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtefactoMagico {
    private Long id;
    private String nombre;
    private String nivelPeligrosidad;
    private boolean estaMaldito;
    private String estadoProcesamiento;
}