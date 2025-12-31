package utils

import "shortify/model"

// # Get 'Action User'
func GetActionUser(user *model.User) string {
	var actionUser string
	if user != nil {
		actionUser = user.ClerkUserID
	} else {
		actionUser = "system"
	}
	return actionUser
}
