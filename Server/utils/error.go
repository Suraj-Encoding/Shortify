package utils

import "fmt"

// # Log 'Error'
func LogError(err error, methodName string) {
	// # Log the 'error'
	if err == nil {
		return
	}
	errMsg := err.Error()
	errMsg = fmt.Sprintf("🚫 Method Name: '%s' | Error Message: '%s'", methodName, errMsg)
	fmt.Println(errMsg)
}
