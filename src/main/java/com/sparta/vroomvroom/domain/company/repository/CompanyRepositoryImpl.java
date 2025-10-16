package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import com.sparta.vroomvroom.domain.region.emd.model.entity.Emd;
import com.sparta.vroomvroom.global.conmon.constants.BusinessStatus;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    //검색어 기반 업체 조회
    @Override
    public Page<Company> searchByKeywordAndLocation(String keyword, Point location, Pageable pageable) {
        String wkt = String.format("POINT(%s %s)", location.getX(), location.getY());
        String likeKeyword = keyword == null ? null : "%" + keyword.toLowerCase() + "%";

        //동적 정렬 처리
        String orderBy = buildOrderBy(pageable);

        //2km 내 원범위를 포함하는 읍면동만 추출
        List<UUID> emdIds = getNearbyEmdIds(location);
        if (emdIds.isEmpty()) return new PageImpl<>(Collections.emptyList(), pageable, 0);

        //해당 읍면동에 포함되면서 현재 위치와 2km 이내 거리에 있는 Company 조회
        //만들어둔 동적 정렬을 적용
        String sql = "SELECT c.*, " +
                "ST_X(c.location) AS location_x, ST_Y(c.location) AS location_y, " +
                "cc.company_category_id, cc.company_category_name, " +
                "e.emd_id, e.name_kr, " +
                "COALESCE(co.order_count, 0) AS order_count, " +
                "COALESCE(r.avg_rating, 0) AS avg_rating " +
                "FROM companies c " +
                "LEFT JOIN company_categories cc ON c.company_category_id = cc.company_category_id " +
                "LEFT JOIN (" +
                "    SELECT o.company_id AS o_company_id, COUNT(o.order_id) AS order_count " +
                "    FROM orders o " +
                "    WHERE o.order_status = 'COMPLETED' " +
                "    GROUP BY o.company_id" +
                ") co ON co.o_company_id = c.company_id " +
                "LEFT JOIN (" +
                "    SELECT comp_id AS r_company_id, AVG(rate) AS avg_rating " +
                "    FROM reviews WHERE is_deleted = false GROUP BY comp_id" +
                ") r ON r.r_company_id = c.company_id " +
                "LEFT JOIN emd e ON e.emd_id = c.emd_id " +
                "WHERE c.is_deleted = false " +
                "AND c.emd_id IN (:emdIds) " +
                "AND (:keyword IS NULL OR LOWER(c.company_name) LIKE :likeKeyword OR LOWER(cc.company_category_name) LIKE :likeKeyword) " +
                "AND ST_DWithin(c.location::geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography, 2000) " +
                "ORDER BY " + orderBy + " " +
                "LIMIT :limit OFFSET :offset";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("emdIds", emdIds)
                .addValue("keyword", keyword)
                .addValue("likeKeyword", likeKeyword)
                .addValue("lng", location.getX())
                .addValue("lat", location.getY())
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset())
                .addValue("wkt", wkt);

        List<Company> companies = jdbcTemplate.query(sql, params, new CompanyRowMapper());
        attachBusinessHoursAndSpecialHours(companies);

        //페이징을 위한 전체 카운트
        String countSql = "SELECT COUNT(*) " +
                "FROM companies c " +
                "LEFT JOIN company_categories cc ON c.company_category_id = cc.company_category_id " +
                "WHERE c.is_deleted = false " +
                "AND c.emd_id IN (:emdIds) " +
                "AND (:keyword IS NULL OR LOWER(c.company_name) LIKE :likeKeyword OR LOWER(cc.company_category_name) LIKE :likeKeyword) " +
                "AND ST_DWithin(c.location, ST_GeomFromText(:wkt, 4326), 2000)";
        long total = jdbcTemplate.queryForObject(countSql, params, Long.class);

        return new PageImpl<>(companies, pageable, total);
    }

    //카테고리 기반 업체 조회
    @Override
    public Page<Company> searchByCategoryAndLocation(UUID categoryId, Point location, Pageable pageable) {
        String wkt = String.format("POINT(%s %s)", location.getX(), location.getY());
        String orderBy = buildOrderBy(pageable);

        //2km 내 원범위를 포함하는 읍면동만 추출
        List<UUID> emdIds = getNearbyEmdIds(location);
        if (emdIds.isEmpty()) return new PageImpl<>(Collections.emptyList(), pageable, 0);

        //검색과 마찬가지로 동적 정렬, 포함하는 읍면동 중 2km이내만 조회. 단 카테고리가 넘겨받은 것과 일치하는 업체만
        String sql = "SELECT c.*, " +
                "ST_X(c.location) AS location_x, ST_Y(c.location) AS location_y, " +
                "cc.company_category_id, cc.company_category_name, " +
                "e.emd_id, e.name_kr, " +
                "COALESCE(co.order_count, 0) AS order_count, " +
                "COALESCE(r.avg_rating, 0) AS avg_rating " +
                "FROM companies c " +
                "LEFT JOIN company_categories cc ON c.company_category_id = cc.company_category_id " +
                "LEFT JOIN (" +
                "    SELECT o.company_id AS o_company_id, COUNT(o.order_id) AS order_count " +
                "    FROM orders o " +
                "    WHERE o.order_status = 'COMPLETED' " +
                "    GROUP BY o.company_id" +
                ") co ON co.o_company_id = c.company_id " +
                "LEFT JOIN (" +
                "    SELECT comp_id AS r_company_id, AVG(rate) AS avg_rating " +
                "    FROM reviews WHERE is_deleted = false GROUP BY comp_id" +
                ") r ON r.r_company_id = c.company_id " +
                "LEFT JOIN emd e ON e.emd_id = c.emd_id " +
                "WHERE c.is_deleted = false " +
                "AND c.company_category_id = :categoryId " +
                "AND c.emd_id IN (:emdIds) " +
                "AND ST_DWithin(c.location::geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography, 2000) " +
                "ORDER BY " + orderBy + " " +
                "LIMIT :limit OFFSET :offset";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("categoryId", categoryId)
                .addValue("emdIds", emdIds)
                .addValue("lng", location.getX())
                .addValue("lat", location.getY())
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset())
                .addValue("wkt", wkt);

        List<Company> companies = jdbcTemplate.query(sql, params, new CompanyRowMapper());
        attachBusinessHoursAndSpecialHours(companies);

        String countSql = "SELECT COUNT(*) " +
                "FROM companies c " +
                "WHERE c.is_deleted = false " +
                "AND c.company_category_id = :categoryId " +
                "AND c.emd_id IN (:emdIds) " +
                "AND ST_DWithin(c.location, ST_GeomFromText(:wkt, 4326), 2000)";
        long total = jdbcTemplate.queryForObject(countSql, params, Long.class);

        return new PageImpl<>(companies, pageable, total);
    }

    // <----------유틸 함수 ------------------>
    //현재 위치로부터 반경 2km 원을그리고 이를 포함하는 읍면동 조회
    private List<UUID> getNearbyEmdIds(Point location) {
        String emdSql = "SELECT e.emd_id " +
                "FROM emd e " +
                "WHERE ST_Intersects(" +
                "    ST_Buffer(ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography, 2000)::geometry, " +
                "    e.geom" +
                ")";
        MapSqlParameterSource emdParams = new MapSqlParameterSource()
                .addValue("lng", location.getX())
                .addValue("lat", location.getY());
        return jdbcTemplate.queryForList(emdSql, emdParams, UUID.class);
    }

    //넘겨받은 sortBy, isAsc에 따라 정렬 조건 동적 생성
    private String buildOrderBy(Pageable pageable) {
        String orderBy = "co.order_count DESC";
        if (pageable.getSort().isSorted()) {
            StringBuilder sb = new StringBuilder();
            pageable.getSort().forEach(sort -> {
                String column = switch (sort.getProperty()) {
                    case "companyName" -> "c.company_name";
                    case "avgRating" -> "avg_rating";
                    case "createdAt" -> "c.created_at";
                    default -> "co.order_count";
                };
                sb.append(column)
                        .append(sort.isAscending() ? " ASC" : " DESC")
                        .append(", ");
            });
            sb.setLength(sb.length() - 2);
            orderBy = sb.toString();
        }
        return orderBy;
    }

    //조회된 업체에 만들어진 영업시간, 특별 영업시간 조회
    private void attachBusinessHoursAndSpecialHours(List<Company> companies) {
        if (companies.isEmpty()) return;
        List<UUID> companyIds = companies.stream().map(Company::getCompanyId).collect(Collectors.toList());
        Map<UUID, Company> companyMap = companies.stream()
                .collect(Collectors.toMap(Company::getCompanyId, c -> c));

        // BusinessHour
        String bhSql = "SELECT * FROM business_hours WHERE company_id IN (:ids) AND is_deleted = false";
        MapSqlParameterSource bhParams = new MapSqlParameterSource("ids", companyIds);
        List<BusinessHour> bhs = jdbcTemplate.query(bhSql, bhParams, (rs, rowNum) -> {
            BusinessHour bh = new BusinessHour();
            bh.setBusinessHourId(rs.getObject("business_hour_id", UUID.class));
            bh.setDay(WeekDay.valueOf(rs.getString("day")));
            bh.setOpenedAt(rs.getObject("opened_at", LocalTime.class));
            bh.setClosedAt(rs.getObject("closed_at", LocalTime.class));
            bh.setCompany(new Company());
            bh.getCompany().setCompanyId(rs.getObject("company_id", UUID.class));
            return bh;
        });
        bhs.forEach(bh -> {
            Company c = companyMap.get(bh.getCompany().getCompanyId());
            bh.setCompany(c);
            c.getBusinessHours().add(bh);
        });

        // SpecialBusinessHour
        String sbhSql = "SELECT * FROM special_business_hours WHERE company_id IN (:ids) AND is_deleted = false";
        List<SpecialBusinessHour> sbhs = jdbcTemplate.query(sbhSql, bhParams, (rs, rowNum) -> {
            SpecialBusinessHour sbh = new SpecialBusinessHour();
            sbh.setSpecialBusinessHourId(rs.getObject("special_business_hour_id", UUID.class));
            sbh.setDate(rs.getObject("date", LocalDate.class));
            sbh.setOpenedAt(rs.getObject("opened_at", LocalTime.class));
            sbh.setClosedAt(rs.getObject("closed_at", LocalTime.class));
            sbh.setBusinessStatus(BusinessStatus.valueOf(rs.getString("business_status")));
            sbh.setCompany(new Company());
            sbh.getCompany().setCompanyId(rs.getObject("company_id", UUID.class));
            return sbh;
        });
        sbhs.forEach(sbh -> {
            Company c = companyMap.get(sbh.getCompany().getCompanyId());
            sbh.setCompany(c);
            c.getSpecialBusinessHours().add(sbh);
        });
    }

    //값을 수동으로 세팅
    private class CompanyRowMapper implements RowMapper<Company> {
        @Override
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
            Company company = new Company();

            company.setCompanyId(rs.getObject("company_id", UUID.class));
            company.setCompanyName(rs.getString("company_name"));
            company.setCompanyLogoUrl(rs.getString("company_logo_url"));
            company.setCompanyDescription(rs.getString("company_description"));
            company.setPhoneNumber(rs.getString("phone_number"));
            company.setDeliveryFee(rs.getInt("delivery_fee"));
            company.setDeliveryRadius(rs.getInt("delivery_radius"));
            company.setOwnerName(rs.getString("owner_name"));
            company.setBizRegNo(rs.getString("biz_reg_no"));
            company.setAddress(rs.getString("address"));
            company.setDetailAddress(rs.getString("detail_address"));
            company.setZipCode(rs.getString("zip_code"));

            //감사/삭제 필드
            company.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
            company.setCreatedBy(rs.getString("created_by"));

            //PostGIS Point
            double x = rs.getDouble("location_x");
            double y = rs.getDouble("location_y");
            Point loc = geometryFactory.createPoint(new Coordinate(x, y));
            company.setLocation(loc);

            //CompanyCategory
            CompanyCategory category = new CompanyCategory();
            category.setCompanyCategoryId(rs.getObject("company_category_id", UUID.class));
            category.setCompanyCategoryName(rs.getString("company_category_name"));
            company.setCompanyCategory(category);

            //Emd
            Emd emd = new Emd();
            emd.setEmdId(rs.getObject("emd_id", UUID.class));
            emd.setNameKr(rs.getString("name_kr"));
            company.setEmd(emd);

            //초기화
            company.setBusinessHours(new ArrayList<>());
            company.setSpecialBusinessHours(new ArrayList<>());

            return company;
        }
    }
}