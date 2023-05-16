package org.gfg.expenseTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "expenseTypes") // hibernate
public class ExpenseTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String expenseType;

    @CreationTimestamp
    private Date createdAt;

    private String createdBy;

    @OneToMany(mappedBy = "expenseTypes")
    @JsonIgnore
    private List<TxnDetails> txnDetailsList;





}
