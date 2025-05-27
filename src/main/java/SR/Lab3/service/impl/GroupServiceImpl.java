package SR.Lab3.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SR.Lab3.entity.Group;
import SR.Lab3.repository.GroupRepository;
import SR.Lab3.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;

    @Override
    public Group read(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Group> read() {
        return repository.findAll();
    }

    @Override
    public void save(Group entity) {
        if (repository.existsByGrName(entity.getGrName())) {
            throw new IllegalArgumentException("Group with name '" + entity.getGrName() + "' already exists");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Group readByName(String name) {
        return repository.findByGrName(name);
    }

    @Override
    public void edit(Group entity) {
        Group group = repository.findById(entity.getId()).orElseThrow(IllegalArgumentException::new);
        group.setGrName(entity.getGrName());
        repository.save(group);
    }
}

