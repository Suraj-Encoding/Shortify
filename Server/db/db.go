package db

import (
	"fmt"
	"log"
	"context"
	"shortify/env"
	"shortify/models"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// # MongoDB Client and Collection Variables 
var client *mongo.Client
var collection *mongo.Collection

// # Initialize MongoDB Connection
func init() {
	// # Load Env Variables
	env.LoadEnv()

	// # Get MongoDB URL, DB Name, and Collection Name
	mongoURI := env.GetEnv("MONGO_URI", "mongodb://localhost:27017")
	dbName := env.GetEnv("DB_NAME", "Shortify")
	collectionName := env.GetEnv("COLLECTION_NAME", "Links")

	// # Connect to MongoDB
	var err error
	clientOptions := options.Client().ApplyURI(mongoURI)
	client, err = mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		// # MongoDB Connection Error
		log.Fatal("🚫 MongoDB Connection Error: ", err)
	}

	// # Ping the MongoDB server
	err = client.Ping(context.TODO(), nil)
	if err != nil {
		// # MongoDB Ping Error
		log.Fatal("🚫 MongoDB Ping Error: ", err)
	}

	// # Set the MongoDB collection
	collection = client.Database(dbName).Collection(collectionName)

	// # MongoDB Connection Successful
    fmt.Printf("🕸️  %s Server Connected!\n", dbName)
    fmt.Printf("🕸️  %s Database Connected!\n", dbName)
}

// # SaveURL : Save a new URL to the MongoDB collection
func SaveURL(url models.URL) error {
	// # Insert URL into the MongoDB collection
	_, err := collection.InsertOne(context.TODO(), url)
	return err
}

// # GetURL : Get a URL from the MongoDB collection
func GetURL(id string) (models.URL, error) {
	var url models.URL
	// # Find URL by ID in the MongoDB collection and Decode it into URL Model Object 
	err := collection.FindOne(context.TODO(), map[string]string{"id": id}).Decode(&url)
	if err != nil {
		// # URL Not Found and Returned Error
		return models.URL{}, err
	}
	// # URL Found and Returned URL
	return url, nil
}
