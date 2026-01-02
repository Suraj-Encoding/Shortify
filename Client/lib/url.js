const getAPIBaseURL = () => {
    const SERVER_BASE_URL = process.env.NEXT_PUBLIC_SERVER_BASE_URL;
    const SERVER_API_VERSION = process.env.NEXT_PUBLIC_SERVER_API_VERSION;

    if (!SERVER_BASE_URL) {
        let errMsg = "🚫 Client Error: MISSING.SERVER_BASE_URL";
        console.error(errMsg);
        errMsg = "";
        sendError(errMsg);
    }

    if (!SERVER_API_VERSION) {
        let errMsg = "🚫 Client Error: MISSING.SERVER_API_VERSION";
        console.error(errMsg);
        errMsg = "";
        sendError(errMsg);
    }

    const API_BASE_URL = `${SERVER_BASE_URL}${SERVER_API_VERSION}`

    return API_BASE_URL;
};

export {
    getAPIBaseURL
};