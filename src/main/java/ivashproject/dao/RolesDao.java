package ivashproject.dao;

import ivashproject.model.Role;

public interface RolesDao {
    public Role getAdminRole();
    public Role getUserRole();

}
