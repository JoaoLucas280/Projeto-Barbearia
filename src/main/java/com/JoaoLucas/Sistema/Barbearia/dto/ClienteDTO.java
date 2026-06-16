package com.JoaoLucas.Sistema.Barbearia.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {


    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres")
    private String nome;
    @NotBlank(message = "O email do cliente é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;
    @NotBlank(message = "O telefone do cliente é obrigatório")
    @Size(max = 30, message = "O telefone do cliente deve ter no máximo 30 caracteres")
    private String telefone;


}
