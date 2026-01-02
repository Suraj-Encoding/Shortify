'use client';

import { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from '@/components/ui/dialog';

const EditUsernameDialog = ({ open, onOpenChange, currentUsername, onUpdate }) => {
    const [username, setUsername] = useState('');

    useEffect(() => {
        if (open) {
            setUsername(currentUsername || '');
        } else {
            // # Reset the 'username' on dialog 'close'
            setUsername('');
        }
    }, [currentUsername, open]);

    const handleUpdate = () => {
        onUpdate(username);
    };

    return (
        <>
            <Dialog open={open} onOpenChange={onOpenChange}>
                <DialogContent className="dark:bg-gray-800 dark:border-gray-700">
                    <DialogHeader>
                        <DialogTitle className="dark:text-white">
                            Edit Username
                        </DialogTitle>
                    </DialogHeader>
                    <div className="space-y-4 mt-4">
                        <Input
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter username"
                            className="dark:bg-gray-700 dark:text-white dark:border-gray-600"
                        />
                        <Button onClick={handleUpdate} className="w-full bg-black hover:bg-gray-800 dark:bg-white dark:text-black dark:hover:bg-gray-200">
                            Save Changes
                        </Button>
                    </div>
                </DialogContent>
            </Dialog>
        </>
    );
};

export default EditUsernameDialog;
