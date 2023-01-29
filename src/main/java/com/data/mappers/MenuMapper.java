package com.data.mappers;

import com.data.dtos.MenuDTO;
import com.data.entities.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuDTO toMenuDTO(Menu menu);

    List<MenuDTO> toMenuDTOs(List<Menu> menus);
}
