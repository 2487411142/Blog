<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      Blog Management System Developed By LimeMartini.
    </div>
    <div>
      Welcome to subscribe Sangeng's bilibili account ：<a href="https://space.bilibili.com/663528522">https://space.bilibili.com/663528522</a>   三更草堂
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
