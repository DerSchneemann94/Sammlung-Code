FROM node:16.14.2

ENV NODE_ENV=production

WORKDIR /app

COPY ["package.json" ,"package-lock.json" ,"./"]

RUN npm install --production

COPY . .

RUN npm run build

EXPOSE 3000

CMD ["node", "dist/main"]