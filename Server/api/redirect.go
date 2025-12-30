package api

// # RedirectURLHandler : Redirect to the original URL from the short URL
// func RedirectURLHandler(w http.ResponseWriter, r *http.Request) {
// 	id := r.URL.Path[len("/redirect/"):]
// 	url, err := db.GetURL(id)
// 	if err != nil {
// 		// # Invalid Request
// 		http.Error(w, "🚫 Invalid Request!", http.StatusNotFound)
// 		return
// 	}
// 	http.Redirect(w, r, url.OriginalURL, http.StatusFound)
// }
