import { makeStyles } from '@material-ui/core';
import React from 'react';
import { useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';

const useGlobalStyles = makeStyles(styles);

const MyContracts = () => {
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

  return (
    <div>
      <NavBar currentSection={'/my-contracts'} />
      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName={t('navigation.sections.mycontracts')} />
      </div>
      <h1>My Contracts</h1>
    </div>
  );
};

export default MyContracts;
