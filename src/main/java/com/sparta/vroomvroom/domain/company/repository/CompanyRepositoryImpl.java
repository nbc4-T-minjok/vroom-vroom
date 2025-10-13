package com.sparta.vroomvroom.domain.company.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.QCompany;
import com.sparta.vroomvroom.domain.company.model.entity.QCompanyCategory;
import com.sparta.vroomvroom.domain.order.model.entity.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private final QCompany company = QCompany.company;
    private final QCompanyCategory category = QCompanyCategory.companyCategory;
    private final QOrder order = QOrder.order;

    @Override
    public Page<Company> searchByKeyword(String keyword, Pageable pageable) {
        BooleanExpression condition = buildKeywordCondition(keyword);
        if (condition == null) {
            //키워드가 문제 있으면 바로 빈 결과 리턴
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        OrderSpecifier<?> sortOrder = getOrderSpecifier(pageable);

        List<Company> content = jpaQueryFactory
                .select(company)
                .from(company)
                .leftJoin(company.companyCategory, category)
                .leftJoin(order).on(order.company.eq(company))
                .where(
                        company.isDeleted.eq(false)
                                .and(condition)
                )
                .groupBy(company.companyId)
                .orderBy(
                        //기본 정렬: 주문 수 내림차순 → 이걸 먼저 적용하고 이후에 사용자 지정 정렬 적용
                        order.count().desc(),
                        sortOrder
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //페이징 용
        long total = jpaQueryFactory
                .select(company.countDistinct())
                .from(company)
                .leftJoin(company.companyCategory, category)
                .where(
                        company.isDeleted.eq(false)
                                .and(condition)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression buildKeywordCondition(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }

        return company.companyName.containsIgnoreCase(keyword)
                .or(company.companyCategory.companyCategoryName.containsIgnoreCase(keyword));
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        //주문많은 순으로 정렬한 이후 적용할 sortBy 기본 = 이름 오름차순
        String sortBy = "companyName";
        Order direction = Order.ASC;

        //사용자가 직접 정렬을 희망하면 반영
        if (pageable != null && !pageable.getSort().isEmpty()) {
            var sortOrder = pageable.getSort().stream().findFirst().orElse(null);
            if (sortOrder != null && sortOrder.getProperty() != null && !sortOrder.getProperty().isBlank()) {
                sortBy = sortOrder.getProperty();
                direction = sortOrder.isAscending() ? Order.ASC : Order.DESC;
            }
        }

        return switch (sortBy) {
            case "companyName" -> new OrderSpecifier<>(direction, company.companyName);
            case "createdAt" -> new OrderSpecifier<>(direction, company.createdAt);
            case "deliveryFee" -> new OrderSpecifier<>(direction, company.deliveryFee);
            default -> new OrderSpecifier<>(Order.ASC, company.companyName);
        };
    }

}
