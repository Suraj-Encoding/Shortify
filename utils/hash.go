package utils

import (
	"crypto/md5"
	"encoding/hex"
	"shortify/db"
	"shortify/models"
	"time"
)

func GenerateShortURL(originalURL string) string {
	hasher := md5.New()
	hasher.Write([]byte(originalURL))
	hash := hex.EncodeToString(hasher.Sum(nil))
	return hash[:8]
}

func CreateURL(originalURL string) string {
	shortURL := GenerateShortURL(originalURL)
	id := shortURL
	url := models.URL{
		ID:           id,
		OriginalURL:  originalURL,
		ShortURL:     shortURL,
		CreationDate: time.Now(),
	}
	err := db.SaveURL(url)
	if err != nil {
		// Handle error (log it or return it)
	}
	return shortURL
}
