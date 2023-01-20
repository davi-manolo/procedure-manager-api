package com.procedure.manager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "procedure_type")
public class ProcedureTypeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long procedureTypeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @Column(name = "disabled", nullable = false, columnDefinition = "boolean default false")
    private Boolean disabled;

}
