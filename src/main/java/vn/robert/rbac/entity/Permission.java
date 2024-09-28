package vn.robert.rbac.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_permission")
public class Permission extends AbstractEntity<Integer>{

    @Column(name = "name")
    private String name;

//    private String description;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> roleHasPermissions = new HashSet<>();

}
