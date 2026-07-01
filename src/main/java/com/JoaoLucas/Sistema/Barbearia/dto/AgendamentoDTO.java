package com.JoaoLucas.Sistema.Barbearia.dto;


import com.JoaoLucas.Sistema.Barbearia.entity.enums.Status;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {

    private Long id;


    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres")
    private String clienteNome;


    @NotBlank(message = "O email do cliente é obrigatório")
    @Size(max = 100, message = "O email do cliente deve ter no máximo 100 caracteres")
    @Email(message = "O email deve ser válido")
    private String clienteEmail;


    @NotBlank(message = "O telefone do cliente é obrigatório")
    @Size(max = 20, message = "O telefone do cliente deve ter no máximo 20 caracteres")
    private String clienteTelefone;


    @NotNull(message = "O serviço é obrigatório")
    private Long servicoId;

    @FutureOrPresent(message = "A data do agendamento não pode ser no passado")
    @NotNull(message = "A data do agendamento é obrigatória")
    private LocalDate data;

    @NotNull(message = "O horário de início do agendamento é obrigatório")
    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    private Status status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AgendamentoDTO that = (AgendamentoDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(getClienteNome(), that.getClienteNome()) && Objects.equals(getClienteEmail(), that.getClienteEmail()) && Objects.equals(getClienteTelefone(), that.getClienteTelefone()) && Objects.equals(getServicoId(), that.getServicoId()) && Objects.equals(getData(), that.getData()) && Objects.equals(getHorarioInicio(), that.getHorarioInicio()) && Objects.equals(getHorarioFim(), that.getHorarioFim()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getClienteNome(), getClienteEmail(), getClienteTelefone(), getServicoId(), getData(), getHorarioInicio(), getHorarioFim(), getStatus());
    }
}
