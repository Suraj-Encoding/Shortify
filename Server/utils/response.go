package utils

import (
	"encoding/json"
	"net/http"
	"shortify/schema"
)

// # Set 'App Response'
func SetAppResponse(w http.ResponseWriter, response any) {
	w.WriteHeader(http.StatusOK) // # "200" #

	encoder := json.NewEncoder(w)

	res := &schema.AppResponse{
		Success: true,
		Payload: response,
	}

	encoder.Encode(res)
}

// # Set 'App Error'
func SetAppError(w http.ResponseWriter, errMsg string, statusCode int) {
	w.WriteHeader(statusCode) // # "error status code" #

	encoder := json.NewEncoder(w)

	err := &schema.AppError{
		Success: false,
		Error:   errMsg,
	}

	encoder.Encode(err)
}
