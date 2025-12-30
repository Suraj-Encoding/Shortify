package main

import (
	"fmt"
	"log"
	"net/http"
	"shortify/env"
	"shortify/router"
	"strings"
)

// # Server "Entry" Point #
func main() {
	var err error

	// # Load the 'Env' variables
	env.LoadEnv()

	// # Get the 'Server' variables from the 'Env'
	serverURI := env.GetEnv("SERVER_URI", "server")
	port := env.GetEnv("PORT", "port")

	// # Build the server 'URI' with the 'port'
	serverURI = strings.Replace(serverURI, "{PORT}", port, 1)

	// # Setup the 'Router'
	router := router.SetupRouter()

	// # Start the 'server'
	fmt.Println("🚀 Server Is Running...")
	fmt.Println("🔗 Link:", serverURI)

	listenAddress := fmt.Sprintf(":%s", port)

	err = http.ListenAndServe(listenAddress, router)
	if err != nil {
		// # Start 'Server' Error
		log.Fatal("🚫 Start Server Error: ", err)
	}
}
