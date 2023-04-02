# images: (optional)

- docker compose file, or virtual machine runtime configuration settings in order to create necessary runtime images

# image creation and deployment

- ./Dockerfile.normal-services
  - Dockerfile to create all Mikroservices expect the user-service
- ./Dockerfile.user-service
  - Dockerfile to create the user-mikroservice
- ./deployment.yaml
  - k8s deployment yaml to configure the k8s pod
- ./service.yaml
  - k8s service yaml to create an k8s service which exposes the backend to the world
- ./deployAll.sh
  - bash deploy script
    - to create all Docker-containers
    - to push the Docker-containers to DockerHub
    - to create a k8s deployment
    - to create a k8s service

# usage

before running the **deployAll.sh** script you should ensure that you have the right to push all the docker-containers in their specific DockerHub Repositories.

Alternatively you can just run the k8s steps as the Containers are already in DockerHub.

If you want to create all from scratch change the Docker-container tags to suit your DockerHub Account in the **deployAll.sh** script, as well as in the **deployment.yaml**.

Also do not run the script in here!
Please run the **deployAll.sh** from the directory: **_"src/backend/deploy/k8s"_** to ensure that the relative paths are working.
