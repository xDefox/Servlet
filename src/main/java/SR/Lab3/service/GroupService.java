package SR.Lab3.service;

import SR.Lab3.entity.Group;


public interface GroupService extends Service<Group> {

    Group readByName(String name);

}
