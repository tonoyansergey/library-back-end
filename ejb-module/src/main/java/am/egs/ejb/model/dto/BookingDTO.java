package am.egs.ejb.model.dto;

import am.egs.ejb.model.pojo.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDTO {

    private Long id;

    @NotNull
    private Book book;

    @NotEmpty
    private String receiptCode;

    @NotNull
    private String expireDate;
}
