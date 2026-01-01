import { sendError } from "./error";

const API_BASE_URL = process.env.NEXT_PUBLIC_SHORTIFY_SERVER_BASE_URL;

if (!API_BASE_URL) {
    const errMsg = "MISSING.API_BASE_URL";
    console.error(errMsg);
    sendError(errMsg);
}

// # "User" API #
const userAPI = {
    // # Get 'User'
    async getUser(clerkUserId) {
        const apiEndpoint = `${API_BASE_URL}/user/?clerk_user_id=${clerkUserId}`;
        const apiRequestOptions = {
            method: 'GET'
        }

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const data = await response.json();

        if (!response.ok) {
            console.error(data.message || 'Client: Failed to get the user');
            sendError(data.message);
        }

        return data;
    },

    // # Update 'Username'
    async updateUsername(clerkUserId, username) {
        const apiEndpoint = `${API_BASE_URL}/user/username?clerk_user_id=${clerkUserId}&username=${encodeURIComponent(username)}`;
        const apiRequestOptions = {
            method: 'PUT'
        };

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const res = await response.json();

        if (!response.ok) {
            console.error(res.message || 'Client: Failed to the update username');
            sendError(res.message);
        }

        return res;
    }
};

// # "Link" API #
const linkAPI = {
    // # Create 'Link'
    async createLink(clerkUserId, data) {
        apiEndpoint = `${API_BASE_URL}/link/?clerk_user_id=${clerkUserId}`;
        apiRequestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                {
                    data
                }
            )
        };

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const res = await response.json();

        if (!response.ok) {
            console.error(res.message || 'Client: Failed to create the link');
            sendError(res.message);
        }

        return res;
    },

    // # Update 'Link'
    async updateLink(clerkUserId, linkId, data) {
        const apiEndpoint = `${API_BASE_URL}/link/?clerk_user_id=${clerkUserId}&link_id=${linkId}`;
        const apiRequestOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                {
                    data
                }
            )
        };

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const res = await response.json();

        if (!response.ok) {
            console.error(res.message || 'Client: Failed to update the link');
            sendError(res.message);
        }

        return res;
    },

    // # Delete 'Link'
    async deleteLink(linkId) {
        const apiEndpoint = `${API_BASE_URL}/link/?link_id=${linkId}`;
        const apiRequestOptions = {
            method: 'DELETE'
        }

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const res = await response.json();

        if (!response.ok) {
            console.error(res.message || 'Client: Failed to delete the link');
            sendError(res.message);
        }

        return res;
    },

    // # Get 'Link'
    async getLink(linkId) {
        const apiEndpoint = `${API_BASE_URL}/link/?link_id=${linkId}`;
        const apiRequestOptions = {
            method: 'GET'
        }

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const data = await response.json();

        if (!response.ok) {
            console.error(data.message || 'Client: Failed to get the link');
            sendError(data.message);
        }

        return data;
    },

    // # Get 'Links'
    async getLinks(clerkUserId) {
        const apiEndpoint = `${API_BASE_URL}/link/list?clerk_user_id=${clerkUserId}`;
        const apiRequestOptions = {
            method: 'GET'
        }

        const response = await fetch(
            apiEndpoint,
            apiRequestOptions
        );

        const data = await response.json();

        if (!response.ok) {
            console.error(data.message || 'Client: Failed to get the links');
            sendError(data.message);
        }

        return data;
    },
};

export {
    userAPI,
    linkAPI
};