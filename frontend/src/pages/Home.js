import React from 'react';
import Hero from '../components/Hero';
import NavBar from '../components/NavBar';

export const Home = () => {
  return (
    <div>
      <NavBar currentSection={'/'} isTransparent />
      <Hero />
      <div style={{ height: 300 }} />
    </div>
  );
};
