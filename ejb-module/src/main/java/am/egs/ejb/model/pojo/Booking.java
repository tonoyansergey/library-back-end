package am.egs.ejb.model.pojo;

import am.egs.ejb.model.dto.UserDTO;
import lombok.*;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Booking {

    private Long id;

    @NotNull
    private UserDTO user;

    @NotNull
    private Book book;

    @NotEmpty
    private String receiptCode;

    @NotNull
    private Date expireDate;
}
