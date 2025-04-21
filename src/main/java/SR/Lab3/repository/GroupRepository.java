package SR.Lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SR.Lab3.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGrName(String name);
    boolean existsByGrName(String grName);
}
