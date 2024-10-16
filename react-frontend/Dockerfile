# Stage 1: Install dependencies
FROM node:20 AS builder
WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn install

# Stage 2: Copy dependencies and run the project
FROM node:20-slim
WORKDIR /app
COPY --from=builder /app/node_modules /node_modules
COPY . .
CMD ["yarn", "start"]
EXPOSE 3000