package schema

type ClerkUser struct {
	// # "Create" & "Update" #
	ID        string  `json:"id"`
	FirstName *string `json:"first_name"`
	LastName  *string `json:"last_name"`
	Username  *string `json:"username"`
	ImageURL  *string `json:"image_url"`

	// # "Delete" #
	Deleted bool   `json:"deleted"` // # "false" #
	Object  string `json:"object"`  // # "user" #
}

type ClerkUserRequest struct {
	InstanceID string     `json:"instance_id"`
	Object     string     `json:"object"` // # "event" #
	Type       string     `json:"type"`   // # "event type" #
	Data       *ClerkUser `json:"data"`   // # "clerk user" #
}
