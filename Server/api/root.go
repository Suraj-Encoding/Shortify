package api

import (
	"fmt"
	"net/http"
)

// # Root 'Page': ["/"]
// # Note: Send the "welcome" message to the 'client'
func RootPage(w http.ResponseWriter, r *http.Request) {
	const welcomeMessage string = "🚀 Welcome To Shortify - Modern URL Shortener 🚀"
	fmt.Fprintf(w, welcomeMessage)
}
