package com.lyl.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TbOrder implements Serializable {
    /**
     * 订单id
     *
     * @mbg.generated
     */
    private Long orderId;

    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     *
     * @mbg.generated
     */
    private BigDecimal payment;

    /**
     * 支付类型，1、在线支付，2、货到付款
     *
     * @mbg.generated
     */
    private String paymentType;

    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     *
     * @mbg.generated
     */
    private String postFee;

    /**
     * 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 订单创建时间
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 订单更新时间
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * 付款时间
     *
     * @mbg.generated
     */
    private Date paymentTime;

    /**
     * 发货时间
     *
     * @mbg.generated
     */
    private Date consignTime;

    /**
     * 交易完成时间
     *
     * @mbg.generated
     */
    private Date endTime;

    /**
     * 交易关闭时间
     *
     * @mbg.generated
     */
    private Date closeTime;

    /**
     * 物流名称
     *
     * @mbg.generated
     */
    private String shippingName;

    /**
     * 物流单号
     *
     * @mbg.generated
     */
    private String shippingCode;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * 买家留言
     *
     * @mbg.generated
     */
    private String buyerMessage;

    /**
     * 买家昵称
     *
     * @mbg.generated
     */
    private String buyerNick;

    /**
     * 买家是否已经评价
     *
     * @mbg.generated
     */
    private String buyerRate;

    /**
     * 收货人地区名称(省，市，县)街道
     *
     * @mbg.generated
     */
    private String receiverAreaName;

    /**
     * 收货人手机
     *
     * @mbg.generated
     */
    private String receiverMobile;

    /**
     * 收货人邮编
     *
     * @mbg.generated
     */
    private String receiverZipCode;

    /**
     * 收货人
     *
     * @mbg.generated
     */
    private String receiver;

    /**
     * 过期时间，定期清理
     *
     * @mbg.generated
     */
    private Date expire;

    /**
     * 发票类型(普通发票，电子发票，增值税发票)
     *
     * @mbg.generated
     */
    private String invoiceType;

    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     *
     * @mbg.generated
     */
    private String sourceType;

    /**
     * 商家ID
     *
     * @mbg.generated
     */
    private String sellerId;

    private static final long serialVersionUID = 1L;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(String buyerRate) {
        this.buyerRate = buyerRate;
    }

    public String getReceiverAreaName() {
        return receiverAreaName;
    }

    public void setReceiverAreaName(String receiverAreaName) {
        this.receiverAreaName = receiverAreaName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", payment=").append(payment);
        sb.append(", paymentType=").append(paymentType);
        sb.append(", postFee=").append(postFee);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", consignTime=").append(consignTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", closeTime=").append(closeTime);
        sb.append(", shippingName=").append(shippingName);
        sb.append(", shippingCode=").append(shippingCode);
        sb.append(", userId=").append(userId);
        sb.append(", buyerMessage=").append(buyerMessage);
        sb.append(", buyerNick=").append(buyerNick);
        sb.append(", buyerRate=").append(buyerRate);
        sb.append(", receiverAreaName=").append(receiverAreaName);
        sb.append(", receiverMobile=").append(receiverMobile);
        sb.append(", receiverZipCode=").append(receiverZipCode);
        sb.append(", receiver=").append(receiver);
        sb.append(", expire=").append(expire);
        sb.append(", invoiceType=").append(invoiceType);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", sellerId=").append(sellerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}