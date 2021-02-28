package am.egs.ejb.model.pojo;

import am.egs.ejb.model.dto.UserDTO;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Loan {

    private Long id;

    @NotNull
    private UserDTO user;

    @NotNull
    private Book book;

    @NotNull
    private Date loanDate;

    @NotNull
    private Date expireDate;
}
