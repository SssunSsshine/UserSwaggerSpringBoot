package com.vsu.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageRequestForUpdate {
    @NotBlank(message = "Text cannot be blank")
    @Size(max = 1000, message = "Out of range. Maximum text length = 1000 symbols")
    private String text;
}
