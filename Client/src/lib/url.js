// # Get 'Env' Variables
const SERVER_BASE_URL = import.meta.env.VITE_SERVER_BASE_URL;
const SERVER_API_VERSION = import.meta.env.VITE_SERVER_API_VERSION;

let errMsg = "";

if (!SERVER_BASE_URL) {
    errMsg = "🚫 Client Error: MISSING.SERVER_BASE_URL";
    console.error(errMsg);
    errMsg = "";
}

if (!SERVER_API_VERSION) {
    errMsg = "🚫 Client Error: MISSING.SERVER_API_VERSION";
    console.error(errMsg);
    errMsg = "";
}

// # Get Server 'Base URL' 
const getServerBaseURL = () => {
    return SERVER_BASE_URL;
};

// # Get API 'Base URL' 
const getAPIBaseURL = () => {
    const API_BASE_URL = `${SERVER_BASE_URL}${SERVER_API_VERSION}`;
    return API_BASE_URL;
};

export {
    getServerBaseURL,
    getAPIBaseURL
};