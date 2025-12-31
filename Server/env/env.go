package env

import (
	"fmt"
	"os"

	"github.com/joho/godotenv"
)

// # Load 'Env'
func LoadEnv() {
	var err error

	// # Load the 'Env' variables
	err = godotenv.Load()
	if err != nil {
		// # Load 'Env' Error
		fmt.Println("🚫 Load Env Error:", err)
	}
}

// # Get 'Env'
func GetEnv(envKey string) string {
	// # Get the 'Env' value
	envValue := os.Getenv(envKey)
	if envValue == "" {
		// # Get 'Env' Error
		fmt.Println("🚫 Get Env Error:", envKey)
	}

	// # Return the 'Env' value
	return envValue
}
