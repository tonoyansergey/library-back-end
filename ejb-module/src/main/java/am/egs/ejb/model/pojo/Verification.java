package am.egs.ejb.model.pojo;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Verification {

    private Long id;

    @NotNull
    private User user;

    @NotEmpty
    private String vCode;
}
