FROM node:14.15.5
WORKDIR /code
RUN npm install -g ember-cli@3.25.0
COPY package.json /code
RUN npm install
COPY . /code
EXPOSE 4200
CMD ["ember", "serve"]
