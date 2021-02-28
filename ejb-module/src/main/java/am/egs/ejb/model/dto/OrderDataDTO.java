package am.egs.ejb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDataDTO {

    @NotNull
    private Long userID;

    @NotNull
    private Long bookID;

    @NotEmpty
    private String verCode;
}
