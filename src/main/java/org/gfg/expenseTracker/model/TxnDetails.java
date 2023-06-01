package org.gfg.expenseTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "txnDetails")
public class TxnDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private Double expenditureAmount;

    @ManyToOne
    @JsonIgnore
    private User createdBy;

    @ManyToOne
    @JoinColumn
    private ExpenseTypes expenseTypes;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date expenseDate;

    private String expenseNote;


    // txn  expense type


}
