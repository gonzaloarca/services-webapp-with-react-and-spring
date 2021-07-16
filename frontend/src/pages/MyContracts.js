import {
  AppBar,
  Divider,
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
import ContractCard from '../components/ContractCard';

const useGlobalStyles = makeStyles(styles);

const useStyles = makeStyles((theme) => ({
  tabs: {
    backgroundColor: 'white',
    color: 'black',
    '& .MuiTab-wrapper': {
      flexDirection: 'row',
      justifyContent: 'start',
    },
  },
  tabContent: {
    display: 'flex',
    alignItems: 'center',
  },
  contractSection: {
    padding: '10px 30px',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  contractTypeSection: {
    padding: '10px 0',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
}));

const hiredServices = {
  activeContracts: [
    {
      avgRate: 3.6666666666666665,
      client: {
        id: 11,
        username: 'El Beto (Julian Sicardi)',
        image: '/img/plumbing.jpeg',
      },
      professional: {
        id: 12,
        username: 'El Beto (Julian Sicardi)',
        image: '/img/plumbing.jpeg',
      },
      contractsCompleted: 4,
      creationDate: '2021-06-16T16:48:40.860',
      image: {
        uri: '/img/plumbing.jpeg',
      },
      jobContract: {
        id: 29,
        uri: 'http://localhost:8080/job-posts/8/packages/8/contracts/29',
      },
      jobPackage: {
        id: 8,
        uri: 'http://localhost:8080/job-posts/8/packages/8',
      },
      jobPost: {
        id: 8,
        uri: 'http://localhost:8080/job-posts/8',
      },
      jobTitle: 'Niñero turno mañana',
      jobType: {
        description: 'BABYSITTING',
        id: 7,
      },
      packageTitle: '4 dias a la semana 4 horas',
      rateType: {
        description: 'TBD',
        id: 2,
      },
      reviewsCount: 3,
      scheduledDate: '2021-06-16T16:48',
      state: {
        description: 'PENDING_APPROVAL',
        id: 0,
      },
    },
  ],
  pendingContracts: [],
  finalizedContracts: [],
};

const myServices = {
  activeContracts: [],
  pendingContracts: [],
  finalizedContracts: [],
};

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
            <ContractsDashboard contracts={hiredServices} />
          </TabPanel>
          <TabPanel value={tabValue} index={1}>
            <ContractsDashboard contracts={myServices} />
          </TabPanel>
        </div>
      </div>
    </div>
  );
};

const ContractsDashboard = ({ contracts }) => {
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

  const getContracts = (index) => {
    switch (index) {
      case 0:
        return contracts.activeContracts;
      case 1:
        return contracts.pendingContracts;
      case 2:
        return contracts.finalizedContracts;
      default:
        return [];
    }
  };

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
      {/* Sección con selector de tipo de contrato */}
      <Grid item sm={12} md={3}>
        <div className={classes.contractTypeSection}>
          <h2 className={clsx(globalClasses.sectionTitle, 'p-3')}>
            {t('mycontracts.contracts')}
          </h2>
          <Divider />
          <Tabs
            orientation="vertical"
            value={tabValue}
            indicatorColor="primary"
            onChange={handleChange}
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
                    <div className="text-left">{section.tabLabel}</div>
                  </div>
                }
              />
            ))}
          </Tabs>
        </div>
      </Grid>

      {/* Sección con lista de contratos */}
      <Grid item sm={12} md={9}>
        <div className={classes.contractSection}>
          {contractSections.map((section, index) => (
            <TabPanel key={index} value={tabValue} index={index}>
              <h2 className={clsx(globalClasses.sectionTitle, 'py-3')}>
                {section.title}
              </h2>
              <Divider />
              {getContracts(index).map((contract) => (
                <ContractCard contract={contract} key={contract.id} />
              ))}
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
