package vn.robert.rbac.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.robert.rbac.entity.Role;
import vn.robert.rbac.repository.RoleRepository;
import vn.robert.rbac.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll(Long userId) {
        return roleRepository.getAllByUserId(userId);
    }
}
