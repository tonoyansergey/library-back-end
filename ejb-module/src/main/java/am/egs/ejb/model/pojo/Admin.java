package am.egs.ejb.model.pojo;

import am.egs.ejb.model.AdminRole;
import lombok.*;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Admin {

    private Long id;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    private AdminRole role;
}
