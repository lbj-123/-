<!-- Shopping.vue -->
<template>
  <div class="shopping">
    <div class="page-title">
      <h2>购物车</h2>
    </div>
    <el-table :data="cartItems" style="width: 100%" border>
      <el-table-column label="商品ID" prop="productId"></el-table-column>
      <el-table-column label="商品名称" prop="productName"></el-table-column>
      <el-table-column label="商品数量" prop="productNumber"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <div style="display: flex; justify-content: center;">
            <el-button type="danger" @click="removeFromCart(scope.row)">移除购物车</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 底部按钮 -->
    <div class="footer-buttons">
      <el-button class="btn-checkout" type="primary" @click="proceedToCheckout">生成订单</el-button>
      <el-button class="btn-price" type="success" @click="getSumPrice">查看购物总价</el-button>
    </div>

    <!-- 弹出框显示总价 -->
    <el-dialog :visible.sync="dialogVisible" title="购物商品总价">
      <span>购物车总价：{{ sumPrice }} 元</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false, // 控制加载动画
      dialogVisible: false, // 控制对话框的显示
      cartItems: {},// 购物车数据
      sumPrice: null, // 购物车总价
    };
  },
  mounted() {
    this.getCartItems();
  },
  methods: {
    //获取总价
    getSumPrice(){
      this.loading = true;
      this.getRequest('/shopping/generateSumPrice').then(resp=>{
        this.loading = false;
        this.sumPrice = resp.obj
        this.dialogVisible = true;
      })
    },
    // 获取购物车数据
    getCartItems() {
      this.loading = true;
      this.getRequest('/shopping/getAll')
          .then(resp => {
            // console.log(resp)
            this.loading = false;
            this.cartItems =resp
          })
          .catch(error => {
            this.loading = false; // 请求结束
            this.$message.error('查看购物车信息请求失败'); // 请求失败，显示错误消息
            console.error(error); // 输出错误信息
          });
    },

    // 删除购物车中的商品
    removeFromCart(shopping) {
      this.loading = true;
      this.deleteRequest(`/shopping/deleteNumber?productName=${shopping.productName}`)
          .then(() => {
            this.loading=false;
            this.getCartItems(); // 重新加载购物车数据
          })
          .catch(error => {
            this.$message.error('移除购物车商品失败');
            console.error(error);
          });
    },

    // 去结算
    proceedToCheckout() {
      this.$router.push('/checkout');
    }
  }
};
</script>

<style scoped>
.shopping {
  padding: 20px;
  background-color: #f9f9f9;
}

.page-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

 el-table {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

 el-table-column {
  font-weight: bold;
}

.footer-buttons {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.btn-checkout, .btn-price {
  width: 48%;
}

 btn-remove {
  width: 100px;
  margin-top: 5px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}

 el-button {
  border-radius: 4px;
}
</style>
