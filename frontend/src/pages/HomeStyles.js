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
    padding: '70px 0',
    color: '#485696',
    background: 'url("/img/publish-landing-bg-1.svg")',
    fontSize: '1.5rem',
    fontWeight: 600,
  },
});

export default homeStyles;
