package com.example.easypay.modals.entities.admins;

import com.example.easypay.modals.entities.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false)
    private Long id;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<Category> categories=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admin_roles", joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "admin_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<AdminRole> roles=new HashSet<>();

    @Column(name = "admin_token", nullable = false, unique = true)
    private String adminToken;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void generateToken() {
        if (this.adminToken == null) {
            this.adminToken = UUID.randomUUID().toString();
        }
    }

    public void addCategory(Category category)
    {

        this.categories.add(category);
        category.setCreatedBy(this);
    }

    private void removeRole(AdminRole role)
    {
        this.roles.remove(role);
        role.getAdmins().remove(this);
    }

    public void addRole(AdminRole role)
    {

        this.roles.add(role);
        role.getAdmins().add(this);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin admin)) return false;
        return Objects.equals(getId(), admin.getId()) && Objects.equals(getEmail(), admin.getEmail()) && Objects.equals(getPassword(), admin.getPassword()) && Objects.equals(getAdminToken(), admin.getAdminToken()) && Objects.equals(getCreatedAt(), admin.getCreatedAt()) && Objects.equals(getUpdatedAt(), admin.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getAdminToken(), getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", categories=" + categories +
                ", roles=" + roles +
                ", adminToken='" + adminToken + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}