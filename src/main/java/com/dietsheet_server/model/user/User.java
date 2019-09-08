package com.dietsheet_server.model.user;

import com.dietsheet_server.model.user.dietlimits.DietLimits;
import com.dietsheet_server.model.user.dietlimits.DietLimitsCalculationStrategy;
import com.dietsheet_server.model.user.dietlimits.DietLimitsCalculationStrategyEnum;
import com.dietsheet_server.model.user.dietlimits.DietLimitsCalculationStrategyFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "[user]")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_data_id",
            referencedColumnName = "id",
            unique = true
    )
    private UserData userData;

    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "diet_limits_id",
            referencedColumnName = "id",
            unique = true
    )
    private DietLimits dietLimits;

    @Column(name = "strategy_enum")
    @Enumerated(EnumType.STRING)
    private DietLimitsCalculationStrategyEnum strategyEnum;

    public void calculateDietLimits() {
        if(strategyEnum != DietLimitsCalculationStrategyEnum.MANUAL) {
            DietLimitsCalculationStrategy dietLimitsCalculationStrategy =
                    DietLimitsCalculationStrategyFactory.getDietLimitsCalculationStrategy(strategyEnum);
            dietLimits = dietLimitsCalculationStrategy.calculateLimits(userData);
        }
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
