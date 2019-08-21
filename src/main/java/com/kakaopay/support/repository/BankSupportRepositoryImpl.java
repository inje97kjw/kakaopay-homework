package com.kakaopay.support.repository;

import com.kakaopay.support.entity.BankSupport;
import com.kakaopay.support.entity.QBankSupport;
import com.kakaopay.support.model.search.RequestBankSupport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class BankSupportRepositoryImpl implements BankSupportRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public BankSupportRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private QBankSupport qBankSupport = QBankSupport.bankSupport;

    @Override
    public List<BankSupport> search(RequestBankSupport request) {
        BooleanBuilder builder = new BooleanBuilder();
        if (request.isResionCondition()) {
            builder.and(qBankSupport.regionCode.name.eq(request.getRegion()));
        }
        return jpaQueryFactory.select(qBankSupport)
                .from(qBankSupport)
                .where(builder)
                .fetch();
    }
}
