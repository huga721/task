package pl.hszaba.betacomtask.item.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hszaba.betacomtask.common.security.Operator;
import pl.hszaba.betacomtask.item.ItemCreateRequest;
import pl.hszaba.betacomtask.item.ItemModel;
import pl.hszaba.betacomtask.user.domain.UserEntity;
import pl.hszaba.betacomtask.user.domain.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserService userService;
    @Mock
    private ItemServiceImpl itemService;

    @BeforeEach
    void setup() {
        itemService = new ItemServiceImpl(itemRepository, userService);
    }

    @Test
    void shouldCreateItem() {
        // given
        var operator = new Operator("test-user");
        var user = UserEntity.create( "test-user", "password");
        var request = new ItemCreateRequest("test-item");

        // when
        when(userService.findUserByLogin("test-user")).thenReturn(user);

        itemService.createItem(request, operator);

        // then
        verify(userService).findUserByLogin("test-user");
        verify(itemRepository).save(argThat(item ->
                item.getName().equals("test-item") &&
                        item.getOwner().equals(user)
        ));
    }

    @Test
    void shouldReturnCurrentUserItems() {
        // given
        var operator = new Operator("test-user");
        var user = UserEntity.create( "test-user", "pass");
        var itemEntity = ItemEntity.create("item-1", user);
        var itemModel = new ItemModel("item-1", "item name");

        when(userService.findUserByLogin("test-user")).thenReturn(user);
        when(itemRepository.findAllByOwner(user)).thenReturn(List.of(itemEntity));

        try (MockedStatic<ItemMapper> mockedMapper = Mockito.mockStatic(ItemMapper.class)) {
            mockedMapper.when(() -> ItemMapper.toModel(itemEntity)).thenReturn(itemModel);

            // when
            var result = itemService.getCurrentUserItems(operator);

            // then
            assertThat(result).containsExactly(itemModel);
            verify(userService).findUserByLogin("test-user");
            verify(itemRepository).findAllByOwner(user);
        }
    }
}
