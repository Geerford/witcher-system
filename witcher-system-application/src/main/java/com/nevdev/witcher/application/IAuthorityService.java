package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.enums.Role;

public interface IAuthorityService extends IService<Authority> {
    Authority find(Role roleName);
}
