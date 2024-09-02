package main

import (
	"fmt"
	"net/http"
	"shortify/handlers"
	"shortify/env"
)

func main() {
	// # Load Env Variables
	env.LoadEnv()

	// # Get Port
	port := env.GetEnv("PORT", "3000")

	// # Routes
	http.HandleFunc("/", handlers.RootPageURL)
	http.HandleFunc("/shorten", handlers.ShortURLHandler)
	http.HandleFunc("/redirect/", handlers.RedirectURLHandler)

	// # Start Server
	fmt.Printf("🚀 Server is running...\n")
	fmt.Printf("🔗 Link : http://localhost:%s\n", port)
	err := http.ListenAndServe(":"+port, nil)
	if err != nil {
		// # Start Server Error
		fmt.Printf("🚫 Start Server Error : %v\n", err)
	}
}
