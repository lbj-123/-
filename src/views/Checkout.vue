<template>
  <div class="checkout">
    <h2>订单详情</h2>
    <el-table :data="order.products" style="width: 100%" border>
      <el-table-column label="商品名称" prop="productName"></el-table-column>
      <el-table-column label="数量" prop="productNumber"></el-table-column>
      <el-table-column label="单价" prop="productPrice"></el-table-column>
      <el-table-column label="总价" prop="totalPrice"></el-table-column>
    </el-table>

    <div class="total-price">
      <span><strong>总价：</strong>￥{{ order.sumPrice }}</span>
    </div>
    <!-- 提交订单按钮 -->
<!--    <el-button type="primary" @click="submitOrder">提交订单</el-button>-->
  </div>
</template>

<script>
export default {
  data() {
    return {
         order:{
           sumPrice:0,
           products:[]
         }
    };
  },
  mounted() {
    this.getOrder();
  },
  methods: {
    //获取订单信息
    getOrder(){
      this.loading = true;
      this.getRequest('/order/generateOrder')
          .then(resp => {
            // console.log(resp)
            this.loading = false;
            this.order =resp
          })
    },
    // 提交订单的处理方法
    submitOrder() {
      this.$message.success("订单已提交成功！");
    }
  }
};
</script>

<style scoped>
.checkout {
  margin: 20px;
}

.total-price {
  margin-top: 20px;
  font-size: 18px;
}

.el-button {
  margin-top: 20px;
}
</style>
