package pl.hszaba.betacomtask.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hszaba.betacomtask.common.security.Operator;
import pl.hszaba.betacomtask.item.ItemCreateRequest;
import pl.hszaba.betacomtask.item.ItemModel;
import pl.hszaba.betacomtask.user.domain.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    @Transactional
    @Override
    public void createItem(ItemCreateRequest itemCreateRequest, Operator operator) {
        var currentUser = userService.findUserByLogin(operator.login());
        var item = ItemEntity.create(itemCreateRequest.name(), currentUser);

        itemRepository.save(item);
        log.info("Successfully saved item {} for user {}", item.getName(), operator.login());
    }

    @Override
    public List<ItemModel> getCurrentUserItems(Operator operator) {
        var currentUser = userService.findUserByLogin(operator.login());
        return itemRepository.findAllByOwner(currentUser)
                .stream()
                .map(ItemMapper::toModel)
                .toList();
    }
}
