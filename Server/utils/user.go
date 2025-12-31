package utils

// # Get 'Action User'
func GetActionUser(clerkUserID string) string {
	var actionUser string
	if clerkUserID != "" {
		actionUser = clerkUserID
	} else {
		actionUser = "system"
	}
	return actionUser
}
