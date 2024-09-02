package utils

import (
	"log"
	"crypto/md5"
	"encoding/hex"
	"shortify/db"
	"shortify/models"
)

// # GenerateShortURL : Generate a short URL from the original URL
func GenerateShortURL(originalURL string) string {
	hasher := md5.New()
	hasher.Write([]byte(originalURL))
	hash := hex.EncodeToString(hasher.Sum(nil))
	return hash[:6]
}

// # CreateURL : Create a new URL in the database
func CreateURL(originalURL string) string {
	shortURL := GenerateShortURL(originalURL)
	id := shortURL
	url := models.URL{
		ID:           id,
		OriginalURL:  originalURL,
		ShortURL:     shortURL,
	}
	err := db.SaveURL(url)
	if err != nil {
		log.Fatal("🚫 Saving URL Error: ", err)
	}
	return shortURL
}
