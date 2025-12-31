package router

import (
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
	UserV1 := APIV1.PathPrefix("/user").Subrouter()

	// # 'User' Routes
	UserV1.HandleFunc("/webhook", api.HandleClerkUserWebhook).Methods("POST")
	UserV1.HandleFunc("/username", api.UpdateUsername).Methods("PUT")
	UserV1.HandleFunc("/", api.GetUser).Methods("GET")
	UserV1.HandleFunc("/all", api.GetUsers).Methods("GET")

	// # Return the 'configured' mux 'router' instance
	return router
}
