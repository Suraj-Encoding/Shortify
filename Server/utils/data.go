package utils

import (
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
