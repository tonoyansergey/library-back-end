package am.egs.ejb.model.entity;

import am.egs.ejb.model.BaseEntity;
import lombok.*;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Builder
@Table(name = "booking_table")
public class BookingEntity extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Column(name = "receipt_code", nullable = false)
    private String receiptCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_date", nullable = false)
    private Date expireDate;
}
