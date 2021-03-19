FROM node:14.16.0-alpine AS react_build
WORKDIR /app
COPY . /app/
RUN npm install
RUN npm run build

FROM nginx:1.19-alpine
COPY --from=react_build /app/build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx","-g","daemon off;"]
