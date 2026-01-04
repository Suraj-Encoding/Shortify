package api

import (
	"errors"
	"fmt"
	"net/http"
	"shortify/schema"
	"shortify/utils"
	"strings"
)

// # Serve 'File': ["/file"]
// # Note: Serve the requested 'file' to the 'client'
func ServeFile(w http.ResponseWriter, r *http.Request) {
	var err error
	var errMsg string
	var errRes schema.Error

	// # Get the 'URL Path' of the requested 'file'
	fileURLPath := r.URL.Path
	fileURLPath = strings.TrimPrefix(fileURLPath, "/file")

	// # Print the 'URL Path' of the requested 'file'
	fmt.Println("🔗 File URL Path:", fileURLPath)

	if fileURLPath == "" {
		errMsg = "🚫 Invalid file requested to shortify!"
		err = errors.New(errMsg)
		utils.LogError(err, "API.ServeFile")
		errRes = schema.Error{
			StatusCode: http.StatusNotFound,
			Message:    errMsg,
		}
		utils.SetAppError(w, &errRes)
		return
	}

	const rootFolderName string = "public"

	filePath := fmt.Sprintf("%s/%s", rootFolderName, fileURLPath)

	http.ServeFile(w, r, filePath)
}
