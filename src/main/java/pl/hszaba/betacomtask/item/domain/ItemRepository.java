package pl.hszaba.betacomtask.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hszaba.betacomtask.user.domain.UserEntity;

import java.util.List;

@Repository
interface ItemRepository extends JpaRepository<ItemEntity, String> {
    List<ItemEntity> findAllByOwner(UserEntity owner);
}
