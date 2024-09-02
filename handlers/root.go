package handlers

import (
	"fmt"
	"net/http"
)

func RootPageURL(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "⚙️ Welcome to the Shortify ⚙️")
}
