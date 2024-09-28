package vn.robert.rbac.service;

import vn.robert.rbac.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll(Long userId);

}
