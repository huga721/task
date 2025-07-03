package pl.hszaba.betacomtask.item.domain;

import org.springframework.security.core.Authentication;
import pl.hszaba.betacomtask.common.security.Operator;
import pl.hszaba.betacomtask.item.ItemCreateRequest;
import pl.hszaba.betacomtask.item.ItemModel;

import java.util.List;

public interface ItemService {
    void createItem(ItemCreateRequest itemCreateRequest, Operator operator);
    List<ItemModel> getCurrentUserItems(Operator operator);
}
