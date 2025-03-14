#!/bin/bash

# 设置环境变量（如果需要）
export ENV="prod"

# 打印当前环境
echo "当前环境: $ENV"

# 执行 Maven 打包，激活 prod 配置
mvn clean package -P prod -D skipTests

# 检查是否构建成功
if [ $? -eq 0 ]; then
    echo "构建成功！"
else
    echo "构建失败！"
    exit 1
fi

# 定义变量
IMAGE_NAME="groot-tools"  # 镜像名称
CONTAINER_NAME="groot-tools"  # 容器名称
PORT="40021"  # 映射端口

# 构建镜像
echo "==> 正在构建 Docker 镜像..."
docker build -t $IMAGE_NAME .

# 停止并删除旧容器（如果存在）
echo "==> 检查并停止旧容器..."
if [ $(docker ps -aq -f name=$CONTAINER_NAME) ]; then
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
  echo "==> 已删除旧容器。"
else
  echo "==> 没有旧容器需要删除。"
fi

# 运行新容器
echo "==> 启动新容器..."
docker run -d --name $CONTAINER_NAME -p $PORT:40021 $IMAGE_NAME

# 检查容器运行状态
if [ $(docker ps -q -f name=$CONTAINER_NAME) ]; then
  echo "==> 容器启动成功！访问 http://localhost:$PORT 查看服务。"
else
  echo "==> 容器启动失败，请检查日志。"
fi