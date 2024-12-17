package com.igeek.zncq.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehouseTransferExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WarehouseTransferExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Long value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Long value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Long value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Long value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Long value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Long value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Long> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Long> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Long value1, Long value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Long value1, Long value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdIsNull() {
            addCriterion("original_warehouse_id is null");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdIsNotNull() {
            addCriterion("original_warehouse_id is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdEqualTo(Integer value) {
            addCriterion("original_warehouse_id =", value, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdNotEqualTo(Integer value) {
            addCriterion("original_warehouse_id <>", value, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdGreaterThan(Integer value) {
            addCriterion("original_warehouse_id >", value, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("original_warehouse_id >=", value, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdLessThan(Integer value) {
            addCriterion("original_warehouse_id <", value, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdLessThanOrEqualTo(Integer value) {
            addCriterion("original_warehouse_id <=", value, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdIn(List<Integer> values) {
            addCriterion("original_warehouse_id in", values, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdNotIn(List<Integer> values) {
            addCriterion("original_warehouse_id not in", values, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdBetween(Integer value1, Integer value2) {
            addCriterion("original_warehouse_id between", value1, value2, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalWarehouseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("original_warehouse_id not between", value1, value2, "originalWarehouseId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdIsNull() {
            addCriterion("original_container_id is null");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdIsNotNull() {
            addCriterion("original_container_id is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdEqualTo(Integer value) {
            addCriterion("original_container_id =", value, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdNotEqualTo(Integer value) {
            addCriterion("original_container_id <>", value, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdGreaterThan(Integer value) {
            addCriterion("original_container_id >", value, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("original_container_id >=", value, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdLessThan(Integer value) {
            addCriterion("original_container_id <", value, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdLessThanOrEqualTo(Integer value) {
            addCriterion("original_container_id <=", value, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdIn(List<Integer> values) {
            addCriterion("original_container_id in", values, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdNotIn(List<Integer> values) {
            addCriterion("original_container_id not in", values, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdBetween(Integer value1, Integer value2) {
            addCriterion("original_container_id between", value1, value2, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andOriginalContainerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("original_container_id not between", value1, value2, "originalContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferdateIsNull() {
            addCriterion("transferDate is null");
            return (Criteria) this;
        }

        public Criteria andTransferdateIsNotNull() {
            addCriterion("transferDate is not null");
            return (Criteria) this;
        }

        public Criteria andTransferdateEqualTo(Date value) {
            addCriterion("transferDate =", value, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateNotEqualTo(Date value) {
            addCriterion("transferDate <>", value, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateGreaterThan(Date value) {
            addCriterion("transferDate >", value, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateGreaterThanOrEqualTo(Date value) {
            addCriterion("transferDate >=", value, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateLessThan(Date value) {
            addCriterion("transferDate <", value, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateLessThanOrEqualTo(Date value) {
            addCriterion("transferDate <=", value, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateIn(List<Date> values) {
            addCriterion("transferDate in", values, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateNotIn(List<Date> values) {
            addCriterion("transferDate not in", values, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateBetween(Date value1, Date value2) {
            addCriterion("transferDate between", value1, value2, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferdateNotBetween(Date value1, Date value2) {
            addCriterion("transferDate not between", value1, value2, "transferdate");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdIsNull() {
            addCriterion("transfer_container_id is null");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdIsNotNull() {
            addCriterion("transfer_container_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdEqualTo(Integer value) {
            addCriterion("transfer_container_id =", value, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdNotEqualTo(Integer value) {
            addCriterion("transfer_container_id <>", value, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdGreaterThan(Integer value) {
            addCriterion("transfer_container_id >", value, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_container_id >=", value, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdLessThan(Integer value) {
            addCriterion("transfer_container_id <", value, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_container_id <=", value, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdIn(List<Integer> values) {
            addCriterion("transfer_container_id in", values, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdNotIn(List<Integer> values) {
            addCriterion("transfer_container_id not in", values, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdBetween(Integer value1, Integer value2) {
            addCriterion("transfer_container_id between", value1, value2, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferContainerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_container_id not between", value1, value2, "transferContainerId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdIsNull() {
            addCriterion("transfer_warehouse_id is null");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdIsNotNull() {
            addCriterion("transfer_warehouse_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdEqualTo(Integer value) {
            addCriterion("transfer_warehouse_id =", value, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdNotEqualTo(Integer value) {
            addCriterion("transfer_warehouse_id <>", value, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdGreaterThan(Integer value) {
            addCriterion("transfer_warehouse_id >", value, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_warehouse_id >=", value, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdLessThan(Integer value) {
            addCriterion("transfer_warehouse_id <", value, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_warehouse_id <=", value, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdIn(List<Integer> values) {
            addCriterion("transfer_warehouse_id in", values, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdNotIn(List<Integer> values) {
            addCriterion("transfer_warehouse_id not in", values, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdBetween(Integer value1, Integer value2) {
            addCriterion("transfer_warehouse_id between", value1, value2, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andTransferWarehouseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_warehouse_id not between", value1, value2, "transferWarehouseId");
            return (Criteria) this;
        }

        public Criteria andGoodIdIsNull() {
            addCriterion("good_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodIdIsNotNull() {
            addCriterion("good_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodIdEqualTo(Integer value) {
            addCriterion("good_id =", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotEqualTo(Integer value) {
            addCriterion("good_id <>", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdGreaterThan(Integer value) {
            addCriterion("good_id >", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("good_id >=", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdLessThan(Integer value) {
            addCriterion("good_id <", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdLessThanOrEqualTo(Integer value) {
            addCriterion("good_id <=", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdIn(List<Integer> values) {
            addCriterion("good_id in", values, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotIn(List<Integer> values) {
            addCriterion("good_id not in", values, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdBetween(Integer value1, Integer value2) {
            addCriterion("good_id between", value1, value2, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotBetween(Integer value1, Integer value2) {
            addCriterion("good_id not between", value1, value2, "goodId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}