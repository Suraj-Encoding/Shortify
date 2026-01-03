'use client';

import { useState } from 'react';
import { Copy, Trash2, ExternalLink } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import DeleteLinkDialog from './DeleteLinkDialog';

// # 'Link Card' Component #
const LinkCard = ({ link, onDelete, onClick, onCopy }) => {
    const [isDeleteOpen, setIsDeleteOpen] = useState(false);

    return (
        <>
            <Card
                className="hover:shadow-lg transition-shadow cursor-pointer dark:bg-gray-800 dark:border-gray-700"
                onClick={() => onClick(link)}
            >
                <CardHeader>
                    <CardTitle className="flex items-start justify-between">
                        <span className="text-lg truncate pr-2 dark:text-white"> {link.title || 'Untitled'} </span>
                        <Button
                            variant="ghost"
                            size="icon"
                            className="h-8 w-8"
                            onClick={(e) => {
                                e.stopPropagation();
                                setIsDeleteOpen(true);
                            }}
                        >
                            <Trash2 className="w-4 h-4 text-red-600" />
                        </Button>
                    </CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="space-y-2">
                        <div>
                            <p className="text-xs text-gray-500 dark:text-gray-400 mb-1"> Short URL </p>
                            <div className="flex items-center space-x-2">
                                <code className="text-sm bg-gray-100 dark:bg-gray-700 px-2 py-1 rounded flex-1 truncate dark:text-gray-200">
                                    {link.short_url}
                                </code>
                                <Button
                                    variant="ghost"
                                    size="icon"
                                    className="h-8 w-8"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        onCopy(link.short_url);
                                    }}
                                >
                                    <Copy className="w-4 h-4" />
                                </Button>
                            </div>
                        </div>
                        <div>
                            <p className="text-xs text-gray-500 dark:text-gray-400 mb-1"> Destination URL </p>
                            <p className="text-sm text-gray-700 dark:text-gray-300 truncate"> {link.destination_url} </p>
                        </div>
                        <div className="flex justify-between items-center pt-2">
                            <span className="text-xs text-gray-500 dark:text-gray-400"> Slug: {link.slug} </span>
                            <a
                                href={link.short_url}
                                target="_blank"
                                rel="noopener noreferrer"
                                onClick={(e) => e.stopPropagation()}
                                className="text-xs text-black dark:text-white hover:underline flex items-center"
                            >
                                Visit
                                <ExternalLink className="w-3 h-3 ml-1" />
                            </a>
                        </div>
                    </div>
                </CardContent>
            </Card >

            <DeleteLinkDialog
                link={link}
                open={isDeleteOpen}
                onOpenChange={setIsDeleteOpen}
                onConfirmDelete={onDelete}
            />
        </>
    );
};

export default LinkCard;