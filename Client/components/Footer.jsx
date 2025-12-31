export default function Footer() {
    return (
        <footer className="bg-white dark:bg-gray-900 border-t border-gray-200 dark:border-gray-700 mt-auto transition-colors">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
                <p className="text-center text-sm text-gray-600 dark:text-gray-400">
                    © {new Date().getFullYear()} Shortify. All rights reserved.
                </p>
            </div>
        </footer>
    );
}
