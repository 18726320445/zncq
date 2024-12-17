<template>
  <div style="height: 800px;">
    <div
        style="width: 100% ;height: 150px;background:antiquewhite;border-top: 10px solid lavender;border-bottom: 10px solid lavender">
      <div style="font-size: 25px;position: relative;top: 20px;left: 430px">欢迎你使用智能厂区监控系统 当前时间:{{ nowTime }}</div>
      <div style="font-size: 25px;position: relative;top: 50px;left: 430px">有{{ title.warn }}个仓库中有原料数量进入预警，请及时查看并处理
      </div>
    </div>
    <div style="width: 100% ;height: 150px;background:antiquewhite;border-bottom: 10px solid lavender">
      <div style="width: 25%;display: inline-block;position: relative;top: 10px">
        <i class="iconfont icon-dengdaichukusvg" style="font-size: 35px;color:#409EFF"></i>
        <span style="position: relative;left: 10px;top: -7px">待出库</span>
        <div style="transform: scale(1.4);position: relative;top: 20px">
          <el-statistic group-separator="," :value="title.outStorages" title="待出库货物数量"></el-statistic>
        </div>
      </div>
      <div style="width: 25%;display: inline-block;position: relative;top: 10px">
        <i class="iconfont icon-dengdairukusvg" style="font-size: 35px;color:orange"></i>
        <span style="position: relative;left: 10px;top: -7px">待入库</span>
        <div style="transform: scale(1.4);position: relative;top: 20px">
          <el-statistic group-separator="," :value="title.inStorages" title="待入库货物数量"></el-statistic>
        </div>
      </div>
      <div style="width: 25%;display: inline-block;position: relative;top: 10px">
        <i class="iconfont icon-he_34hetongguanli" style="font-size: 35px;color:red"></i>
        <span style="position: relative;left: 10px;top: -7px">待签署</span>
        <div style="transform: scale(1.4);position: relative;top: 20px">
          <el-statistic group-separator="," :value="title.contracts" title="待签署合同数量"></el-statistic>
        </div>
      </div>
      <div style="width: 25%;display: inline-block;position: relative;top: 10px">
        <i class="iconfont icon-dingdanxiangqing-dingdanbianhao" style="font-size: 35px;color:darkred"></i>
        <span style="position: relative;left: 10px;top: -7px">待审核</span>
        <div style="transform: scale(1.4);position: relative;top: 20px">
          <el-statistic group-separator="," :value="title.purchases" title="待审核订单数量"></el-statistic>
        </div>
      </div>
    </div>
    <div style="background:antiquewhite">
      <div className="one" style="width:600px;height:600px;position: relative;top: -90px" id="main"></div>
      <div className="one" style="width:500px;height:420px;position: relative;top:-600px;left: 60%" id="mains"></div>
      <div className="one" style="width:1100px;height:420px;position: relative;" id="mainss"></div>
    </div>
  </div>
</template>

<script>
//局部引用
export default {
  name: "FinanceView",
  data() {
    return {
      value2: 1000,
      timer: undefined,
      nowTime: new Date(),
      arr: [{
        name: 'n1',
        value: 1
      },
        {
          name: 'n2',
          value: 2
        }, {
          name: 'n3',
          value: 3
        }],
      TopName: [],
      TopValue: [],
      title: {
        contracts: '',
        inStorages: '',
        outStorages: '',
        purchases: '',
        warn: ''
      }
    }
  },

  mounted() {
    this.initChart()
  },
  methods: {
    initChart() {
      axios.get('http://localhost:8181/index/count')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            console.log(data)
            if (code == 200) {
              this.title = data.data;
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          });
      axios.get('http://localhost:8181/index/goodTopSeven')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            console.log(data)
            if (code == 200) {
              this.TopValue = [];
              this.TopName = [];
              for (let key in data.data) {
                this.TopName.push(key);
                this.TopValue.push(data.data[key])
              }
            }
            this.drawSource()
          })
          .catch(error => {
            this.$message.error(error.message)
          })

      axios.get('http://localhost:8181/index/raw')
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              console.log(data)
              if (code == 200) {
                this.arr = [];
                for (let key in data.data) {
                  this.arr.push({
                    name:key,
                    value: data.data[key]
                  })
                }
              }
              this.drawSource2()
            })
            .catch(error=>{
              this.$message.error(error.message)
            })
    },
    drawSource() {
      let myCharts = this.$echarts.init(document.getElementById('mains'));
      let options = {
        title: {text: '近一周出库产品前七', left: 'center'},
        toolbox: {
          feature: {
            saveAsImage: {},
            magicType: {
              type: ['bar', 'line']
            }
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.TopName,
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.TopValue,
            type: 'bar',
          }
        ]
      };
      myCharts.setOption(options);
    },
    drawSource2(){
      let myChart = this.$echarts.init(document.getElementById('main'));
      let option = {
        title: {
          yAxis: 10,
          text: '原料货物占比',
          subtext: '',
          left: 240,
          top: 100,
        },
        tooltip: {
          trigger: 'item',
          formatter: "{b}:{c} <br/>({d}%)"
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          top: 100
        },
        toolbox: {
          top:100,
          right:100,
          feature: {
            saveAsImage: {},
          }
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: this.arr,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.setOption(option);
    },
  },
  created() {
    //时间1方法
    // 要显示时间，在渲染页面之前一直调用该函数，对this.time进行赋值开启定时
    this.timer = setInterval(() => {
      this.nowTime = new Date().Format('yyyy年 MM月 dd日 hh时 mm分 ss秒');
    });
  },
  // 关闭页面销毁定时器
  beforeDestroy() {

    if (this.timer) {

      clearInterval(this.timer);

    }

  },

}
</script>
<style>
.one {
  display: inline-block
}
</style>