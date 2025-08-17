FROM node:24-alpine

RUN mkdir /app
COPY ./package.json ./build ./package-lock.json /app/
RUN cd /app; npm ci --omit dev
EXPOSE 3000

ENTRYPOINT [ "node", "/app" ]
