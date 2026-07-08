package com.JoaoLucas.Sistema.Barbearia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {


    private Long id;
    @NotBlank
    @Size(min = 1, max = 70)
    private String username;
    @NotBlank
    @Size(min = 6, message = "A senha deve ter no minímo 6 caracteres")
    private String senha;
}
