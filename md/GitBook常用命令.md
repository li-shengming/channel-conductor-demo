# 常用命令

- npm下载(淘宝源)：npm --registry https://registry.npm.taobao.org install gitbook-plugin-toggle-chapters
- 安装gitbook： npm install -g gitbook-cli
- 安装gitbook插件： gitbook install
- gitbook发布：gitbook serve --lrport 35288 --port 4001
- 打成war包：jar -cvf  dist.war *  (提前cd到_book目录)
- 解压war包：jar -xvf dist.war （rz上传命令）
- 上传到服务器上（ip:10.143.135.161,user:yxgly,pwd:Abcd1234）
- 寻找nginx配置文件：nginx -t
- 重新加载nginx：service nginx reload