package com.example.easypay.modals.entities.admins;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "admin_role")
public class AdminRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<Admin> admins=new HashSet<>();


    public void addAdmin(Admin admin)
    {
        this.admins.add(admin);
        admin.getRoles().add(this);
    }

    public void removeAdmin(Admin admin)
    {
        this.admins.remove(admin);
        admin.getRoles().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminRole that)) return false;
        return Objects.equals(getRoleId(), that.getRoleId()) && Objects.equals(getRoleName(), that.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(), getRoleName());
    }
}