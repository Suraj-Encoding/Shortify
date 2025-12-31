'use client';

import { UserButton } from '@clerk/nextjs';
import { Edit2 } from 'lucide-react';
import { Button } from '@/components/ui/button';
import ThemeToggle from './ThemeToggle';

export default function Navbar({ username, onEditUsername }) {
    return (
        <nav className="bg-white dark:bg-gray-900 border-b border-gray-200 dark:border-gray-700 shadow-sm sticky top-0 z-50 transition-colors">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center h-16">
                    <div className="flex items-center space-x-2">
                        <svg className="w-8 h-8 text-black dark:text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13.828 10.172a4 4 0 00-5.656 0l-4 4a4 4 0 105.656 5.656l1.102-1.101m-.758-4.899a4 4 0 005.656 0l4-4a4 4 0 00-5.656-5.656l-1.1 1.1" />
                        </svg>
                        <span className="text-2xl font-bold text-black dark:text-white">Shortify</span>
                    </div>

                    <div className="hidden md:block">
                        <p className="text-sm text-gray-600 dark:text-gray-300">
                            Welcome to <span className="font-semibold text-black dark:text-white">Shortify</span> - modern URL shortener
                        </p>
                    </div>

                    <div className="flex items-center space-x-3">
                        <span className="text-sm text-gray-700 dark:text-gray-300 hidden sm:block font-medium">{username}</span>
                        <ThemeToggle />
                        <Button variant="ghost" size="icon" className="h-8 w-8" onClick={onEditUsername}>
                            <Edit2 className="w-4 h-4" />
                        </Button>
                        <UserButton afterSignOutUrl="/sign-in" />
                    </div>
                </div>
            </div>
        </nav>
    );
}