'use client';

import { UserButton } from '@clerk/nextjs';
import { Edit2 } from 'lucide-react';
import { Button } from '@/components/ui/button';
import ThemeToggle from './ThemeToggle';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import UIData from '@/Interface/constant/ui';
import Tooltip from './Tooltip';

// # 'Navbar' Component #
const Navbar = ({ username, onEditUsername }) => {
    const [theme, setTheme] = useState('light');

    useEffect(() => {
        // # Get the initial 'theme'
        const savedTheme = localStorage.getItem('theme') || 'light';
        setTheme(savedTheme);

        // # Watch for the 'theme' changes
        const observer = new MutationObserver(() => {
            const isDark = document.documentElement.classList.contains('dark');
            setTheme(isDark ? 'dark' : 'light');
        });

        observer.observe(document.documentElement, {
            attributes: true,
            attributeFilter: ['class']
        });

        return () => observer.disconnect();
    }, []);

    return (
        <>
            <nav className="bg-white dark:bg-gray-900 border-b border-gray-200 dark:border-gray-700 shadow-sm sticky top-0 z-50 transition-colors backdrop-blur-sm bg-white/95 dark:bg-gray-900/95">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="flex justify-between items-center h-16">
                        {/* # Website 'Logo' & 'Name' # */}
                        <div className="flex items-center space-x-3">
                            <div className="relative w-10 h-10 flex-shrink-0">
                                <Image
                                    src={theme === 'light' ? UIData.black_logo : UIData.white_logo}
                                    alt="Shortify Logo"
                                    width={40}
                                    height={40}
                                    className="object-contain"
                                    priority
                                />
                            </div>
                            <span className="text-3xl font-extrabold bg-gradient-to-r from-black to-gray-600 dark:from-white dark:to-gray-300 bg-clip-text text-transparent tracking-tight">
                                {UIData.site_name}
                            </span>
                        </div>

                        {/* # Website 'Welcome' Message # */}
                        <div className="hidden md:block">
                            <p className="text-md text-gray-700 dark:text-gray-300 font-medium">
                                <span> Welcome to </span>
                                <span className="font-bold text-black dark:text-white">
                                    {UIData.site_name}
                                </span>
                                <span> - Modern URL Shortener </span>
                            </p>
                        </div>

                        {/* # Right Section - 'User' Controls # */}
                        <div className="flex items-center space-x-2">
                            {/* # Display 'Username' # */}
                            <div data-tip data-for="display-username" className="hidden sm:flex items-center bg-gray-100 dark:bg-gray-800 rounded-lg px-3 py-1.5 mr-1">
                                <span className="text-md font-semibold text-gray-800 dark:text-gray-200">
                                    {username}
                                </span>
                            </div>

                            {/* # 'Display Username' Tooltip # */}
                            <Tooltip id="display-username" place="bottom" offset={{ bottom: 5 }} text={username} />

                            {/* # 'Theme' Toggle # */}
                            <ThemeToggle />

                            {/* # Edit 'Username' Icon # */}
                            <Button
                                data-tip
                                data-for="edit-username"
                                variant="ghost"
                                size="icon"
                                className="h-9 w-9 hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                                onClick={onEditUsername}
                            >
                                <Edit2 className="w-6 h-6 text-gray-700 dark:text-gray-300" />
                            </Button>

                            {/* # 'Edit Username' Tooltip # */}
                            <Tooltip id="edit-username" place="bottom" offset={{ bottom: 5 }} text='Edit Username' />

                            {/* # Clerk 'User' Button # */}
                            <div data-tip data-for="user-button" className="ml-1">
                                <UserButton
                                    afterSignOutUrl="/sign-in"
                                    appearance={{
                                        elements: {
                                            avatarBox: "h-9 w-9"
                                        }
                                    }}
                                />
                            </div>

                            {/* # 'User Button' Tooltip # */}
                            <Tooltip id="user-button" place="bottom" offset={{ bottom: 0 }} text='User Account' />
                        </div>
                    </div>
                </div>
            </nav >
        </>
    );
};

export default Navbar;