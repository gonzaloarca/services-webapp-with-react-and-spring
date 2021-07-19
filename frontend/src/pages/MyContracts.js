import {
  AppBar,
  Divider,
  Grid,
  makeStyles,
  Tab,
  Tabs,
  withStyles,
} from '@material-ui/core';
import React, { useContext, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CircleIcon from '../components/CircleIcon';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import { Check, Group, Person, Schedule, Work } from '@material-ui/icons';
import clsx from 'clsx';
import ContractCard from '../components/ContractCard';
import { useParams, useHistory } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import { useContracts } from '../hooks';
import { UserContext } from '../context';

const useGlobalStyles = makeStyles(styles);

const useStyles = makeStyles((theme) => ({
  tabs: {
    backgroundColor: 'white',
    color: 'black',
  },
  tabContent: {
    display: 'flex',
    alignItems: 'center',
  },
  contractSection: {
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  contractTypeSection: {
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  noContractsContainer: {
    padding: 20,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'column',
    width: '100%',
    height: 350,
  },
  noContractsImage: {
    width: '60%',
    height: '60%',
    objectFit: 'contain',
    marginBottom: 20,
  },
  noContractsHeader: {
    fontSize: themeUtils.fontSizes.lg,
    fontWeight: 600,
    textAlign: 'center',
  },
  noContractsBody: {
    fontSize: themeUtils.fontSizes.md,
    fontWeight: 500,
    textAlign: 'center',
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

const MyContracts = ({ history }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { activeTab, activeState } = useParams();
  const { currentUser } = useContext(UserContext);
  const { getContractsByClientIdAndState, getContractsByProAndStateId } =
    useContracts();
  const [hiredServices, setHiredServices] = useState([]);
  const [myServices, setMyServices] = useState([]);
  const [tabValue, setTabValue] = React.useState(activeTab === 'pro' ? 1 : 0);
  const tabPaths = ['hired', 'pro'];
  const loadHiredContracts = async () => {
    const clientContracts = await getContractsByClientIdAndState(
      currentUser.id,
      activeState,
      1
    );
    setHiredServices(clientContracts);
  };
  const loadMyServicesContracts = async () => {
    const proServices = await getContractsByProAndStateId(
      currentUser.id,
      activeState,
      1
    );
    setMyServices(proServices);
  };
  useEffect(() => {
    if (currentUser && activeTab && activeState) {
      if (activeTab === 'pro') {
        setTabValue(1);
        loadMyServicesContracts();
      } else {
        setTabValue(0);
        loadHiredContracts();
      }
    }
  }, [activeState, activeTab, currentUser]);

  const handleChange = (_event, newValue) => {
    setTabValue(newValue);
    history.push(
      `/my-contracts/${tabPaths[newValue]}` +
        (activeState ? `/${activeState}` : '')
    );
  };

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.mycontracts') })}
        </title>
      </Helmet>
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
            <div className="mt-6">
              <ContractsDashboard
                contracts={hiredServices}
                topTabSection={tabPaths[tabValue]}
                loadHiredContracts={loadHiredContracts}
                loadMyServicesContracts={loadMyServicesContracts}
              />
            </div>
          </TabPanel>
          <TabPanel value={tabValue} index={1}>
            <div className="mt-6">
              <ContractsDashboard
                contracts={myServices}
                isOwner
                topTabSection={tabPaths[tabValue]}
                loadHiredContracts={loadHiredContracts}
                loadMyServicesContracts={loadMyServicesContracts}
              />
            </div>
          </TabPanel>
        </div>
      </div>
    </div>
  );
};

const ContractsDashboard = ({
  contracts,
  isOwner = false,
  topTabSection,
  loadHiredContracts,
  loadMyServicesContracts,
}) => {
  console.log('loadHired', loadHiredContracts);
  console.log('loadMyServices', loadMyServicesContracts);
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { activeState } = useParams();
  const contractSections = [
    {
      title: t('mycontracts.activecontracts'),
      tabLabel: t('mycontracts.active'),
      color: themeUtils.colors.green,
      icon: <Work className="text-white" />,
      path: 'active',
    },
    {
      title: t('mycontracts.pendingapprovalcontracts'),
      tabLabel: t('mycontracts.pendingapproval'),
      color: themeUtils.colors.orange,
      icon: <Schedule className="text-white" />,
      path: 'pending',
    },
    {
      title: t('mycontracts.finalizedcontracts'),
      tabLabel: t('mycontracts.finalized'),
      color: themeUtils.colors.aqua,
      icon: <Check className="text-white" />,
      path: 'finalized',
    },
  ];

  const getNoContractsContent = (isOwner, index) => {
    if (isOwner) {
      switch (index) {
        case 0:
          return {
            header: t('mycontracts.contractstate.noactivecontractsheader'),
            body: t('mycontracts.contractstate.noactivecontractssubtitlepro'),
          };
        case 1:
          return {
            header: t('mycontracts.contractstate.nopendingcontractsheader'),
            body: t('mycontracts.contractstate.nopendingcontractssubtitlepro'),
          };
        case 2:
          return {
            header: t('mycontracts.contractstate.nofinalizedcontractsheader'),
            body: t('mycontracts.contractstate.nofinalizedcontractssubtitle'),
          };
        default:
          return {};
      }
    } else {
      switch (index) {
        case 0:
          return {
            header: t('mycontracts.contractstate.noactivecontractsheader'),
            body: t('mycontracts.contractstate.noactivecontractssubtitle'),
          };
        case 1:
          return {
            header: t('mycontracts.contractstate.nopendingcontractsheader'),
            body: t('mycontracts.contractstate.nopendingcontractssubtitle'),
          };
        case 2:
          return {
            header: t('mycontracts.contractstate.nofinalizedcontractsheader'),
            body: t('mycontracts.contractstate.nofinalizedcontractssubtitle'),
          };
        default:
          return {};
      }
    }
  };

  const history = useHistory();
  const [tabValue, setTabValue] = React.useState(
    activeState === 'finalized' ? 2 : activeState === 'pending' ? 1 : 0
  );

  useEffect(() => {
    if (activeState) {
      if (activeState === 'finalized') {
        setTabValue(2);
      } else if (activeState === 'pending') {
        setTabValue(1);
      } else {
        setTabValue(0);
      }
    }
  }, [activeState]);

  useEffect(() => {
    history.replace(
      `/my-contracts/${topTabSection}/${contractSections[tabValue].path}`
    );
  }, []);

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
    history.push(
      `/my-contracts/${topTabSection}/${contractSections[newValue].path}`
    );
  };

  return (
    <Grid container spacing={4}>
      {/* Sección con selector de tipo de contrato */}
      <Grid item sm={12} md={3}>
        <div className={classes.contractTypeSection}>
          <h2 className={clsx(globalClasses.sectionTitle)}>
            {t('mycontracts.contracts')}
          </h2>
          <Divider className="mb-2" />
          <Tabs
            orientation="vertical"
            value={tabValue}
            indicatorColor="primary"
            onChange={handleChange}
            className={classes.tabs}
          >
            {contractSections.map((section, index) => (
              <HirenetTab
                fullWidth
                key={index}
                className="items-start"
                label={
                  <div
                    className={clsx(
                      classes.tabContent,
                      index === tabValue ? 'font-semibold' : 'font-medium',
                      'w-full'
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
              <h2 className={globalClasses.sectionTitle}>{section.title}</h2>
              <Divider className="mb-2" />

              <div className="p-4">
                {contracts.length === 0 ? (
                  <NoContracts
                    header={getNoContractsContent(isOwner, index).header}
                    body={getNoContractsContent(isOwner, index).body}
                  />
                ) : (
                  contracts.map((contract) => (
                    <div key={contract.id} className="mb-6">
                      <ContractCard
                        contract={contract}
                        isOwner={isOwner}
                        refetch={(section) =>
                          history.push(
                            `/my-contracts/${topTabSection}/${section}`
                          )
                        }
                      />
                    </div>
                  ))
                )}
              </div>
            </TabPanel>
          ))}
        </div>
      </Grid>
    </Grid>
  );
};

const HirenetTab = withStyles((theme) => ({
  root: {
    padding: '6px 10px',
    maxWidth: '100%',
    width: '100%',
    transition: '0.1s opacity',
    '&:hover': {
      opacity: 1,
      transition: '0.1s opacity',
    },
    '& .MuiTab-wrapper': {
      flexDirection: 'row',
      justifyContent: 'start',
    },
  },
}))(Tab);

const NoContracts = ({ header, body }) => {
  const classes = useStyles();

  return (
    <div className={classes.noContractsContainer}>
      <img
        src={process.env.PUBLIC_URL + '/img/contract1.svg'}
        alt=""
        className={classes.noContractsImage}
      />
      <h3 className={classes.noContractsHeader}>{header}</h3>
      <p className={classes.noContractsBody}>{body}</p>
    </div>
  );
};
export default MyContracts;
