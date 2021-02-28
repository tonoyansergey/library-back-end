package am.egs.ejb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserLoginDTO {

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;
}
