package com.ibk.support.localgov.repository;

import com.ibk.support.localgov.entity.QSupportInfo;
import com.ibk.support.localgov.model.search.SearchRequest;
import com.ibk.support.localgov.model.search.SearchSupportInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class SupportInfoRepositoryImpl implements SupportInfoRepositoryCustom {

    @Autowired
    @Qualifier(value = "jpaQueryFactory")
    private JPAQueryFactory jpaQueryFactory;

    private QSupportInfo supportInfo = QSupportInfo.supportInfo;

    @Override
    public List<SearchSupportInfo> search() {
        return jpaQueryFactory.select(Projections.fields(SearchSupportInfo.class,
                supportInfo.region.region,
                supportInfo.target,
                supportInfo.usage,
                supportInfo.limit,
                supportInfo.rate,
                supportInfo.institute,
                supportInfo.mgmt,
                supportInfo.reception))
                .from(supportInfo)
                .fetch();
    }

    @Override
    public SearchSupportInfo findOne(SearchRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        if (request.isResionCondition()) {
            builder.and(supportInfo.region.region.eq(request.getRegion()));
        }

        return jpaQueryFactory.select(Projections.fields(SearchSupportInfo.class,
                supportInfo.region.region,
                supportInfo.target,
                supportInfo.usage,
                supportInfo.limit,
                supportInfo.rate,
                supportInfo.institute,
                supportInfo.mgmt,
                supportInfo.reception))
                .from(supportInfo)
                .where(builder)
                .fetchOne();
    }
}
