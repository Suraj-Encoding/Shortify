package schema

// # App 'Response' Schema
type AppResponse struct {
	Success bool `json:"success"` // # "true"
	Payload any  `json:"payload"`
}

// # App 'Error' Schema
type AppError struct {
	Success bool `json:"success"` // # "false"
	Error   any  `json:"error"`
}
