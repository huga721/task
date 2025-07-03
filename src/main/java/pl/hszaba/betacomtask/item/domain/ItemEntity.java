package pl.hszaba.betacomtask.item.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.hszaba.betacomtask.user.domain.UserEntity;

@Entity
@Table(name = "item")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserEntity owner;

    public static ItemEntity create(String title, UserEntity owner) {
        return new ItemEntity(null, title, owner);
    }
}
