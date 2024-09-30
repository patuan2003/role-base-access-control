package vn.robert.rbac.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_group")
public class Group extends AbstractEntity<Integer>{

    private String name;

    @OneToMany(mappedBy = "group")
    private Set<UserGroup> userGroups;

}
