<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px"label-width="100px"
             class="demo-ruleForm">
      <el-row>
        <el-col :span="6">
          <el-form-item label="仓库名称:" prop="key" style="position: relative;top: -50px">
            <el-select @change="init()" v-model="warehouseId" placeholder="请选择移入库柜名称" clearable
                       :style="{width: '100%'}">
              <el-option v-for="(item, index) in warehouses" :key="index"
                         :label="item.warehouse.name" :value="item.warehouse.id" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div style="width:1300px;height:600px;position: relative;top: -60px;left: -100px" id="container"></div>
  </div>

</template>

<script>
export default {
  data() {
    return {
      warehouseId:'',
      warehouses:[],
    }
  },
  methods:{
    init(){
      let warehouse;
      for (let i = 0; i < this.warehouses.length; i++) {
        if (this.warehouses[i].warehouse.id == this.warehouseId){
          warehouse = this.warehouses[i];
        }
      }
      console.log(warehouse.goodRawVos)
      let good = [];
      let goodNum = [];
      for (let i = 0; i < warehouse.goodRawVos.length; i++) {
        good.push(warehouse.goodRawVos[i].goodName);
        goodNum.push({
          value: warehouse.goodRawVos[i].num,
          itemStyle: {
            color: warehouse.goodRawVos[i].num < 0.1*(warehouse.goodRawVos[i].num + warehouse.goodRawVos[i].enableNum)?'#a90000':'royalblue',
          }
        });
      }
      let chartDom = document.getElementById('container');
      let myChart = this.$echarts.init(chartDom);
      let option
          option = {
        xAxis: {
          type: 'category',
          data: good
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: goodNum,
            type: 'bar',
            label:{
              show:true,
              position:'top',
              fontWeight:'bolder',
              fontSize:18,
            }
          }
        ]
      };
      myChart.setOption(option);
    },
    initWarehouse(){
      axios.get('http://localhost:8181/stock/warn')
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.warehouses = data.data;
            }else{
              this.warehouses = [];
            }
          })
          .catch(error=>{
            this.$message.error(error.message);
          })
    }
  },
  created() {
    this.initWarehouse()
  }
}
</script>