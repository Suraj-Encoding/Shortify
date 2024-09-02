package db

import (
	"context"
	"log"
	"shortify/models"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var client *mongo.Client
var urlCollection *mongo.Collection

func init() {
	var err error
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	client, err = mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}

	err = client.Ping(context.TODO(), nil)
	if err != nil {
		log.Fatal(err)
	}

	urlCollection = client.Database("urlshortener").Collection("urls")
}

func SaveURL(url models.URL) error {
	_, err := urlCollection.InsertOne(context.TODO(), url)
	return err
}

func GetURL(id string) (models.URL, error) {
	var url models.URL
	err := urlCollection.FindOne(context.TODO(), map[string]string{"id": id}).Decode(&url)
	if err != nil {
		return models.URL{}, err
	}
	return url, nil
}
