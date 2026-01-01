const defaultErrMsg = "Something went wrong!";

const sendError = (errMsg) => {
    if (!errMsg) {
        errMsg = defaultErrMsg;
    }

    const err = new Error(errMsg);
    throw err;
};

export {
    sendError
};