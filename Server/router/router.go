package router

import (
	"fmt"
	"shortify/api"

	"github.com/gorilla/mux"
)

// # Setup 'Router'
func SetupRouter() *mux.Router {
	// # Get the new 'mux' router 'instance'
	router := mux.NewRouter()

	// # Root 'Route': Get the server 'welcome' message: ["/"]
	router.HandleFunc("/", api.RootRoute).Methods("GET")

	// # API 'Base Path'
	APIBasePath := "/api/v1"

	// # 'API' Version '1' Router
	APIV1 := router.PathPrefix(APIBasePath).Subrouter()

	// # 'API' Version '1' Sub 'Routers'
	WebhookV1 := APIV1.PathPrefix("/webhook").Subrouter()

	// # 'Webhook' Routes
	fmt.Println(WebhookV1)

	// # Return the 'configured' mux 'router' instance
	return router
}
