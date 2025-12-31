package schema

type ClerkUser struct {
	// # "Create" & "Update" #
	ID        string  `json:"id"`
	FirstName *string `json:"first_name"`
	LastName  *string `json:"last_name"`
	Username  *string `json:"username"`
	ImageURL  *string `json:"image_url"`

	// # "Delete" #
	Deleted bool   `json:"deleted"`
	Object  string `json:"object"`
}

type ClerkUserRequest struct {
	InstanceID string     `json:"instance_id"`
	Object     string     `json:"object"`
	Type       string     `json:"type"`
	Data       *ClerkUser `json:"data"`
}
