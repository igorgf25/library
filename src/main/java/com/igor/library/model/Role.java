package com.igor.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_roles")
public class Role implements GrantedAuthority, Serializable {

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name")
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
