package api

import (
	"encoding/json"
	"errors"
	"fmt"
	"net/http"
	"shortify/app"
	"shortify/schema"
	"shortify/utils"

	"go.mongodb.org/mongo-driver/bson"
)

// # Handle 'Clerk' User 'Webhook'
func HandleClerkUserWebhook(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	var res *string

	contentLength := r.ContentLength
	if contentLength == 0 {
		errMsg = "Empty request body provided"
		errRes = schema.Error{
			StatusCode: 400,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	defer r.Body.Close()

	var clerkUserForm schema.ClerkUserRequest

	decoder := json.NewDecoder(r.Body)

	err = decoder.Decode(&clerkUserForm)
	if err != nil {
		errMsg = fmt.Sprintf("Failed to decode the clerk user request body: %s", err.Error())
		errRes = schema.Error{
			StatusCode: 400,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	var clerkUser *schema.ClerkUser
	if clerkUserForm.Data == nil {
		errMsg = "Empty clerk user request data found"
		errRes = schema.Error{
			StatusCode: 400,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	} else {
		clerkUser = clerkUserForm.Data
	}

	switch clerkUserForm.Type {
	case "user.created":
		{
			res, err = app.CreateUser(clerkUser)
		}
	case "user.updated":
		{
			res, err = app.UpdateUser(clerkUser)
		}
	case "user.deleted":
		{
			res, err = app.DeleteUser(clerkUser)
		}
	default:
		{
			errMsg = fmt.Sprintf("Invalid clerk user webhook type provided: %s", clerkUserForm.Type)
			err = errors.New(errMsg)
		}
	}

	if err != nil {
		utils.LogError(err, "API.HandleClerkUserWebhook")
		errMsg = err.Error()
		errRes = schema.Error{
			StatusCode: 500,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	utils.SetAppResponse(w, res)
}

// # Update 'Username'
func UpdateUsername(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	// # Get the 'clerk user ID' from the 'query params'
	clerkUserID := r.URL.Query().Get("clerk_user_id")
	clerkUserID = utils.GetStringValue(&clerkUserID)
	if clerkUserID == "" {
		errMsg = "Empty clerk user ID provided"
		errRes = schema.Error{
			StatusCode: 400,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Get the 'username' from the 'query params'
	username := r.URL.Query().Get("username")
	username = utils.GetStringValue(&username)
	if username == "" {
		errMsg = "Empty username provided"
		errRes = schema.Error{
			StatusCode: 400,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Update the 'username'
	res, err := app.UpdateUsername(clerkUserID, username)
	if err != nil {
		utils.LogError(err, "API.UpdateUsername")
		errMsg = err.Error()
		errRes = schema.Error{
			StatusCode: 500,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	utils.SetAppResponse(w, res)
}

// # Get 'User'
func GetUser(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	// # Get the 'clerk user ID' from the 'query params'
	clerkUserID := r.URL.Query().Get("clerk_user_id")
	clerkUserID = utils.GetStringValue(&clerkUserID)
	if clerkUserID == "" {
		errMsg = "Empty clerk user ID provided"
		errRes = schema.Error{
			StatusCode: 400,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Base 'Filter'
	filter := bson.M{
		"clerk_user_id": clerkUserID,
	}

	// # Get the 'user'
	user, err := app.GetUser(filter)
	if err != nil {
		utils.LogError(err, "API.GetUser")
		errMsg = err.Error()
		errRes = schema.Error{
			StatusCode: 500,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	utils.SetAppResponse(w, user)
}

// # Get 'Users'
func GetUsers(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	// # Get the 'users'
	users, err := app.GetUsers()
	if err != nil {
		utils.LogError(err, "API.GetUsers")
		errMsg = err.Error()
		errRes = schema.Error{
			StatusCode: 500,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	utils.SetAppResponse(w, users)
}
