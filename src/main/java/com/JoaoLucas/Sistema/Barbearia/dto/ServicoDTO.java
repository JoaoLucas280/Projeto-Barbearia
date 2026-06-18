package com.JoaoLucas.Sistema.Barbearia.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoDTO {


    private Long id;

    @NotBlank(message = "O nome do serviço é obrigatório")
    @Size(max = 50)
    private String nome;

    @NotNull(message = "O valor do serviço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor do serviço deve ser maior que zero")
    private BigDecimal valor;

    @NotBlank(message = "A descrição do serviço é obrigatória")
    private String descricao;
    @Min(value = 1, message = "A duração do serviço deve ser no mínimo 1 minuto")
    @NotNull(message = "O valor não pode ser nulo")
    private Integer duracao;


}
