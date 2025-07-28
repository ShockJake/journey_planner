FROM node:22-alpine

RUN mkdir /app
COPY ./build /app

EXPOSE 3000

ENTRYPOINT [ "node", "/app" ]
