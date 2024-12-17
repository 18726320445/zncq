<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'app',
  components: {
    HelloWorld
  },
  mounted() {
    // window.onbeforeunload = function (ev) {
    //   const storage = window.localStorage;
    //   storage.clear();
    // }
  }
}
</script>


