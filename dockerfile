FROM node:latest AS NODE
ADD . /app
WORKDIR /app
ENV NODE_ENV=development
RUN npm install -g pnpm
RUN npm install -g @vue/cli
RUN pnpm install
RUN pnpm cross-env NODE_ENV=production vue-cli-service build

FROM caddy:latest AS CADDY
EXPOSE 80
COPY --from=NODE /app/dist/ /srv/
ENTRYPOINT ["caddy", "file-server", "--listen", ":80", "--root", "/srv"]
