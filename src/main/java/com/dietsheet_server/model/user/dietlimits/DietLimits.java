package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "diet_limits")
public class DietLimits {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "min_limits_id",
            referencedColumnName = "id",
            unique = true
    )
    private Summary minLimits;

    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "max_limits_id",
            referencedColumnName = "id",
            unique = true
    )
    private Summary maxLimits;
}
