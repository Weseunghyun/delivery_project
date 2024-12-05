package com.example.delivery_project.menu.controller;

import com.example.delivery_project.menu.dto.*;
import com.example.delivery_project.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners/stores/")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    //메뉴 조회
    @GetMapping("/{storeId}/menus")
    public Page<ReadMenuResponseDto> findByAll(
            @PathVariable(name = "storeId") Long storeId,
            @RequestParam(required = false, defaultValue = "0", value = "page") int page) {

        return menuService.getPostsPage(page,storeId);
    }

    //메뉴 생성
    @PostMapping("/{storeId}/menus")
    public ResponseEntity<CreateMenuResponseDto> save(
            @PathVariable Long storeId,
            @RequestBody CreateMenuRequestDto requestDto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long userId = (Long) session.getAttribute("userId");

        CreateMenuResponseDto savedMenu = menuService.save(storeId,userId,requestDto);

        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);

    }
    //메뉴 수정
    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<CreateMenuResponseDto> update(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody UpdateMenuStatusRequestDto requestDto,
            HttpServletRequest request){

        HttpSession session = request.getSession(false);

        Long userId = (Long) session.getAttribute("userId");

        CreateMenuResponseDto updateMenu = menuService.updateMenu(storeId,userId,requestDto,menuId);


        return new ResponseEntity<>(updateMenu, HttpStatus.CREATED);

    }
    //메뉴 삭제
    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        menuService.deleteMenu(storeId,userId,menuId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
