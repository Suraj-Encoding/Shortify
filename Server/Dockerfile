## Dockerfile : For Go Application ##

## Stage 1: Build the Go application

# Start from the latest golang base image (alpine)
FROM golang:1.23.0-alpine3.20 AS build

# Set `app` as a CWD inside the container
WORKDIR /app

# Copy go.mod and go.sum files first to leverage Docker layer caching
COPY go.mod go.sum ./

# Download all dependencies using tidy command
RUN go mod tidy

# Copy the rest of the application code
COPY . .

# Build the Go app
RUN go build -o main .

## Stage 2: Run the Go application

# Use a minimal base image to keep the image size small
FROM alpine:3.20

# Set `app` as a CWD inside the container
WORKDIR /app

# Expose port 3000 to the outside world
EXPOSE 3000

# Run the binary program
CMD ["/app/main"]