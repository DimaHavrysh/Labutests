package com.example.myproject.service;

import com.example.controller.ItemRestController;
import com.example.model.Item;
import com.example.repository.ItemRepository;
import com.example.request.ItemCreateRequest;
import com.example.request.ItemUpdateRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item( "Freddie Mercury", "Queen","vocal, piano"));
        items.add(new Item("2", "Paul McCartney", "Beatles","guitar"));
        items.add(new Item("3", "Mick Jagger", "Rolling Stones","vocal"));
    }

    @PostConstruct
    void init() {
        itemRepository.deleteAll();
        itemRepository.saveAll(items);

    }
    //  CRUD   - create read update delete

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item create(Item item) {

        return itemRepository.save(item);
    }

    public Item create(ItemCreateRequest request) {
        Item item = mapToItem(request);
        item.setCreateDate(LocalDateTime.now());
        item.setUpdateDate(new ArrayList<LocalDateTime>());
        return itemRepository.save(item);
    }

    public  Item update(Item item) {
        return itemRepository.save(item);
    }


    public void delById(String id) {
        itemRepository.deleteById(id);
    }

    private Item mapToItem(ItemCreateRequest request) {
        Item item = new Item(request.name(), request.code(), request.description());
        return item;
    }

    public Item update(ItemUpdateRequest request) {
        Item itemPersisted = itemRepository.findById(request.id()).orElse(null);
        if (itemPersisted != null) {
            List<LocalDateTime> updateDates = itemPersisted.getUpdateDate();
            updateDates.add(LocalDateTime.now());
            Item itemToUpdate =
                    Item.builder()
                            .id(request.id())
                            .name(request.name())
                            .code(request.code())
                            .description(request.description())
                            .createDate(itemPersisted.getCreateDate())
                            .updateDate(updateDates)
                            .build();
            return itemRepository.save(itemToUpdate);

        }
        return null;
    }

}