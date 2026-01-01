const defaultSuccessMsg = "Operation Successful!";

const getSuccessMsg = (successMsg) => {
    if (!successMsg) {
        successMsg = defaultSuccessMsg;
    }

    return successMsg;
};

export {
    getSuccessMsg
};