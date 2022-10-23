package com.procedure.manager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "procedure")
public class ProcedureModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long procedureId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(name = "procedure_date", nullable = false)
    private LocalDate procedureDate;

    @Column(name = "customer", nullable = false)
    private String customer;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "procedure_type_id", nullable = false)
    private ProcedureTypeModel procedureType;

    @Column(name = "value_received", nullable = false)
    private BigDecimal valueReceived;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "disabled", nullable = false, columnDefinition = "boolean default false")
    private Boolean disabled;

}
