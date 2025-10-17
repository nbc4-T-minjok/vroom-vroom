package com.sparta.vroomvroom.domain.menu.service;

import com.sparta.vroomvroom.domain.ai.model.entity.AiApiLog;
import com.sparta.vroomvroom.domain.ai.repository.GeminiRepository;
import com.sparta.vroomvroom.domain.ai.service.GeminiService;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuUpdateRequestDto;
import com.sparta.vroomvroom.domain.menu.model.dto.response.MenuListResponseDto;
import com.sparta.vroomvroom.domain.menu.model.dto.response.MenuResponseDto;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import com.sparta.vroomvroom.global.conmon.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CompanyRepository companyRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void createMenu(UUID companyId, MenuRequestDto requestDto, List<MultipartFile> images) {
        Company company = findCompany(companyId);

        List<String> imageUrls = new ArrayList<>();
        try {
            imageUrls = uploadImages(requestDto.getImages());
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드 실패", e);
        }

        Menu menu = new Menu(
                company,
                requestDto.getMenuName(),
                requestDto.getMenuGroup(),
                requestDto.getMenuPrice(),
                String.join(",", imageUrls),
                requestDto.getMenuDescription(),
                requestDto.getMenuStatus(),
                requestDto.getIsVisible()
        );

        menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(UUID menuId) {
        Menu menu = findMenu(menuId);

        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public MenuListResponseDto getMenus(UUID companyId, boolean includeHidden) {
        List<Menu> menus = includeHidden
                ? menuRepository.findAllByCompany_CompanyIdAndIsDeletedFalse(companyId)
                : menuRepository.findAllByCompany_CompanyIdAndIsDeletedFalseAndIsVisibleTrue(companyId);

        List<MenuResponseDto> items = menus.stream()
                .map(MenuResponseDto::new)
                .toList();

        return new MenuListResponseDto(companyId, items);
    }

    @Transactional
    public MenuResponseDto updateMenu(UUID menuId, MenuUpdateRequestDto requestDto, List<MultipartFile> images) {
        Menu menu = findMenu(menuId);

        try {
            if (images != null && !images.isEmpty()) {
                deleteOldImages(menu.getImageList());
                List<String> newUrls = uploadImages(images);
                menu.setImageList(newUrls);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드 실패", e);
        }

        menu.updateMenu(requestDto);
        return new MenuResponseDto(menu);
    }

    @Transactional
    public void deleteMenu(UUID menuId, String deletedBy) {
        Menu menu = findMenu(menuId);

        if (menu.getMenuImage() != null) {
            try {
                s3Uploader.delete(menu.getMenuImage());
            } catch (Exception e) {

                log.warn("S3 이미지 삭제 실패 (menuId: {}, image: {}): {}", menuId, menu.getMenuImage(), e.getMessage());
            }
        }

        menu.softDelete(LocalDateTime.now(),
                deletedBy != null ? deletedBy : "SYSTEM");
    }

    //예외처리
    private Company findCompany(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체를 찾을 수 없습니다. " + companyId));
    }

    private Menu findMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다. " + menuId));
    }

    private List<String> uploadImages(List<MultipartFile> images) throws IOException {
        if (images == null || images.isEmpty()) return new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for (MultipartFile img : images) {
            urls.add(s3Uploader.upload(img, "menus"));
        }
        return urls;
    }

    private void deleteOldImages(List<String> imageUrls) {
        if (imageUrls == null) return;
        for (String url : imageUrls) {
            s3Uploader.deleteByUrl(url);
        }
    }

}
//
