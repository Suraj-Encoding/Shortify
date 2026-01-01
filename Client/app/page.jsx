'use client';

import { useState, useEffect } from 'react';
import { useUser } from '@clerk/nextjs';
import { Plus } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card, CardContent } from '../components/ui/card';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import LinkCard from '../components/LinkCard';
import CreateLinkDialog from '../components/CreateLinkDialog';
import LinkDetailDialog from '../components/LinkDetailDialog';
import EditUsernameDialog from '../components/EditUsernameDialog';
import Toast from '../components/Toast';
import { userAPI, linkAPI } from '@/lib/api';
import { getSuccessMsg } from '@/lib/success';

export default function Dashboard() {
  const { user, isLoaded } = useUser();
  const [links, setLinks] = useState([]);
  const [selectedLink, setSelectedLink] = useState(null);
  const [userData, setUserData] = useState(null);
  const [isCreateOpen, setIsCreateOpen] = useState(false);
  const [isEditUserOpen, setIsEditUserOpen] = useState(false);
  const [isDetailOpen, setIsDetailOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [toast, setToast] = useState(null);

  useEffect(() => {
    if (user) {
      fetchUser();
      fetchLinks();
    }
  }, [user]);

  const showToast = (message, type) => {
    setToast({ message, type });
  };

  const fetchUser = async () => {
    try {
      const response = await userAPI.getUser(user.id);
      if (response.success) {
        setUserData(response.payload);
      }
    } catch (err) {
      showToast(err.message, 'error');
    }
  };

  const fetchLinks = async () => {
    setLoading(true);
    try {
      const response = await linkAPI.getLinks(user.id);
      if (response.success) {
        setLinks(response.payload);
      }
    } catch (err) {
      showToast(err.message, 'error');
    } finally {
      setLoading(false);
    }
  };

  const fetchLinkDetails = async (link) => {
    try {
      const response = await linkAPI.getLink(link._id);
      if (response.success) {
        setSelectedLink(response.payload);
        setIsDetailOpen(true);
      }
    } catch (err) {
      showToast(err.message, 'error');
    }
  };

  const createLink = async (data) => {
    try {
      const response = await linkAPI.createLink(user.id, data);
      if (response.success) {
        const successMsg = getSuccessMsg(response.payload);
        showToast(successMsg, 'success');
        setIsCreateOpen(false);
        fetchLinks();
      }
    } catch (err) {
      showToast(err.message, 'error');
      setIsCreateOpen(false);
    }
  };

  const updateLink = async (linkId, data) => {
    try {
      const response = await linkAPI.updateLink(user.id, linkId, data);
      if (response.success) {
        const successMsg = getSuccessMsg(response.payload);
        showToast(successMsg, 'success');
        setIsDetailOpen(false);
        fetchLinks();
      }
    } catch (err) {
      showToast(err.message, 'error');
      setIsDetailOpen(false);
    }
  };

  const deleteLink = async (linkId) => {
    if (!confirm('Are you sure you want to delete this link?')) return;
    try {
      const response = await linkAPI.deleteLink(linkId);
      if (response.success) {
        const successMsg = getSuccessMsg(response.payload);
        showToast(successMsg, 'success');
        fetchLinks();
      }
    } catch (err) {
      showToast(err.message, 'error');
      fetchLinks();
    }
  };

  const updateUsername = async (username) => {
    try {
      const response = await userAPI.updateUsername(user.id, username);
      if (response.success) {
        const successMsg = getSuccessMsg(response.payload);
        showToast(successMsg, 'success');
        setIsEditUserOpen(false);
        fetchUser();
      }
    } catch (err) {
      showToast(err.message, 'error');
      setIsEditUserOpen(false);
    }
  };

  const copyToClipboard = (text) => {
    navigator.clipboard.writeText(text);
    showToast('Copied to clipboard!', 'success');
  };

  if (!isLoaded) {
    return (
      <div className="min-h-screen flex items-center justify-center dark:bg-gray-900">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-black dark:border-white"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800 flex flex-col transition-colors">
      <Navbar
        username={userData?.username || user?.username || 'User'}
        onEditUsername={() => setIsEditUserOpen(true)}
      />

      <main className="flex-1 max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white">Link Management</h1>
            <p className="text-gray-600 dark:text-gray-300 mt-1">Create and manage your shortened URLs</p>
          </div>
          <CreateLinkDialog
            open={isCreateOpen}
            onOpenChange={setIsCreateOpen}
            onCreate={createLink}
          />
        </div>

        {loading ? (
          <div className="text-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-black dark:border-white mx-auto"></div>
          </div>
        ) : !links || links.length === 0 ? (
          <Card className="text-center py-12 dark:bg-gray-800 dark:border-gray-700">
            <CardContent>
              <svg className="w-16 h-16 text-gray-400 dark:text-gray-500 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13.828 10.172a4 4 0 00-5.656 0l-4 4a4 4 0 105.656 5.656l1.102-1.101m-.758-4.899a4 4 0 005.656 0l4-4a4 4 0 00-5.656-5.656l-1.1 1.1" />
              </svg>
              <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2">No links yet</h3>
              <p className="text-gray-600 dark:text-gray-400 mb-4">Create your first shortened URL to get started</p>
              <Button onClick={() => setIsCreateOpen(true)} className="bg-black hover:bg-gray-800 dark:bg-white dark:text-black dark:hover:bg-gray-200">
                <Plus className="w-4 h-4 mr-2" />
                Create First Link
              </Button>
            </CardContent>
          </Card>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {links.map((link) => (
              <LinkCard
                key={link._id}
                link={link}
                onClick={fetchLinkDetails}
                onDelete={deleteLink}
                onCopy={copyToClipboard}
              />
            ))}
          </div>
        )}

        <LinkDetailDialog
          link={selectedLink}
          open={isDetailOpen}
          onOpenChange={setIsDetailOpen}
          onUpdate={updateLink}
          onDelete={deleteLink}
        />
        <EditUsernameDialog
          open={isEditUserOpen}
          onOpenChange={setIsEditUserOpen}
          currentUsername={userData?.username || user?.username}
          onUpdate={updateUsername}
        />
      </main>

      <Footer />

      {toast && (
        <Toast
          message={toast.message}
          type={toast.type}
          onClose={() => setToast(null)}
        />
      )}
    </div>
  );
}
