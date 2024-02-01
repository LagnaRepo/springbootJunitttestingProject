package com.yash.springdatajparestful.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "User DTO class"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @Schema(
            description = "User FirstName"
    )
    @NotEmpty(message = "User FirstName cannot be null")
    private String firstName;
    @Schema(
            description = "User LastName"
    )
    @NotEmpty(message = "User LastName cannot be null")
    private String lastName;
    @Schema(
            description = "User Email Address"
    )
    @NotEmpty(message = "User Email cannot be null")
    @Email(message = "User Email should be well Formed")
    private String email;
}
