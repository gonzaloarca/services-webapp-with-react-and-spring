import {
  AppBar,
  Grid,
  makeStyles,
  Tab,
  Tabs,
  withStyles,
} from '@material-ui/core';
import React from 'react';
import { useTranslation } from 'react-i18next';
import CircleIcon from '../components/CircleIcon';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import { Check, Group, Person, Schedule, Work } from '@material-ui/icons';
import clsx from 'clsx';

const useGlobalStyles = makeStyles(styles);

const useStyles = makeStyles((theme) => ({
  tabs: {
    backgroundColor: 'white',
    color: 'black',
  },
  tabContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'start',
  },
  contractSection: {
    padding: 10,
  },
}));

const TabPanel = ({ children, value, index }) => {
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
    >
      {value === index && children}
    </div>
  );
};

const MyContracts = () => {
  const [tabValue, setTabValue] = React.useState(0);
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
  };

  return (
    <div>
      <NavBar currentSection={'/my-contracts'} />
      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName={t('navigation.sections.mycontracts')} />
        <div>
          <AppBar position="static">
            <Tabs
              variant="fullWidth"
              className={classes.tabs}
              value={tabValue}
              onChange={handleChange}
            >
              <Tab
                label={
                  <div className="flex items-center justify-center">
                    <CircleIcon
                      className="mr-2"
                      color={themeUtils.colors.yellow}
                      size="2rem"
                    >
                      <Group className="text-white" />
                    </CircleIcon>
                    {t('mycontracts.hiredservices')}
                  </div>
                }
              />
              <Tab
                label={
                  <div className="flex items-center justify-center">
                    <CircleIcon
                      className="mr-2"
                      color={themeUtils.colors.lightBlue}
                      size="2rem"
                    >
                      <Person className="text-white" />
                    </CircleIcon>
                    {t('mycontracts.myservices')}
                  </div>
                }
              />
            </Tabs>
          </AppBar>

          <TabPanel value={tabValue} index={0}>
            <ContractsDashboard />
          </TabPanel>
          <TabPanel value={tabValue} index={1}>
            Item Two
          </TabPanel>
          <TabPanel value={tabValue} index={2}>
            Item Three
          </TabPanel>
        </div>
      </div>
    </div>
  );
};

const ContractsDashboard = () => {
  const [tabValue, setTabValue] = React.useState(0);
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
  };

  const contractSections = [
    {
      title: t('mycontracts.activecontracts'),
      tabLabel: t('mycontracts.active'),
      color: themeUtils.colors.green,
      icon: <Work className="text-white" />,
    },
    {
      title: t('mycontracts.pendingapprovalcontracts'),
      tabLabel: t('mycontracts.pendingapproval'),
      color: themeUtils.colors.orange,
      icon: <Schedule className="text-white" />,
    },
    {
      title: t('mycontracts.finalizedcontracts'),
      tabLabel: t('mycontracts.finalized'),
      color: themeUtils.colors.aqua,
      icon: <Check className="text-white" />,
    },
  ];

  // const getSectionTitle = (index) => {
  //   switch (index) {
  //     case 0:
  //       return t('mycontracts.activecontracts');
  //     case 1:
  //       return t('mycontracts.pendingapprovalcontracts');
  //     case 2:
  //       return t('mycontracts.finalizedcontracts');
  //     default:
  //       return '';
  //   }
  // };

  // const getTabTitle = (index) => {
  //   switch (index) {
  //     case 0:
  //       return t('mycontracts.active');
  //     case 1:
  //       return t('mycontracts.pendingapproval');
  //     case 2:
  //       return t('mycontracts.finalized');
  //     default:
  //       return '';
  //   }
  // };

  return (
    <Grid container spacing={4}>
      <Grid item sm={12} md={3}>
        <Tabs
          orientation="vertical"
          value={tabValue}
          onChange={handleChange}
          aria-label="Vertical tabs example"
          className={classes.tabs}
        >
          {contractSections.map((section, index) => (
            <HirenetTab
              key={index}
              className="items-start"
              label={
                <div
                  className={clsx(
                    classes.tabContent,
                    index === tabValue ? 'font-semibold' : 'font-medium'
                  )}
                >
                  <CircleIcon
                    className="mr-2"
                    color={section.color}
                    size="2rem"
                  >
                    {section.icon}
                  </CircleIcon>
                  {section.tabLabel}
                </div>
              }
            />
          ))}
        </Tabs>
      </Grid>
      <Grid item sm={12} md={9}>
        <div className={classes.contractSection}>
          {contractSections.map((section, index) => (
            <TabPanel value={tabValue} index={index}>
              <h2 className={globalClasses.sectionTitle}>{section.title}</h2>
            </TabPanel>
          ))}
        </div>
      </Grid>
    </Grid>
  );
};

const HirenetTab = withStyles((theme) => ({
  root: {
    transition: '0.1s opacity',
    '&:hover': {
      opacity: 1,
      transition: '0.1s opacity',
    },
  },
}))(Tab);
export default MyContracts;
