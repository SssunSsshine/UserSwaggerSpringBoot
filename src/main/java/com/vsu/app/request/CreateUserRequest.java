package com.vsu.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Surname cannot be blank")
    @Pattern(regexp = "^([а-яА-ЯёЁ]+|[a-zA-Z]){1,50}", message = "Surname has wrong symbols")
    private String surname;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^([а-яА-ЯёЁ]+|[a-zA-Z]){1,50}", message = "Name has wrong symbols")
    private String name;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull(message = "Date cannot be empty")
    @Past(message = "Date is not valid")
    private Date birthday;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Bad format for email")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    /*@Min(value = 11, message = "Phone should contain 11 digits")
    @Digits(message="Phone should contain 11 digits.", fraction = 0, integer = 11)*/
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Phone format is invalid")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
