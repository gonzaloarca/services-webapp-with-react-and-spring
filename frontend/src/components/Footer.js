import { Grid, makeStyles } from '@material-ui/core';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';

const useStyles = makeStyles((theme) => ({
  footerContainer: {
    backgroundColor: 'transparent',
    width: '80vw',
    padding: '50px 0',
    margin: '0 auto',
  },
  header: {
    fontWeight: 600,
    fontSize: '1rem',
    color: themeUtils.colors.blue,
    height: '1.2rem',
  },
  text: {
    fontWeight: 500,
    fontSize: '0.9rem',
    color: themeUtils.colors.blue,
    lineHeight: '1rem',
  },
  copyright: {
    marginTop: 20,
    fontSize: '0.8rem',
    color: 'rgba(0,0,0,0.7)',
  },
}));

const Footer = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.footerContainer}>
      <Grid container spacing={5}>
        <Grid container justifyContent="center" item spacing={3}>
          <Grid className={classes.header} item md={2}>
            {t('footer.creators')}
          </Grid>
          <Grid className={classes.header} item md={2}>
            {t('footer.contact')}
          </Grid>
        </Grid>
        <Grid container justifyContent="center" item spacing={2}>
          <Grid className={classes.text} item md={2}>
            <ul>
              <li className="mb-2">Gonzalo Arca</li>
              <li className="mb-2">Manuel Félix Parma</li>
              <li className="mb-2">Francisco Quesada</li>
              <li>Manuel Joaquín Rodriguez</li>
            </ul>
          </Grid>
          <Grid className={classes.text} item md={2}>
            <a href="mailto: paw.hirenet@gmail.com">paw.hirenet@gmail.com</a>
          </Grid>
        </Grid>
        <Grid container item justifyContent="center" lg={12}>
          <div className={classes.copyright}>{t('footer.copyright')}</div>
        </Grid>
      </Grid>
    </div>
  );
};

export default Footer;
