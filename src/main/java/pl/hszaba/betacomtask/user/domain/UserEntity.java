package pl.hszaba.betacomtask.user.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.hszaba.betacomtask.item.domain.ItemEntity;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<ItemEntity> items;

    public static UserEntity create(String username, String password) {
        return new UserEntity(
                null,
                username,
                password,
                Set.of());
    }
}
