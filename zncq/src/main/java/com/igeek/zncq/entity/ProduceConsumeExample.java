package com.igeek.zncq.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProduceConsumeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProduceConsumeExample() {
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

        public Criteria andWarehouseIdIsNull() {
            addCriterion("warehouse_id is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdIsNotNull() {
            addCriterion("warehouse_id is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdEqualTo(Integer value) {
            addCriterion("warehouse_id =", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdNotEqualTo(Integer value) {
            addCriterion("warehouse_id <>", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdGreaterThan(Integer value) {
            addCriterion("warehouse_id >", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("warehouse_id >=", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdLessThan(Integer value) {
            addCriterion("warehouse_id <", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdLessThanOrEqualTo(Integer value) {
            addCriterion("warehouse_id <=", value, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdIn(List<Integer> values) {
            addCriterion("warehouse_id in", values, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdNotIn(List<Integer> values) {
            addCriterion("warehouse_id not in", values, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdBetween(Integer value1, Integer value2) {
            addCriterion("warehouse_id between", value1, value2, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andWarehouseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("warehouse_id not between", value1, value2, "warehouseId");
            return (Criteria) this;
        }

        public Criteria andRawNumIsNull() {
            addCriterion("raw_num is null");
            return (Criteria) this;
        }

        public Criteria andRawNumIsNotNull() {
            addCriterion("raw_num is not null");
            return (Criteria) this;
        }

        public Criteria andRawNumEqualTo(Long value) {
            addCriterion("raw_num =", value, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumNotEqualTo(Long value) {
            addCriterion("raw_num <>", value, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumGreaterThan(Long value) {
            addCriterion("raw_num >", value, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumGreaterThanOrEqualTo(Long value) {
            addCriterion("raw_num >=", value, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumLessThan(Long value) {
            addCriterion("raw_num <", value, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumLessThanOrEqualTo(Long value) {
            addCriterion("raw_num <=", value, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumIn(List<Long> values) {
            addCriterion("raw_num in", values, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumNotIn(List<Long> values) {
            addCriterion("raw_num not in", values, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumBetween(Long value1, Long value2) {
            addCriterion("raw_num between", value1, value2, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawNumNotBetween(Long value1, Long value2) {
            addCriterion("raw_num not between", value1, value2, "rawNum");
            return (Criteria) this;
        }

        public Criteria andRawIdIsNull() {
            addCriterion("raw_id is null");
            return (Criteria) this;
        }

        public Criteria andRawIdIsNotNull() {
            addCriterion("raw_id is not null");
            return (Criteria) this;
        }

        public Criteria andRawIdEqualTo(Integer value) {
            addCriterion("raw_id =", value, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdNotEqualTo(Integer value) {
            addCriterion("raw_id <>", value, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdGreaterThan(Integer value) {
            addCriterion("raw_id >", value, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("raw_id >=", value, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdLessThan(Integer value) {
            addCriterion("raw_id <", value, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdLessThanOrEqualTo(Integer value) {
            addCriterion("raw_id <=", value, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdIn(List<Integer> values) {
            addCriterion("raw_id in", values, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdNotIn(List<Integer> values) {
            addCriterion("raw_id not in", values, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdBetween(Integer value1, Integer value2) {
            addCriterion("raw_id between", value1, value2, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawIdNotBetween(Integer value1, Integer value2) {
            addCriterion("raw_id not between", value1, value2, "rawId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdIsNull() {
            addCriterion("raw_container_id is null");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdIsNotNull() {
            addCriterion("raw_container_id is not null");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdEqualTo(Integer value) {
            addCriterion("raw_container_id =", value, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdNotEqualTo(Integer value) {
            addCriterion("raw_container_id <>", value, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdGreaterThan(Integer value) {
            addCriterion("raw_container_id >", value, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("raw_container_id >=", value, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdLessThan(Integer value) {
            addCriterion("raw_container_id <", value, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdLessThanOrEqualTo(Integer value) {
            addCriterion("raw_container_id <=", value, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdIn(List<Integer> values) {
            addCriterion("raw_container_id in", values, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdNotIn(List<Integer> values) {
            addCriterion("raw_container_id not in", values, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdBetween(Integer value1, Integer value2) {
            addCriterion("raw_container_id between", value1, value2, "rawContainerId");
            return (Criteria) this;
        }

        public Criteria andRawContainerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("raw_container_id not between", value1, value2, "rawContainerId");
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

        public Criteria andGoodNumIsNull() {
            addCriterion("good_num is null");
            return (Criteria) this;
        }

        public Criteria andGoodNumIsNotNull() {
            addCriterion("good_num is not null");
            return (Criteria) this;
        }

        public Criteria andGoodNumEqualTo(Long value) {
            addCriterion("good_num =", value, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumNotEqualTo(Long value) {
            addCriterion("good_num <>", value, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumGreaterThan(Long value) {
            addCriterion("good_num >", value, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumGreaterThanOrEqualTo(Long value) {
            addCriterion("good_num >=", value, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumLessThan(Long value) {
            addCriterion("good_num <", value, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumLessThanOrEqualTo(Long value) {
            addCriterion("good_num <=", value, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumIn(List<Long> values) {
            addCriterion("good_num in", values, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumNotIn(List<Long> values) {
            addCriterion("good_num not in", values, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumBetween(Long value1, Long value2) {
            addCriterion("good_num between", value1, value2, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodNumNotBetween(Long value1, Long value2) {
            addCriterion("good_num not between", value1, value2, "goodNum");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdIsNull() {
            addCriterion("good_container_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdIsNotNull() {
            addCriterion("good_container_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdEqualTo(Integer value) {
            addCriterion("good_container_id =", value, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdNotEqualTo(Integer value) {
            addCriterion("good_container_id <>", value, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdGreaterThan(Integer value) {
            addCriterion("good_container_id >", value, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("good_container_id >=", value, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdLessThan(Integer value) {
            addCriterion("good_container_id <", value, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdLessThanOrEqualTo(Integer value) {
            addCriterion("good_container_id <=", value, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdIn(List<Integer> values) {
            addCriterion("good_container_id in", values, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdNotIn(List<Integer> values) {
            addCriterion("good_container_id not in", values, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdBetween(Integer value1, Integer value2) {
            addCriterion("good_container_id between", value1, value2, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andGoodContainerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("good_container_id not between", value1, value2, "goodContainerId");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
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