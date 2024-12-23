package com.example.delivery_project.menu.service;

import com.example.delivery_project.menu.dto.*;
import com.example.delivery_project.menu.entity.Menu;
import com.example.delivery_project.menu.entity.MenuDelete;
import com.example.delivery_project.menu.repository.MenuRepository;
import com.example.delivery_project.store.entity.Store;
import com.example.delivery_project.store.repository.StoreRepository;
import com.example.delivery_project.store.service.StoreService;
import com.example.delivery_project.user.entity.User;
import com.example.delivery_project.user.repository.UserRepository;
import com.example.delivery_project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    @Transactional
    public CreateMenuResponseDto save(Long storeId, Long userId, CreateMenuRequestDto requestDto) {
        Store findStore = storeRepository.findByIdOrElseThrow(storeId);
        User loginUser = userRepository.findByIdOrElseThrow(userId);

        //해당 가게 주인이 아닐 경우
        if (!findStore.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 가게 주인이 아닙니다.");
        }


        Menu menu = new Menu(requestDto.getName(), requestDto.getPrice(),findStore);
        Menu savedMenu = menuRepository.save(menu);
        return CreateMenuResponseDto.toDto(savedMenu);
    }
    public Page<ReadMenuResponseDto> getPostsPage(int page, Long storeId) {
        Store findStore = storeRepository.findByIdOrElseThrow(storeId);
        ReadMenuResponseDto findMenu = ReadMenuResponseDto.toDto(
            menuRepository.findMenuById(findStore.getId()));

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        return menuRepository.findAllByStoreIdAndMenuDelete(findMenu.getStoreId(),
            MenuDelete.ACTIVE, pageable).map(ReadMenuResponseDto::toDto);
    }

    @Transactional
    public CreateMenuResponseDto updateMenu(Long storeId, Long userId,
        UpdateMenuStatusRequestDto requestDto, Long menuId) {

        Store findStore = storeRepository.findByIdOrElseThrow(storeId);
        User loginUser = userRepository.findByIdOrElseThrow(userId);

        if (!findStore.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 가게 주인이 아닙니다.");
        }

        Menu findMenu = menuRepository.findByIdOrElseThrow(menuId);

        findMenu.updateMenu(requestDto.getName(), requestDto.getPrice(),
            requestDto.getMenuDelete());

        return CreateMenuResponseDto.toDto(findMenu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long userId, Long menuId) {
        Store findStore = storeRepository.findByIdOrElseThrow(storeId);
        User loginUser = userRepository.findByIdOrElseThrow(userId);

        if (!findStore.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 가게 주인이 아닙니다.");
        }

        Menu findMenu = menuRepository.findByIdOrElseThrow(menuId);

        findMenu.setMenu();
        menuRepository.save(findMenu);
    }

}
