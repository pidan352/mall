package com.lyl.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TbSeckillGoods implements Serializable {
    private Long id;

    /**
     * spu ID
     *
     * @mbg.generated
     */
    private Long goodsId;

    /**
     * sku ID
     *
     * @mbg.generated
     */
    private Long itemId;

    /**
     * 标题
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 商品图片
     *
     * @mbg.generated
     */
    private String smallPic;

    /**
     * 原价格
     *
     * @mbg.generated
     */
    private BigDecimal price;

    /**
     * 秒杀价格
     *
     * @mbg.generated
     */
    private BigDecimal costPrice;

    /**
     * 商家ID
     *
     * @mbg.generated
     */
    private String sellerId;

    /**
     * 添加日期
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 审核日期
     *
     * @mbg.generated
     */
    private Date checkTime;

    /**
     * 审核状态
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 开始时间
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * 结束时间
     *
     * @mbg.generated
     */
    private Date endTime;

    /**
     * 秒杀商品数
     *
     * @mbg.generated
     */
    private Integer num;

    /**
     * 剩余库存数
     *
     * @mbg.generated
     */
    private Integer stockCount;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String introduction;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", itemId=").append(itemId);
        sb.append(", title=").append(title);
        sb.append(", smallPic=").append(smallPic);
        sb.append(", price=").append(price);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", sellerId=").append(sellerId);
        sb.append(", createTime=").append(createTime);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", status=").append(status);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", num=").append(num);
        sb.append(", stockCount=").append(stockCount);
        sb.append(", introduction=").append(introduction);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}