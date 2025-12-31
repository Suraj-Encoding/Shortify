'use client';

import { useEffect } from 'react';
import { X, CheckCircle, XCircle } from 'lucide-react';

export default function Toast({ message, type, onClose }) {
    useEffect(() => {
        const timer = setTimeout(() => {
            onClose();
        }, 5000);

        return () => clearTimeout(timer);
    }, [onClose]);

    return (
        <div className={`fixed bottom-4 right-4 z-50 flex items-start space-x-3 p-4 rounded-lg shadow-lg max-w-md animate-in slide-in-from-right ${type === 'success' ? 'bg-green-50 border border-green-200' : 'bg-red-50 border border-red-200'
            }`}>
            {type === 'success' ? (
                <CheckCircle className="w-5 h-5 text-green-600 flex-shrink-0 mt-0.5" />
            ) : (
                <XCircle className="w-5 h-5 text-red-600 flex-shrink-0 mt-0.5" />
            )}
            <p className={`text-sm flex-1 ${type === 'success' ? 'text-green-800' : 'text-red-800'}`}>
                {message}
            </p>
            <button
                onClick={onClose}
                className={`flex-shrink-0 ${type === 'success' ? 'text-green-600 hover:text-green-800' : 'text-red-600 hover:text-red-800'}`}
            >
                <X className="w-4 h-4" />
            </button>
        </div>
    );
}