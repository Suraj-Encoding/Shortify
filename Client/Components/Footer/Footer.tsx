"use client";

import React from 'react';
import Data from '@/Interface/constant/data';

//  # Footer Component
const Footer = () => {
  return (
    <div className="flex font-[600] justify-center gap-1 text-[16px] py-3 lg:pb-6 text-gray-500">
      <span> Copyright </span>
      <span className="text-[18px]" > &copy; </span>
      <span> {new Date().getFullYear()} {Data.author} </span>
    </div >
  );
};

export default Footer;
