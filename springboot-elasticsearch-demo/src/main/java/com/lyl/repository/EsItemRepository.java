package com.lyl.repository;

import com.lyl.document.EsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/27 0027
 */

@Repository
public interface EsItemRepository extends ElasticsearchRepository<EsItem, Long> {//第一个泛型是操作的类型，第二个泛型是类型的标识的类型

	Page<EsItem> findByTitleAndBrand(String title, String brand, Pageable pageable);

	//将query的内容放进来
	//参数传递？加上方法穿过来的第几个参数，从0开始算起
	@Query("{\n" +
			"    \"multi_match\": {\n" +
			"      \"query\": \"?0\",\n" +
			"      \"fields\": [\"title\",\"category\"]\n" +
			"    }\n" +
			"  }")
	Page<EsItem> findByKeyword(String keyword, Pageable pageable);

	@Query("{\n" +
			"    \"prefix\": {\n" +
			"      \"title\": {\n" +
			"        \"value\": \"?0\"\n" +
			"      }\n" +
			"    }\n" +
			"  }")
	Page<EsItem> findEsItemByTitlePrefix(String prefix, Pageable pageable);
}
