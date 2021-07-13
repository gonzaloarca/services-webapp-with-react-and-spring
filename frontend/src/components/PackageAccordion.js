import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Button,
  Chip,
  Grid,
  makeStyles,
  withStyles,
} from '@material-ui/core';
import { Description, ExpandMore } from '@material-ui/icons';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { LightenDarkenColor, themeUtils } from '../theme';
import packagePriceFormatter from '../utils/packagePriceFormatter';

const useStyles = makeStyles((theme) => ({
  packageContainer: {
    backgroundColor: themeUtils.colors.blue,
    color: 'white',
  },
  packageSummaryContainer: {
    minHeight: 100,
  },
  packageTitleContainer: {
    height: 100,
    width: '100%',
    display: 'flex',
    alignItems: 'center',
  },
  packageTitle: {
    fontSize: '1.1rem',
    WebkitLineClamp: 3,
    width: '100%',
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    fontWeight: 600,
  },
  priceContainer: {
    position: 'relative',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '0.9rem',
    fontWeight: 500,
  },
  priceLabel: {
    fontSize: '0.9rem',
    position: 'absolute',
    margin: '0 auto',
    [theme.breakpoints.up('sm')]: {
      top: '0.4rem',
    },
    top: '-0.5rem',
  },
  priceContent: {
    WebkitLineClamp: 3,
    width: '100%',
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    marginTop: 3,
    padding: '5px 5px',
    borderRadius: 10,
    backgroundColor: 'white',
    color: themeUtils.colors.blue,
    fontSize: '1rem',
    lineHeight: '1.2rem',
    fontWeight: 600,
    textAlign: 'center',
  },
  priceDivider: {
    [theme.breakpoints.up('sm')]: {
      display: 'none',
    },
    color: 'white',
  },
  hireDivider: {
    [theme.breakpoints.up('md')]: {
      display: 'none',
    },
    color: 'white',
  },
  hireContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  packageDescription: {
    backgroundColor: 'white',
    color: 'black',
    borderRadius: 10,
    fontSize: '0.9rem',
    fontWeight: 500,
    padding: 15,
    width: '100%',
  },
  MuiAccordionroot: {
    '&.MuiAccordion-root:before': {
      backgroundColor: 'white',
    },
  },
}));

const PackageAccordion = ({ pack, isHireable }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Accordion
      TransitionProps={{ unmountOnExit: true }}
      className={classes.packageContainer}
      classes={{ root: classes.MuiAccordionroot }}
    >
      <AccordionSummary
        className={classes.packageSummaryContainer}
        expandIcon={<ExpandMore className="text-white" />}
      >
        <Grid container justifyContent="space-evenly" spacing={3}>
          <Grid
            className={classes.packageTitleContainer}
            item
            xs={12}
            sm={8}
            md={isHireable ? 7 : 8}
          >
            <div className={classes.packageTitle}>{pack.title}</div>
          </Grid>
          <Grid className={classes.priceDivider} item xs={12}>
            <hr />
          </Grid>

          <Grid
            className={classes.priceContainer}
            item
            xs={12}
            sm={4}
            md={isHireable ? 3 : 4}
          >
            <div className={classes.priceLabel}>{t('pricelabel')}</div>
            <div className={classes.priceContent}>
              {packagePriceFormatter(t, pack.rateType, pack.price)}
            </div>
          </Grid>
          {isHireable ? (
            <>
              {' '}
              <Grid className={classes.hireDivider} item xs={12}>
                <hr />
              </Grid>
              <Grid
                className={classes.hireContainer}
                item
                xs={12}
                sm={12}
                md={2}
              >
                <HireButton>{t('hire').toUpperCase()}</HireButton>
              </Grid>{' '}
            </>
          ) : (
            <></>
          )}
        </Grid>
      </AccordionSummary>

      <AccordionDetails className="w-100">
        <div className={classes.packageDescription}>
          <Chip
            className="font-semibold text-sm"
            icon={<Description />}
            label={t('packagedescription')}
          />
          <p className="mt-4">{pack.description}</p>
        </div>
      </AccordionDetails>
    </Accordion>
  );
};

const HireButton = withStyles((theme) => ({
  root: {
    backgroundColor: themeUtils.colors.lightBlue,
    color: 'white',
    width: '80%',
    fontSize: '1.1rem',
    '&:hover': {
      backgroundColor: LightenDarkenColor(themeUtils.colors.lightBlue, 20),
      color: 'white',
    },
  },
}))(Button);

export default PackageAccordion;
