const homeStyles = (theme) => ({
  contentContainer: {
    width: '90vw',
    maxWidth: 1184,
    margin: '0 auto',
    padding: '30px 5%',
    backgroundColor: 'white',
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
  },
  contentContainerTransparent: {
    width: '90vw',
    maxWidth: 1184,
    margin: '0 auto',
    padding: '30px 5%',
  },
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
    paddingTop: 35,
    color: '#485696',
  },
});

export default homeStyles;
