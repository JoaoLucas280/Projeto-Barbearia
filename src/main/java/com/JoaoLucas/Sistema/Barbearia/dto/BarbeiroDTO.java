package com.JoaoLucas.Sistema.Barbearia.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BarbeiroDTO {


    private Long id;

    @NotBlank(message = "O nome do barbeiro é obrigatório")
    @Size(max = 100, message = "O nome do barbeiro deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 150, message = "O máximo de caracteres é 150")
    private String especialidade;

    @Size(max = 200, message = "O máximo de caracteres para a descrição é 200")
    private String descricao;

    @NotNull(message = "O horário inicial é obrigatório")
    private LocalTime horarioInicio;
    @NotNull(message = "O horário final é obrigatório")
    private LocalTime horarioFim;
    @NotEmpty(message = "Os dias da semana são obrigatórios")
    private Set<DayOfWeek> diasTrabalho = new HashSet<>();


}
