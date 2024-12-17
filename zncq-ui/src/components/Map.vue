<template>
  <div>
    <div>
      <el-input style="width: 200px" v-model="orderNo" placeholder="请输入订单号"></el-input>
      <el-button @click="serch" type="primary">搜索</el-button>
    </div>
    <div id="container"></div>
  </div>
</template>


<script>
// 添加高德安全密钥
import * as AMapLoader from "@amap/amap-jsapi-loader";

window._AMapSecurityConfig = {
  securityJsCode: "846a7bbb2315c4f9d0f4fc4d66b03114"
};

export default {

  name: "EmployeeGPS",
  data() {
    return {
      startPosition: [],
      endPosition: [],
      midPosition: [],
      map: null,
      orderNo: '',
    };
  },
  mounted() {
    this.initAMap()

  },
  methods: {

    // 获取目的地地址，路线规划
    initAMap() {
      const _this = this;
      AMapLoader.load({
        key: "ce49a4f8ed20d5ea9b21516aaddbf456", //设置您的key
        version: "1.4.15",
        plugins: ["AMap.ToolBar", "AMap.Marker"],
        AMapUI: {
          version: "1.1",
          plugins: []
        },
        Loca: {
          version: "1.4.15"
        }
      }).then(AMap => {
        this.initPoint();
        this.map = new AMap.Map("container", {
          viewMode: "2D",
          zoom: 4,
          zooms: [2, 22],
          center: this.midPosition
        });
        let marker1 = new AMap.Marker({
          position: new AMap.LngLat(this.startPosition[0], this.startPosition[1]),
          zoom: 4,
          title: '起点'
        });
        this.map.add(marker1);
        let marker2 = new AMap.Marker({
          position: new AMap.LngLat(this.midPosition[0], this.midPosition[1]),
          zoom: 4,
          title: '订单位置'
        });
        this.map.add(marker2);
        let marker3 = new AMap.Marker({
          position: new AMap.LngLat(this.endPosition[0], this.endPosition[1]),
          zoom: 4,
          title: '终点'
        });
        this.map.add(marker3);
      }).catch(e => {
        console.log(e);
      })
    },
    initPoint(){
      this.startPosition = JSON.parse(window.localStorage.getItem("position"))
      this.midPosition = JSON.parse(window.localStorage.getItem("midPosition"))
      this.endPosition = JSON.parse(window.localStorage.getItem("endPosition"))
    },
    serch() {
      axios.get('http://localhost:8181/map', {params: {orderNo: this.orderNo}})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            console.log(data)
            if (code == 200) {
              window.localStorage.setItem("position", JSON.stringify(data.data.position))
              window.localStorage.setItem("endPosition", JSON.stringify(data.data.endPosition))
              window.localStorage.setItem("midPosition", JSON.stringify(data.data.midPosition))
            }
            this.initPoint();
            this.initAMap();
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
  }
};
</script>
<style scoped>

#container {

  width: 100%;
  height: 100%;
  position: absolute;
}
</style>


