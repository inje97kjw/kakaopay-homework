package com.ibk.support.localgov.repository;

import com.ibk.support.localgov.model.search.SearchRequest;
import com.ibk.support.localgov.model.search.SearchSupportInfo;

import java.util.List;

public interface SupportInfoRepositoryCustom {
    List<SearchSupportInfo> search();
    SearchSupportInfo findOne(SearchRequest request);
}
