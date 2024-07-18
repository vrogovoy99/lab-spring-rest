package com.cydeo.dto;

import com.cydeo.enums.EducationLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeacherDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "First Name is a required field")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters long")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last Name is a required field")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters long")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name must contain only letters")
    private String lastName;

    @NotBlank(message = "Phone Number is a required field")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone Number must be between 10 and 15 digits and can include an optional leading +")
    private String phoneNumber;

    @NotBlank(message = "Email is a required field")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username is a required field")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is a required field")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "The password must be at least 8 characters long and include at least 1 uppercase letter, 1 lowercase letter, and 1 digit")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Birthday is a required field")
    @Past(message = "Birthday must be a past date")
    private LocalDate birthday;

    @NotNull(message = "Education Level is a required field")
    private EducationLevel educationLevel;

    @NotBlank(message = "Address No is a required field")
    private String addressNo;
}
