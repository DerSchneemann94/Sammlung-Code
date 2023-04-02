FROM node:alpine as production

ARG NODE_ENV=production
ENV NODE_ENV=${NODE_ENV}

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build

EXPOSE 3000

CMD ["node", "dist/main"]
# #Base this Dockerimage on alpine
# #alpine is lightweight Linux with node installed

# FROM node:alpine AS development

# #Set working dir for container
# WORKDIR /usr/src/app

# #copy package-lock and package json in Working dir
# COPY package*.json ./

# #install all dependecies
# RUN npm install

# #copy all files of the project to the working dir
# COPY . . 

# #build app
# RUN npm run build

# FROM node:alpine as production

# #set an environment variable
# ARG NODE_ENV=production
# ENV NODE_ENV=${NODE_ENV}

# WORKDIR /usr/src/app

# COPY package*.json ./

# #install only core dependencies
# RUN npm install --only=prod

# COPY . .

# #get build from development stage
# COPY --from=development /usr/src/app/dist ./dist

# #allow port 2999 to be accessable
# EXPOSE 2999

# #run the app
# CMD ["node", "dist/main"]

# #create Dokcer image like this:
# #docker login
# #docker build -t arkanipro/nestjs-k8s . 
# #   // the -t(Tag) should match with the container image in the deploment.yaml for cubernetes and your repository on DockerHub
# # You can then push the Docker Image to an existing DockerHub Repo like this:
# # docker push arkanipro/nestjs-k8s
# # "arkanipro/nestjs-k8s" is the exact name of the docker hub repository in this case

# # Then you can deploy the container with the deployment.yaml in the k8s folder
# # make sure you have Kubernetes activated in your DockerDesktop
# # move to the k8s folder in the console
# # cd ...../k8s
# # create the deployment:
# # kubectl create -f deployment.yaml
# # then you should see with "kubectl get pods" that your pods are running
# # with "kubectl logs ${nestjs-k8s-Identifier}" you should see that the pods are running properly

# # then create a service so that the pods are accessable from the outside aswell as loadbalanced by kubernetes
# # run:
# # kubectl create -f service.yaml 
# # you can view your service aswell as the port with the following command:
# # kubectl get service
# # my port would be 32486 //nestjs-k8s   NodePort    10.99.47.50   <none>        2999:32486/TCP   48s

# # remove all services with 
# # kubectl delete --all services

# # remove something via yaml:
# # kubectl delete -f service.yaml 