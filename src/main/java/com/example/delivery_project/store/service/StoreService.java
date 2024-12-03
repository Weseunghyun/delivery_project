package com.example.delivery_project.store.service;

import com.example.delivery_project.store.dto.StoreRequestDto;
import com.example.delivery_project.store.dto.StoreResponseDto;
import com.example.delivery_project.store.dto.UpdateStoreStatusResponseDto;
import com.example.delivery_project.store.entity.Store;
import com.example.delivery_project.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

        // 가게 이름이 이미 존재하는 경우
        Store findName = storeRepository.findByName(storeRequestDto.getName());
        if (findName != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "가게 이름이 이미 존재합니다.");
        }

        Store store = new Store(storeRequestDto.getName(), storeRequestDto.getOpenTime(), storeRequestDto.getCloseTime(), storeRequestDto.getMinimumOrderPrice());
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    public UpdateStoreStatusResponseDto updateStoreStatus(Long id) {

        Store findStore = storeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "해당하는 가게가 존재하지 않습니다."));

        // 가게가 존재하면 폐업 상태로 변환 후 DB 저장
        findStore.closeStore();
        Store savedStore = storeRepository.save(findStore);

        return new UpdateStoreStatusResponseDto(savedStore.getId(),savedStore.getName(), savedStore.getStoreStatus());
    }
}
