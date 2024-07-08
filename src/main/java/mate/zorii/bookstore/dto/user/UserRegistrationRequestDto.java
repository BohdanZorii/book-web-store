package mate.zorii.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(
        @Email(message = "Invalid email format")
        @NotBlank
        @Size(max = 255, message = "Email must be less than 256 characters")
        String email,

        @NotBlank
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
        String password,

        @NotBlank
        @Size(min = 8, max = 50,
                message = "Repeat password must be between 8 and 50 characters long")
        String repeatPassword,

        @NotBlank
        @Size(max = 100, message = "First name must be less than 101 characters")
        String firstName,

        @NotBlank
        @Size(max = 100, message = "Last name must be less than 101 characters")
        String lastName,

        @Size(max = 255, message = "Shipping address must be less than 256 characters")
        String shippingAddress
) {
}
