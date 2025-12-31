package api

import (
	"errors"
	"fmt"
	"net/http"
	"shortify/app"
	"shortify/schema"
	"shortify/utils"
	"strings"
)

// # Redirect 'URL'
func RedirectURL(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	// # Get the 'URL Path'
	urlPath := strings.Trim(r.URL.Path, "/")

	urlPathParts := strings.Split(urlPath, "/")
	urlPathPartLength := len(urlPathParts)

	if urlPathPartLength != 2 {
		errMsg = "🚫 Invalid Request To Shortify!"
		err = errors.New(errMsg)
		utils.LogError(err, "API.RedirectURL")
		errRes = schema.Error{
			StatusCode: http.StatusNotFound,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Get the 'username' and 'link slug' from the 'URL Path'
	username := utils.GetTrimmedValue(urlPathParts[0])
	linkSlug := utils.GetTrimmedValue(urlPathParts[1])

	if username == "" || linkSlug == "" {
		errMsg = "🚫 Invalid Request To Shortify!"
		err = errors.New(errMsg)
		utils.LogError(err, "API.RedirectURL")
		errRes = schema.Error{
			StatusCode: http.StatusNotFound,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Get the 'Destination URL'
	destinationURL, err := app.GetDestinationURL(username, linkSlug)
	if err != nil {
		errMsg = err.Error()
		errMsg = fmt.Sprintf("🚫 Invalid Request To Shortify: Failed to get the destination URL: %s", errMsg)
		err = errors.New(errMsg)
		utils.LogError(err, "API.RedirectURL")
		errRes = schema.Error{
			StatusCode: http.StatusInternalServerError,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Redirect to the 'Destination URL'
	http.Redirect(w, r, *destinationURL, http.StatusFound)
}
