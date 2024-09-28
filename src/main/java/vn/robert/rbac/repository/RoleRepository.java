package vn.robert.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.robert.rbac.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r join UserRole ur on r.id = ur.role.id " +
            "WHERE ur.user.id = :id")
    List<Role> getAllByUserId(@Param("id") Long userId);

}
