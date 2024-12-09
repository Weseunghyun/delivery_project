package com.example.delivery_project.review.controller;

import com.example.delivery_project.common.constant.UserSession;
import com.example.delivery_project.review.dto.ReadReviewResponseDto;
import com.example.delivery_project.review.dto.ReviewRequestDto;
import com.example.delivery_project.review.dto.ReviewResponseDto;
import com.example.delivery_project.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{orderId}/review")
    public ResponseEntity<ReviewResponseDto> createReview(
        @PathVariable Long orderId,
        @RequestBody ReviewRequestDto reviewRequestDto,
        @SessionAttribute(UserSession.USER_ID) Long userId
    ) {

        ReviewResponseDto createdReview = reviewService.createReview(
            orderId,
            userId,
            reviewRequestDto
        );

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{storeId}/review")
    public Page<ReadReviewResponseDto> findByAll(
        @PathVariable(name = "storeId") Long storeId,
        @RequestParam(required = false, defaultValue = "0", value = "page") int page,
        @SessionAttribute(UserSession.USER_ID) Long userId
    ) {

        return reviewService.getPostsPage(page, storeId, userId);
    }

    @GetMapping("/{storeId}/review/point")
    public Page<ReadReviewResponseDto> findByAllStarPoint(
        @PathVariable(name = "storeId") Long storeId,
        @RequestParam(required = false, name = "minStar") Long minStar,
        @RequestParam(required = false, name = "maxStar") Long maxStart,
        @RequestParam(required = false, defaultValue = "0", value = "page") int page,
        @SessionAttribute(UserSession.USER_ID) Long userId
    ) {

        return reviewService.getStarPointPage(page, storeId, userId, minStar, maxStart);
    }
}
