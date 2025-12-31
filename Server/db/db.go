package db

import (
	"context"
	"fmt"
	"log"
	"os"
	env "shortify/env"
	"strings"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// # MongoDB 'Variables'
var client *mongo.Client
var database *mongo.Database
var collection *mongo.Collection

// # Initialize 'MongoDB' Connection
func init() {
	ctx := context.TODO()

	var err error

	// # Get the 'DB' variables from the 'Env'
	dbName := env.GetEnv("DB_NAME", "Database")
	dbUser := env.GetEnv("DB_USER", "User")
	dbPass := env.GetEnv("DB_PASS", "Password")

	// # Build MongoDB URI with DB User and DB Password
	mongoURI := os.Getenv("MONGO_URI")
	mongoURI = strings.Replace(mongoURI, "{DB_USER}", dbUser, 1)
	mongoURI = strings.Replace(mongoURI, "{DB_PASS}", dbPass, 1)
	mongoURI = strings.Replace(mongoURI, "{DB_NAME}", dbName, 1)

	// # Connect to the 'MongoDB'
	clientOptions := options.Client().ApplyURI(mongoURI)
	client, err = mongo.Connect(ctx, clientOptions)
	if err != nil {
		// # MongoDB 'Connection' Error
		log.Fatal("🚫 MongoDB Connection Error: ", err)
	}

	// # Get the 'MongoDB' database
	database = client.Database(dbName)

	// # Ping the 'MongoDB' server
	err = client.Ping(ctx, nil)
	if err != nil {
		// # MongoDB 'Ping' Error
		log.Fatal("🚫 MongoDB Ping Error: ", err)
	}

	// # MongoDB 'Connection' Successful
	fmt.Printf("🕸️  %s Server Connected!\n", dbName)
	fmt.Printf("🕸️  %s Database Connected!\n", dbName)
}

// # Get 'MongoDB' Collection
func GetMongoCollection(collectionName string) *mongo.Collection {
	collection = database.Collection(collectionName)
	return collection
}
