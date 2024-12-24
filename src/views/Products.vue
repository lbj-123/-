<template>
  <div class="home">
    <div class="page-title">
      <h2>商品列表</h2>
    </div>
    <!-- 商品表单 -->
    <el-table :data="products" style="width: 100%" border>
      <el-table-column label="商品ID" prop="id"></el-table-column>
      <el-table-column label="商品名称" prop="name"></el-table-column>
      <el-table-column label="商品价格" prop="price">
      </el-table-column>
      <el-table-column label="库存数量" prop="number">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button class="btn-action" type="primary" @click="addProduct(scope.row)">增加</el-button>
          <el-button class="btn-action" type="danger" @click="removeProduct(scope.row)">删除</el-button>
          <el-button class="btn-action" type="success" @click="addToCart(scope.row)">添加到购物车</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 底部按钮区域 -->
    <div class="footer-buttons">
      <el-button class="btn-footer" type="info" @click="viewCart">查看购物车</el-button>
    </div>
    <!-- 添加商品的对话框 -->
    <el-dialog title="添加商品" :visible.sync="dialogVisible" @close="resetForm">
      <el-form :model="newProduct" ref="productForm" label-width="80px">
        <el-form-item label="商品名称" :rules="[ { required: true, message: '请输入商品名称', trigger: 'blur' } ]">
          <el-input v-model="newProduct.name" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" :rules="[ { required: true, message: '请输入商品价格', trigger: 'blur' } ]">
          <el-input v-model="newProduct.price" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="库存数量" :rules="[ { required: true, message: '请输入商品库存', trigger: 'blur' } ]">
          <el-input v-model="newProduct.number" placeholder="请输入商品库存数量"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitProduct">添加商品</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "Home",
  data() {
    return {
      products: [], // 商品列表数据
      loading: false, // 控制加载动画
      dialogVisible: false, // 控制对话框的显示
      newProduct: { // 新商品的表单数据
        name: '',
        price: null,
        number: null
      },
      cartItem:{}
    };
  },
  methods: {
    //查看购物车
    viewCart(){
      this.$router.push('/Shopping');
    },
    //添加到购物车
    addToCart(product){
      this.loading = true; // 启动加载动画
      this.cartItem ={
        productId:product.id,
        productName:product.name,
        productNumber:1
      }
      this.postRequest('/shopping/add',this.cartItem).then(resp =>{
        console.log(resp)
        this.loading = false;
        this.cartItem=resp;
        this.$message.success("购物项添加成功")
      })
    },
    // 获取所有商品信息
    getAllProducts() {
      this.loading = true; // 启动加载动画
      this.getRequest('/products/getAll') // 发送 GET 请求
          .then(resp => {
            // console.log(resp)
            this.loading = false; // 请求结束
              this.products = resp;
          })
          .catch(error => {
            this.loading = false; // 请求结束
            this.$message.error('加载商品列表请求失败'); // 请求失败，显示错误消息
            console.error(error); // 输出错误信息
          });
    },
    // 增加商品
    addProduct() {
      this.dialogVisible = true;
    },
    submitProduct(){
      this.loading = true;
      this.postRequest('/products/add',this.newProduct).then(resp =>{
        this.loading = false; // 请求结束
        this.dialogVisible = false;
        this.newProduct = { name: '', price: null, number: 0 }; // 重置表单
        this.getAllProducts(); // 刷新商品列表
      })
    },
    removeProduct(product){
      this.loading = true;
      this.deleteRequest(`/products/decreaseNumber?name=${product.name}`).then(resp =>{
        this.loading=false;
        this.getAllProducts();
      })
    },
    // 重置表单
    resetForm() {
      this.newProduct = { name: '', price: null, number: 0 };
    }
  },
  mounted() {
    this.getAllProducts(); // 组件挂载时获取商品信息
  }
};
</script>

<style scoped>
.home {
  width: 100%;
  margin: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
}
 el-table {
  margin-bottom: 20px;
  border-radius: 8px;
}
 el-table-column {
  font-weight: bold;
}
.footer-buttons {
  text-align: center;
  margin-top: 20px;
}

.btn-footer {
  margin: 0 10px;
  padding: 8px 16px;
  font-size: 1rem;
  border-radius: 4px;
}
.btn-action {
  margin: 0 5px;
  padding: 5px 10px;
}

.el-dialog .el-dialog__header {
  background-color: #409EFF;
  color: white;
}

.el-dialog .el-dialog__body {
  padding: 20px;
}
.page-title {
  margin-bottom: 20px;
  text-align: center;
}

.el-form-item label {
  font-size: 1rem;
}

.el-input, .el-input-number {
  width: 100%;
}

</style>
