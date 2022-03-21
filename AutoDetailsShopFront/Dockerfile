FROM node:latest
WORKDIR /usr/src/app/frontend
COPY package.json ./
RUN npm install
RUN npm install --save-dev @types/jquery
RUN npm install jquery --save
COPY . .
EXPOSE 4200 49153
CMD npm run start