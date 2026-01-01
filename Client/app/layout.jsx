import './globals.css'
import MetaData from '../Interface/constant/metadata';
import { ClerkProvider } from '@clerk/nextjs';
import "react-toastify/dist/ReactToastify.css";

export const metadata = MetaData

const RootLayout = ({ children }) => {
  return (
    <>
      <ClerkProvider>
        <html lang="en">
          <body>
            {children}
          </body>
        </html>
      </ClerkProvider >
    </>
  );
};

export default RootLayout;