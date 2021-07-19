import { themeUtils } from '../theme';

const homeStyles = (theme) => ({
  header: {
    fontSize: '1.5rem',
    fontWeight: 600,
  },

  sectionShadow: {
    boxShadow: '0 -3px 6px rgba(0, 0, 0, 0.16)',
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
    background: `url(${process.env.PUBLIC_URL}/img/publish-landing-bg-1.svg)`,
    fontSize: '1.5rem',
    fontWeight: 600,
  },

  noJobsContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    height: 400,
    width: '100%',
  },

  noJobsImage: {
    width: '40%',
    height: '40%',
    objectFit: 'contain',
    marginBottom: 30,
  },

  noJobsMessage: {
    fontSize: themeUtils.fontSizes.lg,
    fontWeight: 500,
    width: '40%',
    textAlign: 'center',
  },

  viewAll: {
    color: themeUtils.colors.blue,
    fontSize: themeUtils.fontSizes.sm,
    fontWeight: 500,
    textTransform: 'uppercase',
  },
});

export default homeStyles;
