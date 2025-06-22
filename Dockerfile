FROM node:alpine

WORKDIR /app

COPY package.json /app

RUN npm install

COPY . .

RUN ["node","index.js"]