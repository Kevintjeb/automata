FROM node:9.11.1-alpine

# Create app directory
WORKDIR /app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./

RUN npm install

RUN apk add --update --no-cache \
           graphviz \
           ttf-freefont

# Bundle app source
COPY . .

EXPOSE 3001
CMD [ "npm", "start" ]
