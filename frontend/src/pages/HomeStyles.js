import { themeUtils } from '../theme';

const homeStyles = (theme) => ({
  header: {
    fontSize: '1.5rem',
    fontWeight: 600,
  },

  sectionShadow: {
    boxShadow: themeUtils.shadows.containerShadow,
  },

  publishBannerContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundSize: 'cover',
    textAlign: 'center',
    padding: '70px 0',
    color: '#485696',
    background: 'url("/img/publish-landing-bg-1.svg")',
    fontSize: '1.5rem',
    fontWeight: 600,
  },
});

export default homeStyles;
