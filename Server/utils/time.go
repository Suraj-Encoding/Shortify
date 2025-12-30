package utils

import (
	"errors"
	"fmt"
	"time"
)

// # Get 'Current System Time' in 'IST' Timezone
func GetCurrentSystemTimeInIST() (*time.Time, error) {
	// # Error 'Variables'
	var err error
	var errMsg string

	// # Get the 'current system time'
	currentSystemTime := time.Now()

	// # Get the 'IST' timezone
	ISTTimezone := "Asia/Kolkata"

	// # Load the 'IST' timezone 'location'
	ISTTimezoneLocation, err := time.LoadLocation(ISTTimezone)
	if err != nil {
		errMsg = fmt.Sprintf("Failed to load the IST timezone location: %s", err.Error())
		err = errors.New(errMsg)
		return nil, err
	}

	// # Convert the 'current system time' to the 'IST' timezone
	ISTTime := currentSystemTime.In(ISTTimezoneLocation)

	// # Return the 'IST' time
	return &ISTTime, nil
}
