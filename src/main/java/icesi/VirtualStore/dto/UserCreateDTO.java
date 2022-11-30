package icesi.VirtualStore.dto;

import icesi.VirtualStore.validation.CustomAnnotations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private UUID id;

    @NotBlank
    private String email;

    @NotNull
    private String phoneNumber;

    @Size(min = 1, max = 120)
    @CustomAnnotations.NameValidation
    private String firstName;

    @NotNull
    @Size(min = 1, max = 120)
    private String lastName;

    @NotNull
    private String password;

    private String roleName;

}
