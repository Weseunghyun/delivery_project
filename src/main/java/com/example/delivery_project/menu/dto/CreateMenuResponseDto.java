package com.example.delivery_project.menu.dto;

import com.example.delivery_project.menu.entity.Menu;
import com.example.delivery_project.menu.entity.MenuDelete;
import lombok.Getter;

@Getter
public class CreateMenuResponseDto {
    private Long Id;
    private Long storeId;

    private String name;
    private int price;
    private MenuDelete menuDelete;


    public CreateMenuResponseDto(Long Id, Long storeId, String name, int price,
        MenuDelete menuDelete) {
        this.Id = Id;
        this.storeId = storeId;

        this.name = name;
        this.price = price;
        this.menuDelete = menuDelete;
    }

    public static CreateMenuResponseDto toDto(Menu menu) {
        return new CreateMenuResponseDto(
                menu.getId(),
                menu.getStore().getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getMenuDelete()
        );
    }

}
