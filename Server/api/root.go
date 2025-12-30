package api

import (
	"fmt"
	"net/http"
)

// # Root 'Route': ["/"]
func RootRoute(w http.ResponseWriter, r *http.Request) {
	// # Send the 'welcome' message to the 'client'
	fmt.Fprintf(w, "🚀 Welcome To Shortify! 🚀")
}
