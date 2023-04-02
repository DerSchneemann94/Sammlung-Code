#!/bin/bash
echo -e "\nLoging in to Docker"
docker login
echo -e "\nBUILDING API GATEWAY CONTAINER"
docker build -t arkanipro/cnd-api-gateway ../../api-gateway
echo -e "\nBUILDING API FETCHING SERVICE CONTAINER"
docker build -t arkanipro/cnd-api-fetching ../../api-fetching-service
echo -e "\nBUILDING ARTICLES CONTAINER"
docker build -t arkanipro/cnd-articles ../../articles-service
echo -e "\nBUILDING COMMNENTS CONTAINER"
docker build -t arkanipro/cnd-comments ../../comments-service
echo -e "\nBUILDING USER CONTAINER"
docker build -t arkanipro/cnd-user ../../user-service

echo -e "\nPUSHING API GATWAY CONTAINER"
docker push arkanipro/cnd-api-gateway
echo -e "\nPUSHING API FETCHING SERVICE CONTAINER"
docker push arkanipro/cnd-api-fetching
echo -e "\nPUSHING ARTICLES CONTAINER"
docker push arkanipro/cnd-articles
echo -e "\nPUSHING COMMENTS CONTAINER"
docker push arkanipro/cnd-comments
echo -e "\nPUSHING USER CONTAINER"
docker push arkanipro/cnd-user

echo -e "\nDeploying APP TO K8S"
kubectl delete -f deployment.yaml
kubectl create -f deployment.yaml

echo -e "\nCREATE SERVICE TO MAKE APP ACCESIBLE"
kubectl delete -f service.yaml 
kubectl create -f service.yaml 

echo -e "\nFIN TRY out GET localhost:30000"