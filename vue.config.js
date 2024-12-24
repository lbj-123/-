
let proxyObj={}
const NodePolyfillPlugin = require("node-polyfill-webpack-plugin");
proxyObj['/']={
  ws:false,
  //目标地址
  target:'http://localhost:8081',
  //发送请求host会被设置target
  changeOrigin:true,
  //不重写请求地址
  pathReWrite:{
    '^/':'/'
  }
}

module.exports={
  devServer:{
    host:'localhost',
    port:8080,
    proxy:proxyObj
  },
  configureWebpack: {
    resolve: {
      fallback: {
        fs: false,
        crypto: require.resolve("crypto-browserify"),
        stream: require.resolve("stream-browserify"),
      },
    },
    plugins: [new NodePolyfillPlugin()],
  }
}
