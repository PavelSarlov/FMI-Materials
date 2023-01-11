FROM node:16-alpine AS builder

RUN mkdir -p /app/frontend

WORKDIR /app/frontend

RUN npm cache clean --force

COPY package.json package-lock.json ./

RUN npm i

COPY . .

RUN npm run build --omit=dev

FROM nginx:1.23.3-alpine

COPY --from=builder /app/frontend/dist/ /usr/share/nginx/html/
COPY --from=builder /app/frontend/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
