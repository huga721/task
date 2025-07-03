package pl.hszaba.betacomtask.item.domain;

import pl.hszaba.betacomtask.item.ItemModel;

class ItemMapper {
    public static ItemModel toModel(ItemEntity entity) {
        return new ItemModel(entity.getId(), entity.getName());
    }
}
