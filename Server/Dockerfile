## Dockerfile : For Go Application ##

## Stage-I: Build the Go application 

# Start from the latest golang base image (alpine) 
FROM golang:1.23.0-alpine3.20 AS build

# Make new directory as app
RUN mkdir /app

# Copy the current directory contents into the container at /app
ADD . /app

# Set the Current Working Directory inside the container
WORKDIR /app

# Copy go.mod and go.sum files to the workspace
COPY go.mod go.sum ./

# Download all the dependencies using `tidy` command
RUN go mod tidy 

# Copy the rest of the application code
COPY . .

## Stage-II: Run the Go app 

# Build the Go app
RUN go build -o main .

# Expose port 3000 to the outside world
EXPOSE 3000

# Run the binary program
CMD ["/app/main"]
