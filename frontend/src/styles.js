const styles = (theme) => ({
  contentContainer: {
    width: '95vw',
    maxWidth: 1284,
    margin: '0 auto',
    padding: '30px 5%',
    backgroundColor: 'white',
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
  },
  contentContainerTransparent: {
    width: '95vw',
    maxWidth: 1284,
    margin: '0 auto',
    padding: '30px 5%',
  },
  sectionTitle: {
    padding: '25px 30px 0',
    fontSize: '1.3rem',
    fontWeight: 600,
  },
  blueCircle: {
    width: '35px',
    height: '35px',
    borderRadius: '35px',
    backgroundColor: '#485696',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  yellowCircle: {
    width: '35px',
    height: '35px',
    borderRadius: '35px',
    backgroundColor: '#ffc800',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  orangeCircle: {
    width: '35px',
    height: '35px',
    borderRadius: '35px',
    backgroundColor: 'darkorange',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  circleText: {
    position: 'absolute',
    margin: '0',
    color: 'white',
    fontWeight: 'bold',
    fontSize: '18px',
  },
});

export default styles;
