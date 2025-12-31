const API_BASE_URL = process.env.NEXT_PUBLIC_SHORTIFY_SERVER_BASE_URL;

export const userAPI = {
    async getUser(clerkUserId) {
        const response = await fetch(`${API_BASE_URL}/user/?clerk_user_id=${clerkUserId}`);
        const data = await response.json();
        if (!response.ok) throw new Error(data.message || 'Failed to fetch user');
        return data;
    },

    async updateUsername(clerkUserId, username) {
        const response = await fetch(
            `${API_BASE_URL}/user/username?clerk_user_id=${clerkUserId}&username=${encodeURIComponent(username)}`,
            { method: 'PUT' }
        );
        const data = await response.json();
        if (!response.ok) throw new Error(data.message || 'Failed to update username');
        return data;
    }
};

export const linkAPI = {
    async getLinks(clerkUserId) {
        const response = await fetch(`${API_BASE_URL}/link/list?clerk_user_id=${clerkUserId}`);
        const data = await response.json();
        if (!response.ok) throw new Error(data.message || 'Failed to fetch links');
        return data;
    },

    async getLink(linkId) {
        const response = await fetch(`${API_BASE_URL}/link/?link_id=${linkId}`);
        const data = await response.json();
        if (!response.ok) throw new Error(data.message || 'Failed to fetch link');
        return data;
    },

    async createLink(clerkUserId, data) {
        const response = await fetch(`${API_BASE_URL}/link/?clerk_user_id=${clerkUserId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ data })
        });
        const result = await response.json();
        if (!response.ok) throw new Error(result.message || 'Failed to create link');
        return result;
    },

    async updateLink(clerkUserId, linkId, data) {
        const response = await fetch(`${API_BASE_URL}/link/?clerk_user_id=${clerkUserId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ data: { ...data, link_id: linkId } })
        });
        const result = await response.json();
        if (!response.ok) throw new Error(result.message || 'Failed to update link');
        return result;
    },

    async deleteLink(linkId) {
        const response = await fetch(`${API_BASE_URL}/link/?link_id=${linkId}`, {
            method: 'DELETE'
        });
        const result = await response.json();
        if (!response.ok) throw new Error(result.message || 'Failed to delete link');
        return result;
    }
};
