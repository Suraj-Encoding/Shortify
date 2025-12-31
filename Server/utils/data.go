package utils

import (
	"shortify/model"
	"strings"
)

// # Get 'String Value'
func GetStringValue(value *string) string {
	var valueStr string
	if value != nil {
		valueStr = strings.TrimSpace(*value)
	} else {
		valueStr = ""
	}
	return valueStr
}

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
