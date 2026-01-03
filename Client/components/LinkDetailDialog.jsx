'use client';

import { useState, useEffect } from 'react';
import { Copy } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import DeleteLinkDialog from './DeleteLinkDialog';

// # 'Link Detail Dialog' Component #
const LinkDetailDialog = ({ link, open, onOpenChange, onUpdate, onDelete }) => {
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        destination_url: '',
        slug: '',
    });

    const [isDeleteOpen, setIsDeleteOpen] = useState(false);

    useEffect(() => {
        if (link) {
            setFormData({
                title: link.title || '',
                description: link.description || '',
                destination_url: link.destination_url || '',
                slug: link.slug || '',
            });
        }
    }, [link]);

    const handleUpdate = () => {
        onUpdate(link._id, formData);
    };

    // # Copy To 'Clipboard'
    const copyToClipboard = (text) => {
        navigator.clipboard.writeText(text);
    };

    if (!link) return null;

    return (
        <>
            <Dialog open={open} onOpenChange={onOpenChange}>
                <DialogContent className="max-w-2xl dark:bg-gray-800 dark:border-gray-700">
                    <DialogHeader>
                        <DialogTitle className="dark:text-white">
                            Link Details
                        </DialogTitle>
                    </DialogHeader>
                    <div className="space-y-4 mt-4">
                        <div>
                            <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                Title
                            </label>
                            <Input
                                value={formData.title}
                                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                                placeholder="My Link"
                                className="dark:bg-gray-700 dark:text-white dark:border-gray-600"
                            />
                        </div>
                        <div>
                            <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                Description
                            </label>
                            <Textarea
                                value={formData.description}
                                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                                placeholder="Link Description"
                                className="dark:bg-gray-700 dark:text-white dark:border-gray-600"
                            />
                        </div>
                        <div>
                            <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                Destination URL <span className="text-red-500"> * </span>
                            </label>
                            <Input
                                value={formData.destination_url}
                                onChange={(e) => setFormData({ ...formData, destination_url: e.target.value })}
                                placeholder="https://example.com"
                                className="dark:bg-gray-700 dark:text-white dark:border-gray-600"
                            />
                        </div>
                        <div>
                            <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                Slug <span className="text-red-500"> * </span>
                            </label>
                            <Input
                                value={formData.slug}
                                onChange={(e) => setFormData({ ...formData, slug: e.target.value })}
                                placeholder="custom-slug"
                                className="dark:bg-gray-700 dark:text-white dark:border-gray-600"
                            />
                        </div>
                        <div>
                            <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                Short URL
                            </label>
                            <div className="flex items-center space-x-2">
                                <Input value={link.short_url} readOnly className="flex-1 dark:bg-gray-700 dark:text-white dark:border-gray-600" />
                                <Button
                                    onClick={() => copyToClipboard(link.short_url)}
                                    className="bg-black hover:bg-gray-800 dark:bg-white dark:text-black dark:hover:bg-gray-200"
                                >
                                    <Copy className="w-4 h-4" />
                                </Button>
                            </div>
                        </div>
                        <div className="grid grid-cols-2 gap-4">
                            <div>
                                <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                    Created
                                </label>
                                <Input value={new Date(link.created_at).toLocaleDateString()} readOnly className="dark:bg-gray-700 dark:text-white dark:border-gray-600" />
                            </div>
                            {link.updated_at && (
                                <div>
                                    <label className="text-sm font-medium mb-1 block dark:text-gray-200">
                                        Updated
                                    </label>
                                    <Input value={new Date(link.updated_at).toLocaleDateString()} readOnly className="dark:bg-gray-700 dark:text-white dark:border-gray-600" />
                                </div>
                            )}
                        </div>
                        <div className="flex space-x-2 pt-4">
                            <Button onClick={handleUpdate} className="flex-1 bg-black hover:bg-gray-800 dark:bg-white dark:text-black dark:hover:bg-gray-200">
                                Save Changes
                            </Button>
                            <Button
                                onClick={() => setIsDeleteOpen(true)}
                                variant="outline"
                                className="border-red-600 text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20"
                            >
                                Delete Link
                            </Button>
                        </div>
                    </div>
                </DialogContent>
            </Dialog>

            <DeleteLinkDialog
                link={link}
                open={isDeleteOpen}
                onOpenChange={setIsDeleteOpen}
                onConfirmDelete={(id) => {
                    onDelete(id);
                    onOpenChange(false);
                }}
            />
        </>
    );
};

export default LinkDetailDialog;