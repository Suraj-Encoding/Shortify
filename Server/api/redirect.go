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
// # Note: Redirect the 'user' to the 'Destination URL' of the requested 'link'
func RedirectURL(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	// # Get the 'URL Path' of the requested 'link'
	linkURLPath := r.URL.Path
	linkURLPath = strings.Trim(linkURLPath, "/")

	// # Print the 'URL Path' of the requested 'link'
	fmt.Println("🔗 Link URL Path:", linkURLPath)

	linkURLPathParts := strings.Split(linkURLPath, "/")
	linkURLPathPartLength := len(linkURLPathParts)

	if linkURLPathPartLength != 2 {
		errMsg = "🚫 Invalid link requested to shortify: Invalid link URL path found"
		err = errors.New(errMsg)
		utils.LogError(err, "API.RedirectURL")
		errRes = schema.Error{
			StatusCode: http.StatusNotFound,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Get the 'username' and 'link slug' from the 'URL Path' of the requested 'link'
	username := utils.GetTrimmedValue(linkURLPathParts[0])
	linkSlug := utils.GetTrimmedValue(linkURLPathParts[1])

	if username == "" || linkSlug == "" {
		errMsg = "🚫 Invalid link requested to shortify: Empty username or link slug found"
		err = errors.New(errMsg)
		utils.LogError(err, "API.RedirectURL")
		errRes = schema.Error{
			StatusCode: http.StatusNotFound,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Get the 'Destination URL' of the requested 'link'
	destinationURL, err := app.GetDestinationURL(username, linkSlug)
	if err != nil {
		errMsg = err.Error()
		errMsg = fmt.Sprintf("🚫 Invalid link requested to shortify: Failed to get the destination URL of the requested link: %s", errMsg)
		err = errors.New(errMsg)
		utils.LogError(err, "API.RedirectURL")
		errRes = schema.Error{
			StatusCode: http.StatusInternalServerError,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	// # Redirect the 'user' to the 'Destination URL' of the requested 'link'
	http.Redirect(w, r, *destinationURL, http.StatusFound)
}
