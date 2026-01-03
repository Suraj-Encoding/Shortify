'use client';

import { useRef, useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from '@/components/ui/dialog';

// # 'Update Username' Dialog Component #
const UpdateUsernameDialog = ({ open, onOpenChange, currentUsername, onUpdate }) => {
    const [username, setUsername] = useState('');
    const inputRef = useRef(null);

    useEffect(() => {
        if (open) {
            setUsername(currentUsername || '');

            // # Prevent the 'auto-selection' of the 'username' (input text) by setting the 'cursor' at the 'end' after the 'render'
            requestAnimationFrame(() => {
                if (inputRef.current) {
                    const len = (currentUsername || '').length;
                    inputRef.current.setSelectionRange(len, len);
                    inputRef.current.focus();
                }
            });
        } else {
            // # Clear the 'username' on dialog 'close'
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
                            Update Username
                        </DialogTitle>
                    </DialogHeader>
                    <div className="space-y-4 mt-4">
                        <Input
                            ref={inputRef}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter new username"
                            className="bg-gray-100 dark:bg-gray-700 dark:text-white dark:border-gray-600"
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

export default UpdateUsernameDialog;
