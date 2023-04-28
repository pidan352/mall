package com.lyl.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能：
 * * ElasticSearch文档对象
 * * 文档类型映射
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/27 0027
 */

@Data
@Document(indexName = "mall_items")
public class EsItem implements Serializable {
	@Id
	private Long id;
	@Field(analyzer = "ik_max_word", type = FieldType.Text)
	private String title;
	private String image;
	private BigDecimal price;
	private Long goodsId;
	@Field(type = FieldType.Keyword)  //如果要聚合统计，但不作分词
	private String category;
	@Field(type = FieldType.Keyword)
	private String brand;
	@Field(analyzer = "ik_smart", type = FieldType.Text)
	private String seller;
	private String spec;
	private String sellPoint;
}
