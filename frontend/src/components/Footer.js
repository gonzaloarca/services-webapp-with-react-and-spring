import { Grid, Link, makeStyles } from '@material-ui/core';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';

const useStyles = makeStyles((theme) => ({
  footerContainer: {
    backgroundColor: 'transparent',
    width: '80vw',
    padding: '30px 0',
    margin: '0 auto',
  },
  header: {
    fontWeight: 600,
    fontSize: '1rem',
    lineHeight: '1.2rem',
    color: themeUtils.colors.blue,
    maxHeight: '1.2rem',
    marginBottom: '15px',
  },
  text: {
    fontWeight: 500,
    fontSize: '0.9rem',
    color: themeUtils.colors.blue,
    lineHeight: '1rem',
    maxHeight: '1.2rem',
    marginBottom: 6,
  },
  copyright: {
    marginTop: 10,
    fontSize: '0.8rem',
    color: 'rgba(0,0,0,0.7)',
  },
  creatorList: {
    display: 'table',
    margin: '0 auto',
    textAlign: 'center',
  },
}));

const Footer = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.footerContainer}>
      <Grid container direction="row" justifyContent="center" spacing={5}>
        <Grid
          container
          direction="column"
          alignItems="center"
          item
          md={4}
          spacing={3}
        >
          <Grid className={classes.header} item md={12}>
            {t('footer.creators')}
          </Grid>
          <Grid item md={12}>
            <ul className={classes.creatorList}>
              <li className={classes.text}>Gonzalo Arca</li>
              <li className={classes.text}>Manuel Félix Parma</li>
              <li className={classes.text}>Francisco Quesada</li>
              <li className={classes.text}>Manuel Joaquín Rodriguez</li>
            </ul>
          </Grid>
        </Grid>
        <Grid
          container
          direction="column"
          alignItems="center"
          item
          md={4}
          spacing={3}
        >
          <Grid className={classes.header} item md={12}>
            {t('footer.contact')}
          </Grid>
          <Grid className={classes.text} item md={12}>
            <Link href="mailto: paw.hirenet@gmail.com">
              paw.hirenet@gmail.com
            </Link>
          </Grid>
        </Grid>
        <Grid container item justifyContent="center" md={12}>
          <div className={classes.copyright}>{t('footer.copyright')}</div>
        </Grid>
      </Grid>
    </div>
  );
};

export default Footer;
